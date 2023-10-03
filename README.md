# CS 330 Software Design and Development - Fall 2023

## Programming Exercise (PEX) 2: Mini-MASSIVE Battle Simulation

**Due Date**: 16 Oct 2023

### Learning Objectives

- Launch an application object from the `static main()` method.
- Design a Java program with collaborating classes using inheritance, association, and aggregation.
- Understand instance and static attributes usage.
- Implement appropriate Java data structures and error handling.
- Animate graphics using DrawingPanel and capture keyboard/mouse inputs.
- Incorporate `Vector330` class from PEX1.
- Comprehensively document Java code to produce JavaDoc.

### Introduction

In this exercise, we're creating a mini-version of the MASSIVE software that powered the battle scenes in the Lord of the Rings movies. We'll simulate many-on-many battles, where warriors from two armies observe their environment and react.

For more on MASSIVE's applications, visit [massivesoftware.com](http://www.massivesoftware.com/).

### Functional Requirements

1. Create a `Battle` class that's instantiated and run from a `Main` class.
2. Display the battle in a resizable `DrawingPanel` window.
3. Include at least two distinct armies with varying warrior attributes.
4. Position each warrior randomly based on their army.
5. Initialize the simulation and await user prompt to start.
6. Display and update live warrior count and provide termination instructions.
7. Warriors should move towards their adversaries and fight when close enough.
8. Warriors falling off the battlefield perish.
9. Terminate simulation if the user left-clicks or an army is eliminated.
10. Pause and resume simulation using the spacebar.

### Non-Functional Requirements

1. Minimum of seven Java classes: `Main`, `Battle`, `Army`, `Warrior`, a sub-class of `Warrior`, `Vector330`, and `DrawingPanel`.
2. Utilize `Vector330` for warriors' positions, velocities, and calculating distances.
3. Implement phased approaches in the battle for fairness.
4. Generate a comprehensive JavaDoc site.
5. Adhere to standard Java programming conventions.

### Bonus Points

Up to 5 bonus points can be earned for creative enhancements, such as adding images, animations, sound effects, and more.

### Hints

- Carefully design your classes.
- Implement incrementally and test at each stage.
- Backup your work periodically.
- Use an array or ArrayList for aggregating warriors.

### Submission Instructions

- Document your work in the header comments of the `Main` class.
- Submit your PEX2 IntelliJ project via Blackboard. GitHub submissions are not required.
- Optionally, send a zip of your PEX2 files via a Teams Chat message for backup.

### Disclaimer

**AUTHORIZED RESOURCES**: Course materials, online Java references, AI code generators, and your instructor. Consultation with fellow cadets is allowed, but code sharing is prohibited. Document all help received, especially from AI tools like GitHub CoPilot or ChatGPT.

Remember, the goal is to learn crucial software development skills. Ensure you understand the help you receive to perform well in formal assessments.
