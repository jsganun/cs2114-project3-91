# Theme Park Queue System (cs2114-project3-91)

## How to Run

Run these commands from the project folder.

**Compile everything:**

```
javac -d out -cp lib/junit-platform-console-standalone-1.10.2.jar classes/*.java tests/*.java
```

**Run the tests:** All 49 tests should pass. The model tests do not need the view or the controller. TestParkView tests the prompt methods by typing input into them.

```
java -jar lib/junit-platform-console-standalone-1.10.2.jar execute -cp out --scan-classpath
```

**Run the program (thin view):** Type the number of a menu option and press Enter.

```
java -cp out classes.Main
```

**Quick demo:**

1. Enter `7`, then `y` to open the park.
2. Enter `3` to add a ride. Use a small capacity like 2. A ride only starts when at least half of its seats would be filled.
3. Enter `5` to open the ride.
4. Enter `9` a few times to add visitors to its line. Answer `y` to the fast pass question for some of them.
5. Enter `2` to see the ride's details, including the regular and fast pass lines.
6. Enter `10` to let time pass, then `2` again to see the lines get shorter. Fast pass visitors board first.
7. Enter `0` to quit.



## Reflection



### What changed from our specification? (D3)

- **Attraction:** A method that was not present in the specification for this class was `Tick(float)`. We added this method to allow the attractions to simulate time, something which we forgot to account for in specification.
- **Dequeue:** An additional method that wasn't included in specification was `find(Object)`. We added this method to see if an object was inside of the Dequeue, and if so, its index as well.
- **ParkView:** A method that was not present in the specification for this class was `showMessage(String)`. We added this method to allow other classes to easily display messages to the user without having the rewrite the code. In our spec, we also had `showAttractionDetails(Attraction)` taking in user input, but we moved it to controller to keep the model/view boundary.
- **UserMenu:** The spec had seven menu actions. We added three more: add a visitor, advance time, and quit. Without them there was no way to put people in a line or let rides run, so the MVP could not be shown. UserMenu also now asks for the attraction ID, since the view cannot look up an ID without seeing the park.
- **Attraction:** We also added `getLineSize()`. The spec's model/view boundary listed it, but it was never built. Without it, the view would have needed the `Dequeue` to count the people in line.
- **Dequeue:** We also added `clear()`. Attraction uses it to empty the line when a ride closes.
- **Where validation lives:** The spec said `UserMenu.promptIntRange()` checks for bad numbers. That method is in `ParkView` instead, since reading input is the view's job. The spec also put rule checks in `Park`, but `Park` does not yet reject duplicate ride names or visitors while the park is closed, so `UserMenu` checks those for now. `UserMenu` also checks whether the park is already open or closed, because `Park.setActive()` does not return a result.
- **Tests:** We added two tests to `TestAttraction`. One tests `getLineSize()`. The other tests wait times when a ride is not running, after we found and fixed a bug there. The spec planned tests for every class, but `TestParkView` is still empty and there is no `TestUserMenu` yet.



### Did the model really stand alone? (D3)

Mostly. The model classes do not know about the view or the controller, and all of the model tests run without them. The view never changes the model. Only the controller does.

A few rules did end up in the controller instead of the model, because the model did not handle them yet:

- Checking for duplicate ride names
- Checking whether the park or a ride is already open or closed
- Checking that the park is open before adding a visitor
- The limits for capacity, ride length, and wait time, which are copied from `Park`

Also, `Attraction.getVisitors()` still returns the real line, so any class that calls it could change the line. We added `getLineSize()` so the view does not need it.

### What happened to our unknowns? (D3)

- **Time Simulation:** Figured out. The manager lets time pass with the "Advance time" menu option, and each ride moves forward one minute at a time. We chose this over a real clock because the screen only changes when the user picks an option, and a clock running in the background would print in the middle of the menu.
- **Calculating Wait Times:** We figured this out by implementing a `find` method into `Dequeue` that allows us to find the position of a visitor. We then used their position to figure out how many rounds it will take before they can board the ride. We then used that number to calculate time using the time of the current round and future rounds.
- **Arriving Visitors:** Partly figured out. For now, the manager adds each visitor through the menu. We still plan to have visitors arrive on their own as time passes, but `VISITOR_INTERVAL` in `Main` is not used yet.
- **Visitor Groups:** We are still determining how to cleanly implement visitor groups if we continue adding it as a stretch feature.



### What doesn't work yet? (D3)

- Visitors do not leave the line when their maximum wait runs out. They only check the wait once, when they try to join. Our revised scope said they would leave.
- The wait estimate ignores the rule that a ride only starts when at least half of its seats would be filled. A visitor can be told "0 minutes" and then wait until more people join.
- The minute a ride boards is not counted toward the ride, so simulated time runs a little slow.
- Pressing Ctrl+Z or Ctrl+D ends the input and crashes the program.
- `TestParkView` is empty and there is no `TestUserMenu`.
- There is no menu option to show visitors, and the `Statistic` class is not used yet.

### How did your design absorb the change? (D4)
The main way that the change to add a fast-pass lane to the attractions was done through the `Attraction` class. The `Attraction` class is responsible for managing the queues for itself, meaning it was the most reasonable place to put logic for the fast-pass lane.

The `Visitor` class also partially absorbed the change. To determine if a visitor had a fast pass, a boolean attribute had to be added to assist with this. As a result, the constructor for the class now includes a parameter to indicate if the visitor has a fast pass or not.

Classes the feature touched:

- Attraction: a second line for fast pass visitors, addVisitor picks the line, tick boards the fast pass share first, and the wait estimates handle both lines.
- Visitor: a fast pass field, getter, and constructor.
- Park: a version of addVisitor that takes a fast pass flag.
- UserMenu: a yes/no fast pass question when adding a visitor, and closing a ride and advancing time count both lines.
- ParkView: the attraction details show both lines and the wait for each.
- TestAttraction, TestPark, and TestVisitor: tests for fast pass visitors.

What matched our D3 prediction:

- Every class we said would change did change, in the way we said.
- Dequeue, Statistic, Main, UserMenu.run, and the ParkView prompt methods did not change for the feature. Dequeue changed later only to reject null in find, which was a bad input fix and not part of the feature.

What surprised us:

- More of Attraction changed than we predicted. We expected only the remainingTime methods to change, but getVisitors, getLineSize, removeVisitor, and remainingRounds also gained a fast pass parameter.
- Because those methods changed, UserMenu.closeAttraction and UserMenu.advanceTime also had to change, even though they have nothing to do with fast passes. We did not predict that.

What would make the next feature cheaper:

- When a method needs a new parameter, keep the old version and add a new one next to it, like we did with Park.addVisitor. Then the view and controller do not break.
- If Attraction.tick returned how many riders boarded from each line, the statistics feature could count riders directly instead of comparing line sizes before and after each tick.

### Did the change cross the model/view line? (D4)
The implementation of the fast pass queue sent ripples throughout the entire project, and all for a good reason. The model must computate a new type of visitor, a fast pass visitor, and therefore, must also computate a new queue for attractions. Since a new type of visitor can be made, the controllers had to now include in additional check to create a visitor with a fast pass or not. The view must show information about both fast pass visitors and the fast pass queues in attractions.

- The view learned one new thing: an attraction now has two lines. ParkView asks for each line's size and wait by passing true or false to getLineSize, remainingTime, and remainingRounds. It still never sees the Dequeue and never changes the model.
- The ripple we did not want: Attraction's existing methods were changed to take a fast pass parameter instead of adding new versions next to the old ones. That broke ParkView and UserMenu until both were updated, even in places like closing a ride.
- Park.addVisitor shows the other way. We kept the old version and added a new one, so nothing that already used it broke.

### What changed from your D3 plan? (D4)

- Park.addVisitor: D3 said it would get a fast pass parameter. We added a second version with the parameter and kept the old one, so the existing tests and callers did not change.
- Attraction: more methods took a fast pass parameter than planned. Only the remainingTime methods were in the plan.
- UserMenu: closeAttraction and advanceTime also changed so they count both lines. Advancing time now shows the regular and fast pass lines separately.
- Validation: Park.addAttraction now rejects duplicate ride names, ignoring case. Park.setActive now returns false if the park is already open or closed. D3 planned these for later.
- Validation: the Attraction and Visitor constructors now reject blank names and numbers that are not positive, and Dequeue.find rejects null. We added the Attraction check after finding that a ride with a capacity of 0 made the wait estimate loop forever.
- Tests: we dropped TestUserMenu. The D4 rubric says tests should target the model, so we spent that time on model tests instead.
- Tests: we wrote TestParkView, which tests bad input to each prompt method, including the fast pass yes/no question.
- Tests: every bad input case from our scope now has a test.
  - A ride that does not exist: TestPark.testRemoveAttractionInvalid
  - Adding a ride that already exists: TestPark.testAddAttractionDuplicate
  - Out of index or out of range: TestParkView.testPromptIntRange and testPromptFloatRange, TestDequeue.testRemove, and TestAttraction.testRemoveVisitor
  - Opening or closing the park when it is already in that state: TestPark.testSetActiveSameState and TestAttraction.testSetActive
  - An invalid yes/no answer, including the fast pass question: TestParkView.testPromptConfirmation
  - Invalid visitor or ride values: TestPark.testAddVisitor and testAddAttraction, and testConstructorInvalid in TestVisitor and TestAttraction
- Tests: we added a test that mixes adds and removes in Dequeue and checks the order stays first in, first out.

### What doesn't work yet? (D4)

- Visitors still do not leave the line when their maximum wait runs out.
- The wait estimate still ignores the rule that a ride only starts when at least half of its seats would be filled.
- A ride with a capacity of 1 starts rounds even when nobody is waiting, because half of 1 rounds down to 0.
- Park.addVisitor does not check whether the park is open. UserMenu checks it instead.
- UserMenu still has its own duplicate name and park open or closed checks, even though Park now does them too.
- Pressing Ctrl+Z or Ctrl+D ends the input and crashes the program.
- There is no menu option to show visitors, and the Statistic class is not used yet.
- Visitors do not arrive on their own. VISITOR_INTERVAL in Main is not used yet.
- The Statistic constructor does not check its input, so it has no bad input test yet.

## Next Steps



### What is left to finish the MVP (D3)

1. Make visitors leave the line when their max wait runs out.
2. Make the wait estimate match the boarding rule.
3. Move the duplicate-name and park-open checks into `Park`.
4. Add `TestUserMenu` and fill in `TestParkView`.



### Our change feature (D3)

**Fast pass lane:** A second line at each ride for fast pass holders. Each round fills a set share of seats from the fast pass line first, then the rest from the regular line.

- **Will change:**
  - `Visitor`: a fast-pass field and getter.
  - `Park.addVisitor`: a fast-pass parameter.
  - `Attraction`: a second line, `addVisitor` choosing which line, `tick` boarding the fast-pass share first, and both `remainingTime` methods.
  - `UserMenu.addVisitor`: a Y/N fast-pass question.
  - `ParkView.showAttractionDetails`: showing both lines.
- **Should not change:** `Dequeue`, `Statistic`, `Main`, `UserMenu.run`, and the ParkView prompt methods.



### What stretch goals we will attempt? (D3)

- **Attempting:** fast pass (it's the change feature) and ride statistics (the `Statistic` class is already built for this).
- **Dropping:** visitor groups. That's still an open unknown, and it breaks first-in-first-out order.
- **Moved to the MVP:** visitors leaving the line when the wait is too long, since our revised scope added it.



### What new tests will we need? (D3)

- **Time Tests:** Now that time is an element in our program, we have to make sure that the classes continue to work as time passes.
- **IllegalArgumentExceptions:** Most of the newly added methods from the specification document are public, meaning we have to make sure that they do not accept any values that may break the program.
- **TestUserMenu:** Scripted input covering the MVP path and the 4 bad-input cases from the scope.
- **TestParkView:** Bad input to the prompt methods.
- **Fast pass:** Sending visitors to the right line, and the boarding split between the two lines.
- **Leaving the line:** Visitors leave once their max wait runs out.



### Who does what? (D3)


| Member           | Task                                                                                                          |
| ---------------- | ------------------------------------------------------------------------------------------------------------- |
| Jonathan Ganun   | The Attraction changes: visitors leaving the line, the wait estimate fix, and the fast pass line and boarding |
| Benjamin Maloney | The Park and Visitor changes: the duplicate-name and park-open checks, the fast pass field, and statistics    |
| Sami Saleh       | The UserMenu options for fast pass and statistics, `TestUserMenu`, and Main                                   |
| Katie Smith      | ParkView display for both lines and statistics, and `TestParkView`                                            |

### What is left for the final build? (D4)

1. Make visitors leave the line when their maximum wait runs out.
2. Make the wait estimate follow the half capacity rule, and stop a ride with a capacity of 1 from running empty rounds.
3. Have Park.addVisitor reject visitors while the park is closed, then remove the duplicate name and park open or closed checks from UserMenu.
4. Ride statistics: total riders, average wait, and peak line length for each ride, and the busiest ride.
5. A menu option to show the visitors in the park, with a readable way to print each visitor.
6. Visitors arriving on their own every VISITOR_INTERVAL minutes when time passes.
7. Quit cleanly instead of crashing when input ends with Ctrl+Z or Ctrl+D.

### Which stretch goals are you committing to, and which are you dropping? (D4)

- Committed: the fast pass lane, which is done as our change feature.
- Committed: ride statistics, for the final build.
- Dropping: visitor groups. It is still an open unknown, and letting a group board early breaks first in, first out order.
- Visitors leaving the line is no longer a stretch goal. Our revised scope made it part of the MVP.

### What will your 3-minute demo show? (D4)

- 0:00 to 0:20: Open the park, then try to open it again to show the "already open" message.
- 0:20 to 0:50: Add a ride with a capacity of 4. Type a letter for the capacity to show the re-prompt. Then try adding the same ride name again to show the duplicate check.
- 0:50 to 1:30: Open the ride and add six visitors, some with fast passes. Type an invalid answer to the fast pass question once to show the re-prompt.
- 1:30 to 1:50: Show the ride's details, with the regular and fast pass lines and the wait for each.
- 1:50 to 2:20: Advance time and show the fast pass line boarding first, three fast pass riders for every regular rider.
- 2:20 to 2:45: Show a visitor leaving the line after waiting too long, and show the ride statistics. These are final build features.
- 2:45 to 3:00: Close the ride, confirm, and quit.

### Who does what? (D4)

- Jonathan Ganun: visitors leaving the line, the wait estimate fix, and the capacity of 1 fix in Attraction, with tests.
- Benjamin Maloney: Park.addVisitor rejecting visitors while the park is closed, and the statistics model for total riders, average wait, and peak line length, with tests.
- Sami Saleh: the UserMenu options for statistics and showing visitors, visitors arriving on their own, removing the checks from UserMenu that Park now handles, and the demo.
- Katie Smith: the ParkView display for statistics and visitors, a readable way to print each visitor, and quitting cleanly when input ends, with TestParkView tests.


## Attributions

| File | Who worked on it and how |
| --- | --- |
| `Attraction` | **Jonathan:** wrote it, including `tick()` for time (GenAI-assisted). Implemented the fast-pass queue stretch feature.<br>**Sami:** revised it by adding `getLineSize()` and fixing the wait time when a ride is not running (GenAI-assisted). Added input checks to the constructor.|
| `Dequeue` (and its `Node` class) | **Jonathan:** wrote it and fixed its bugs (GenAI-assisted).<br>**Sami:** made find reject null. |
| `Park` | **Jonathan:** made the starting template (GenAI-assisted).<br>**Ben:** wrote it.<br>**Sami:** added the fast pass version of addVisitor and the duplicate name check in addAttraction, and made setActive return whether the park changed (GenAI-assisted). |
| `Visitor` | **Jonathan:** made the starting template (GenAI-assisted) and wrote the getters. Added an attribute, constructor, and getter for determining if a visitor has a fast pass.<br>**Ben:** revised it and added `equals()`.<br>**Sami:** added input checks to the constructor. |
| `Statistic` | **Jonathan:** made the starting template (GenAI-assisted).<br>**Ben:** wrote it. |
| `ParkView` | **Jonathan:** made the starting template (GenAI-assisted).<br>**Katie:** wrote it, including the display and prompt methods.<br>**Sami:** revised the menu numbers, the attraction display, and the number prompts, and made yes/no accept lowercase (GenAI-assisted). Made the attraction details show both lines. |
| `UserMenu` | **Jonathan:** made the starting template (GenAI-assisted).<br>**Sami:** wrote the menu loop and all 10 menu options, and tested them by running the program (GenAI-assisted). Added the fast pass question, and made closing a ride and advancing time count both lines. |
| `Main` | **Jonathan:** made the starting template (GenAI-assisted).<br>**Sami:** finished and documented it (GenAI-assisted). |
| `TestAttraction` | **Jonathan:** wrote it (GenAI-assisted). Added addtional tests for the fast pass queue. <br>**Sami:** added two tests (GenAI-assisted). Added tests for bad constructor input and reopening an open ride. |
| `TestDequeue` | **Jonathan:** wrote it (GenAI-assisted).<br>**Sami:** added the test that mixes adds and removes, and the find null case. |
| `TestPark` | **Ben:** wrote it.<br>**Jonathan:** fixed typos and JUnit usage.<br>**Sami:** added tests for fast pass visitors, duplicate ride names, and setting the park to the state it is already in. |
| `TestVisitor` | **Ben:** wrote it.<br>**Jonathan:** fixed typos and JUnit usage.<br>**Sami:** added tests for fast pass and bad constructor input. |
| `TestStatistic` | **Ben:** wrote it.<br>**Jonathan:** fixed typos and JUnit usage. |
| `TestParkView` | **Katie:** created it.<br>**Sami:** wrote the tests for bad input to each prompt method. |
| `README.md` | **Jonathan:** made the skeleton and wrote what changed for `Attraction` and `Dequeue`, the unknowns, whether the model stood alone, and the first two new tests. Wrote *How did your design absorb the change?* and *Did the change cross model/view lines?* sections.<br>**Katie:** wrote what changed for `ParkView`.<br>**Sami:** wrote How to Run and added to the Reflection and Next Steps sections (Gen-AI assisted). Wrote the D4 sections for what changed from the D3 plan, what doesn't work yet, and the D4 Next Steps, and added the bullet points to the other two D4 Reflection sections. |

### GenAI Note

- **Jonathan:** Used Copilot to generate the class templates from the UML diagram. Used Copilot to generate `TestAttraction` and `TestDequeue`, then proofread and revised them. Used Copilot to generate the Javadocs for `Attraction` and `Dequeue`, then proofread them. Used Copilot to make a template for the attribution table.
- **Sami:** Used Claude to plan the controller methods and their pseudocode before they were implemented. Sami decided what to build, reviewed the pseudocode, and tested it by running the program. Used Claude to help write and format the README sections listed above. 
- **Katie:** No GenAI used.


