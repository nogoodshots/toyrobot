
**Overview**

*Simulate* a *Toy Robot* that has a *position* on a *board*. The robot can be *placed*, *moved* and *turned*. It can also *report* its position on the board.

Main classes:
Robot, Board, Simulation, BoardPosition

**Design decisions**

* Use Antlr to parse commands, firstly to avoid writing text parsing code when a library can take that burden. Secondly it is easier to maintain and extend a grammar definition than a parser implementation.
* Java, my langauage of preference. The simulation is quite tightly constrained in what it does, Java is good at tightly constrained problems. IDE support is good.

**To Do List**
* Add logging - the one off cost of adding Log4j support and logging statements
* Investigate alternatives to CharStream.fromStream as this closes the input stream as soon as it is empty. Ideally a blocking read could be used and then commands can be streamed to the handler. Practically this limits the simulation to creating an input stream per input.
* Make parameterless RobotAction, like MoveAction comparable to support handling in collections. Suggest adding a timestamp to allow instances to be compared
* Consider using a Vistor pattern with the Action classes. This may be more extensible later when actions involving the board or things other than the robot.

**Build**

Requirements
* Maven
* Java 8 SDK

From the root folder of the project:
```
mvn compile dependency:copy-dependencies package
```
To run the simulation: 
```
(cd target; java -cp toyrobot-1.0-SNAPSHOT.jar:dependency/* net.nogoodshots.toyrobot.App)
```
This will read commands from StdIn. The App also accepts a file name containing commands.

**Testing**

Sample files exist in src/test/resources to illustrate 
```
(cd target; for f in `find ../src/test/resources -type f`; do echo "Running $f"; java -cp toyrobot-1.0-SNAPSHOT.jar:dependency/* net.nogoodshots.toyrobot.App $f; done)
```
