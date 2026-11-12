# Theme Park Queue System (cs2114-project3-91)

## How to Run

Run these commands from the project folder.

**Compile everything:**

```
javac -d out -cp lib/junit-platform-console-standalone-1.10.2.jar classes/*.java tests/*.java
```

**Run the model tests:** The tests only use the model classes, so they do not need the view or the controller. All 41 tests should pass.

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
4. Enter `9` a few times to add visitors to its line.
5. Enter `2` to see the ride's details.
6. Enter `10` to let time pass, then `2` again to see the line get shorter.
7. Enter `0` to quit.



## Reflection



### What changed from our specification?

- **Attraction:** A method that was not present in the specification for this class was `Tick(float)`. We added this method to allow the attractions to simulate time, something which we forgot to account for in specification.
- **Dequeue:** An additional method that wasn't included in specification was `find(Object)`. We added this method to see if an object was inside of the Dequeue, and if so, its index as well.
- **ParkView:** A method that was not present in the specification for this class was `showMessage(String)`. We added this method to allow other classes to easily display messages to the user without having the rewrite the code. In our spec, we also had `showAttractionDetails(Attraction)` taking in user input, but we moved it to controller to keep the model/view boundary.
- **UserMenu:** The spec had seven menu actions. We added three more: add a visitor, advance time, and quit. Without them there was no way to put people in a line or let rides run, so the MVP could not be shown. UserMenu also now asks for the attraction ID, since the view cannot look up an ID without seeing the park.
- **Attraction:** We also added `getLineSize()`. The spec's model/view boundary listed it, but it was never built. Without it, the view would have needed the `Dequeue` to count the people in line.
- **Dequeue:** We also added `clear()`. Attraction uses it to empty the line when a ride closes.
- **Where validation lives:** The spec said `UserMenu.promptIntRange()` checks for bad numbers. That method is in `ParkView` instead, since reading input is the view's job. The spec also put rule checks in `Park`, but `Park` does not yet reject duplicate ride names or visitors while the park is closed, so `UserMenu` checks those for now. `UserMenu` also checks whether the park is already open or closed, because `Park.setActive()` does not return a result.
- **Tests:** We added two tests to `TestAttraction`. One tests `getLineSize()`. The other tests wait times when a ride is not running, after we found and fixed a bug there. The spec planned tests for every class, but `TestParkView` is still empty and there is no `TestUserMenu` yet.



### Did the model really stand alone?

Mostly. The model classes do not know about the view or the controller, and all of the model tests run without them. The view never changes the model. Only the controller does.

A few rules did end up in the controller instead of the model, because the model did not handle them yet:

- Checking for duplicate ride names
- Checking whether the park or a ride is already open or closed
- Checking that the park is open before adding a visitor
- The limits for capacity, ride length, and wait time, which are copied from `Park`

Also, `Attraction.getVisitors()` still returns the real line, so any class that calls it could change the line. We added `getLineSize()` so the view does not need it.

### What happened to our unknowns?

- **Time Simulation:** Figured out. The manager lets time pass with the "Advance time" menu option, and each ride moves forward one minute at a time. We chose this over a real clock because the screen only changes when the user picks an option, and a clock running in the background would print in the middle of the menu.
- **Calculating Wait Times:** We figured this out by implementing a `find` method into `Dequeue` that allows us to find the position of a visitor. We then used their position to figure out how many rounds it will take before they can board the ride. We then used that number to calculate time using the time of the current round and future rounds.
- **Arriving Visitors:** Partly figured out. For now, the manager adds each visitor through the menu. We still plan to have visitors arrive on their own as time passes, but `VISITOR_INTERVAL` in `Main` is not used yet.
- **Visitor Groups:** We are still determining how to cleanly implement visitor groups if we continue adding it as a stretch feature.



### What doesn't work yet?

- Visitors do not leave the line when their maximum wait runs out. They only check the wait once, when they try to join. Our revised scope said they would leave.
- The wait estimate ignores the rule that a ride only starts when at least half of its seats would be filled. A visitor can be told "0 minutes" and then wait until more people join.
- The minute a ride boards is not counted toward the ride, so simulated time runs a little slow.
- Pressing Ctrl+Z or Ctrl+D ends the input and crashes the program.
- `TestParkView` is empty and there is no `TestUserMenu`.
- There is no menu option to show visitors, and the `Statistic` class is not used yet.



## Next Steps



### What is left to finish the MVP

1. Make visitors leave the line when their max wait runs out.
2. Make the wait estimate match the boarding rule.
3. Move the duplicate-name and park-open checks into `Park`.
4. Add `TestUserMenu` and fill in `TestParkView`.



### Our change feature

**Fast pass lane:** A second line at each ride for fast pass holders. Each round fills a set share of seats from the fast pass line first, then the rest from the regular line.

- **Will change:**
  - `Visitor`: a fast-pass field and getter.
  - `Park.addVisitor`: a fast-pass parameter.
  - `Attraction`: a second line, `addVisitor` choosing which line, `tick` boarding the fast-pass share first, and both `remainingTime` methods.
  - `UserMenu.addVisitor`: a Y/N fast-pass question.
  - `ParkView.showAttractionDetails`: showing both lines.
- **Should not change:** `Dequeue`, `Statistic`, `Main`, `UserMenu.run`, and the ParkView prompt methods.



### What stretch goals we will attempt?

- **Attempting:** fast pass (it's the change feature) and ride statistics (the `Statistic` class is already built for this).
- **Dropping:** visitor groups. That's still an open unknown, and it breaks first-in-first-out order.
- **Moved to the MVP:** visitors leaving the line when the wait is too long, since our revised scope added it.



### What new tests will we need?

- **Time Tests:** Now that time is an element in our program, we have to make sure that the classes continue to work as time passes.
- **IllegalArgumentExceptions:** Most of the newly added methods from the specification document are public, meaning we have to make sure that they do not accept any values that may break the program.
- **TestUserMenu:** Scripted input covering the MVP path and the 4 bad-input cases from the scope.
- **TestParkView:** Bad input to the prompt methods.
- **Fast pass:** Sending visitors to the right line, and the boarding split between the two lines.
- **Leaving the line:** Visitors leave once their max wait runs out.



### Who does what?


| Member           | Task                                                                                                          |
| ---------------- | ------------------------------------------------------------------------------------------------------------- |
| Jonathan Ganun   | The Attraction changes: visitors leaving the line, the wait estimate fix, and the fast pass line and boarding |
| Benjamin Maloney | The Park and Visitor changes: the duplicate-name and park-open checks, the fast pass field, and statistics    |
| Sami Saleh       | The UserMenu options for fast pass and statistics, `TestUserMenu`, and Main                                   |
| Katie Smith      | ParkView display for both lines and statistics, and `TestParkView`                                            |




## Attributions

| File | Who worked on it and how |
| --- | --- |
| `Attraction` | Jonathan: wrote it, including `tick()` for time (GenAI-assisted).<br>Sami: revised it by adding `getLineSize()` and fixing the wait time when a ride is not running (GenAI-assisted). |
| `Dequeue` (and its `Node` class) | Jonathan: wrote it and fixed its bugs (GenAI-assisted). |
| `Park` | Jonathan: made the starting template (GenAI-assisted).<br>Ben: wrote it. |
| `Visitor` | Jonathan: made the starting template (GenAI-assisted) and wrote the getters.<br>Ben: revised it and added `equals()`. |
| `Statistic` | Jonathan: made the starting template (GenAI-assisted).<br>Ben: wrote it. |
| `ParkView` | Jonathan: made the starting template (GenAI-assisted).<br>Katie: wrote it, including the display and prompt methods.<br>Sami: revised the menu numbers, the attraction display, and the number prompts, and made yes/no accept lowercase (GenAI-assisted). |
| `UserMenu` | Jonathan: made the starting template (GenAI-assisted).<br>Sami: wrote the menu loop and all 10 menu options, and tested them by running the program (GenAI-assisted). |
| `Main` | Jonathan: made the starting template (GenAI-assisted).<br>Sami: finished and documented it (GenAI-assisted). |
| `TestAttraction` | Jonathan: wrote it (GenAI-assisted).<br>Sami: added two tests (GenAI-assisted). |
| `TestDequeue` | Jonathan: wrote it (GenAI-assisted). |
| `TestPark` | Ben: wrote it.<br>Jonathan: fixed typos and JUnit usage. |
| `TestVisitor` | Ben: wrote it.<br>Jonathan: fixed typos and JUnit usage. |
| `TestStatistic` | Ben: wrote it.<br>Jonathan: fixed typos and JUnit usage. |
| `TestParkView` | Katie: created it. It has no tests yet. |
| `README.md` | Jonathan: made the skeleton and wrote what changed for `Attraction` and `Dequeue`, the unknowns, whether the model stood alone, and the first two new tests.<br>Katie: wrote what changed for `ParkView`.<br>Sami: wrote How to Run and added to the Reflection and Next Steps sections (GenAI-assisted). |

### GenAI Note

- **Jonathan:** Used Copilot to generate the class templates from the UML diagram. Used Copilot to generate `TestAttraction` and `TestDequeue`, then proofread and revised them. Used Copilot to generate the Javadocs for `Attraction` and `Dequeue`, then proofread them. Used Copilot to make a template for the attribution table.
- **Sami:** Used Claude to plan the controller methods and their pseudocode before they were implemented. Sami decided what to build, reviewed the pseudocode, and tested it by running the program. Used Claude to help write and format the README sections listed above.
- **Ben:** To be filled in by Ben.
- **Katie:** To be filled in by Katie.


