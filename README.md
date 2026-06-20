# Freelance-Job-Portal-App
Java OOP Freelance Job Portal with Console and Swing GUI
# Freelance Job Portal App

## Overview

Freelance Job Portal is a Java-based Object-Oriented Programming (OOP) project that allows clients to post jobs and freelancers to view available jobs. The project includes both a Console Interface and a Java Swing GUI Interface.

## Features

### Client

* Register an account
* Login to the system
* Post freelance jobs
* View posted jobs
* Logout

### Freelancer

* Register an account
* Login to the system
* View available jobs
* Logout

### GUI Features

* Login Screen
* Registration Screen
* Client Dashboard
* Freelancer Dashboard
* Job Posting Interface
* Job Viewing Interface

## OOP Concepts Used

### Abstraction

* Implemented using the abstract `User` class.

### Inheritance

* `Client` and `Freelancer` classes inherit from the `User` class.

### Polymorphism

* The `dashboard()` method is overridden in both `Client` and `Freelancer` classes.

### Encapsulation

* Job details are stored in the `Job` class using private attributes and public getter methods.

## Technologies Used

* Java
* Java Swing
* ArrayList
* OOP Principles

## Project Structure

```text
FreelanceJobPortalConsole.java
FreelanceJobPortalGUI.java
README.md
```

## How to Run

### Compile

```bash
javac FreelanceJobPortalConsole.java
javac FreelanceJobPortalGUI.java
```

### Run

```bash
java FreelanceJobPortalConsole
```

## Future Improvements

* Database Integration
* Profile Management
* Job Applications
* Payment System
* Search and Filter Jobs
* Admin Panel

## Author

Fatima Ahsan

BS Computer Science Student
