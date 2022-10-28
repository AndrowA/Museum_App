# :art: Museum Software System 
Welcome to ECSE321 by Project Group 16. 

Here you will find a brief description of the project, overview table with names, team roles, and individual efforts along with a project report.

## Project reports
Click here to view [Deliverable 1 Project wiki](../../wiki/Deliverable-1).

## Project description and scope
The project consists in designing and implementing a Museum Software System for a customer that will allow museum visitors to buy passes and loan items online. It should also allow for managers and employees to view their schedules, approve loans (if clearance permits) and store artefacts in a database. The system should be accessible through a web frontend for all users and function in a proper manner.

## Key design decisions 

For the UML diagram, we created a central class called museum, in which we store the characteristics/attributes of the museum. 

The other classes represent the other components of the museum, such as accounts, museum passes, loans and artifacts. 

For accounts, there are 2 types, a visitor and employee, we decided to make account an abstract class so that we can see all accounts in 1 table. We also decided to make wordDay a class, so that we could set the opening hours and schedules of our employees.

Below is a database diagram, which shows the primary and foreign keys of our tables, as they are used in our code. 

One of the decisions we made was also to make our Museum class transient, in order to not have a column for every class for museum since we believed this was redundant.
shady i wrote this shit
add to it whatever ur changing
cuz idk what ur gonna change so i cant write it

# Team members
| Name                | GitHub username | Team Role      |
|---------------------|-----------------|----------------|
| Hadi Ghaddar        | hadi-ghaddar    | Designer       | 
| Androw Abd El Malak | AndrowAM        | Tester         | 
| Shady Guindi        | Shadysjn        | Scrum Master   | 
| Daniel Makhlin      | DanielMakhlin   | Designer       | 
| Yassine Meliani     | yassinemeliani  | Main Developer | 
| Radu Petrescu       | PetrescuRad     | Designer       |
# Time Table Deliverable 1
| Name                | Time spent | Tasks done |
|---------------------|------------|------------|
| Hadi Ghaddar        | 10 hours   | UML, Tests, documentation | 
| Androw Abd El Malak | 8 hours    | UML, Tests, documentation | 
| Shady Guindi        | 10 hours   | UML, Tests, documentation | 
| Daniel Makhlin      | 10 hours   | UML, Tests, documentation, database setup | 
| Yassine Meliani     | 12 hours   | UML, Tests, documentation, database setup, JPA annotation | 
| Radu Petrescu       | 10 hours   | UML, Tests, documentation, database setup | 
