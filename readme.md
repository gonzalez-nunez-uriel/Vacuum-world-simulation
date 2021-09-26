This is an implementation of the simulation of the vacuum world mentioned in
*Artificial Intelligence: a modern approach* by Peter Norvig and Stuart J. Russell.
This was created during a course in artificial intelligence at UTRGV.

The code implements two types of agent: one that has an strategy and one that
behaves randomly. The goal of the vacuum robots is to clean all the dirt in the
room provided. The robot must maneuver through obstacles in order to reach the
dirty tiles in the room. An obstacle is represented using a filled white square,
a dirty tile is represented by an X, and the vacuum is represented by an @.
The room is surrounded by obstacles to represent the walls (a form of abstraction
reuse). The strategies for the strategic agent are hardcoded into the implementation.
