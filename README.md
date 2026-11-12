# Theme Park Queue System (cs2114-project3-91)

## Reflection
### What changed from our specification?
<ul>
    <li><b>Attraction: </b>A method that was not present in the specification for this class was <code>Tick(float)</code>. We added this method to allow the attractions to simulate time, something which we forgot to account for in specification.</li>
    <li><b>Dequeue: </b>An additional method that wasn't included in specification was <code>find(Object)</code>. We added this method to see if an object was inside of the Dequeue, and if so, its index as well.</li>
</ul>

### Did the model really stand alone?
### What happened to our unknowns?
<ul>
    <li><b>Time Simulation: </b>We are still determining whether to let time pass passively as the program runs or if the manager should run a command to simulate time.</li>
    <li><b>Calculating Wait Times: </b>We figured this out by implementing a `find` method into `Dequeue` that allows us to find the position of a visitor. We then used their position to figure out how many rounds it will take before they can board the ride. We then used that number to calculate time using the time of the current round and future rounds.</li>
    <li><b>Arriving Visitors: </b>We determined that the `Main` class will be responsible for adding the visitors with time as long as the park is active and below its maximum capacity. </li>
    <li><b>Visitor Groups: </b>We are still determining how to cleanly implement visitor groups if we continue adding it as a stretch feature.</li>
</ul>

### What doesn't work yet?

## Next Steps
### What is left to finish the MVP
### Our change feature
### What stretch goals we will attempt?
### What new tests will we need?
<ul>
    <li><b>Time Tests: </b>Now that time is an element in our program, we have to make sure that the classes continue to work as time passes.</li>
    <li><b>IllegalArgumentExceptions: </b>Most of the newly added methods from the specification document are public, meaning we have to make sure that they do not accept any values that may break the program.</li>
</ul>

### Who does what?
| Member | Task |
| --- | --- |
| Jonathan Ganun |  |
| Benjamin Maloney |  |
| Sami Saleh |  |
| Katie Smith |  |

## Attributions

| Member | Contributions | AI Disclosure |
| --- | --- | --- |
| Jonathan Ganun | <ul><li>Created the boilerplates for all of the classes.</li><li>`Attraction` and `TestAttraction`</li><li>`Dequeue` and `TestDequeue`</li><li>Created `README` skeleton</li><li>Added what changed from specification for `Attraction` and `Dequeue`.</li><li>Added <b>Time Tests</b> and <b>IllegalArgumentExceptions</b> to the <i>"What new tests will we need?"</i> section.</li> <li>Wrote the <i>"What happened to our unknowns"</i> section.</li></ul> | <ul><li>Used Copilot to generate boilerplates based on UML diagram.</li><li>`TestAttraction` and `TestDequeue` were generated with Copilot with proofreading and revisions.</li><li>JavaDocs for `Attraction` and `Dequeue` were generated with Copilot and proofread.</li><li>Used Copilot to generate a template table for the Attributions section of the `README`.</li></ul> |
| Member 2 | <ul><li>Contribution</li></ul> | <ul><li>AI disclosure</li></ul> |
| Member 3 | <ul><li>Contribution</li></ul> | <ul><li>AI disclosure</li></ul> |
| Member 4 | <ul><li>Contribution</li></ul> | <ul><li>AI disclosure</li></ul> |
