# Quiz Maker Study Tool

## Project outline by Daniel K

The purpose of this project is to develop an application to create and use short quizzes as a tool for studying.
The main function of the program will be to add quiz questions and then later practice using them.
The intended user for this program would be someone like a student, trying to create study materials for an exam,
or an instructor trying to provide study resources for their students. The project is of interest to me because cue card style studying is something 
I personally use often, so I tried to imagine a project I would find useful when studying for an exam. Functions I want to include in this program include:

- Adding a question with multiple answers as an item
- Adding multiple questions and grouping them together (the group of questions could be called a collection or subject or class)
- Being able to work through a collection of questions and generate feedback in terms of what the user gets right and wrong
- Saving your work on a collection, so a user could see how they perform on questions in the past, and compare to how well they can answer the questions later.
- generate multiple types of quiz questions, such as multiple choice, true or false or fill in the blanks.


## User Stories

As a user, I want to be able to:
- Add a quiz question to a collection of questions
- Practice using questions I have created and recieve feedback on performance
- Store several collections of questions spanning different subjects and classes, and have that data persist through the program shutting down.
- View my history of practice on questions, seeing statistical data like whether my ability to answer questions has improved over time.
- Have the option to save my collection(s) to practice on them later, so I don't have to recreate practice material every time
- Have to option to load my collection(s) to practice on them later


# Phase 4: Task 2
- Log Events:
- New Collection added: Science
- New Question Created: TestQuestion
- New Answer added to TestQuestion: answer 1
- New Answer added to TestQuestion: answer 2
- New Answer added to TestQuestion: answer 3
- New Answer added to TestQuestion: answer 4
- New Question added to Science
- Each bullet point is a different line printed, a separate log event


# Phase 4: Task 3

Looking back at my project, there are several key aspects that I would refactor now, in order to make development easier as well as improving readability, and repetition in my code. 
When working with the Collection data, I found that I was working with it in a cumbersome manner, where I was trying to find a specific question in a list of questions. Looking back, implementing my 
Questions in each collection as a hashmap, where each question name corresponded to a question, would have made more sense for my design.
This would let me find a specific question to display or modify more easily, and I don't need to maintain order for functionality in my program.

Another point where I found a lot of repetition was in my 2 UI classes. I have several repeated fields, functions and similar structure, so if I had the time to refactor it, I would make 
it into an abstract UI class, where the 2 other UI classes would extend from. This would lessen repetition, and make it so I didn't have to redo a lot of function in my GUI.