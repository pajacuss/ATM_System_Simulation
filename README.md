# ATM System Simulation

The ATM System Simulation project is a Java application designed to provide similar functionalities to this of an Automated Teller Machine. Besides the ATM features, the system offers intuitive GUI to help navigate through the app.
  
## Key Features
* **Account Signup**: Creating a new bank account effortlessly by inputing your personal data and contact information.
* **Account information**: Displaying users card number, name and balance.
* **Transactions**: Making deposits and withdrawls.
* **Transactions history**: Show 5 most recent transactions including the transactions type, transaction value and the date of transaction.

## Getting started
To run this application folow these steps:
* Clone this repository or download it as a ZIP archive.
* Navigate to the directory in your terminal.
* Build and run the project using following commands:

```./gradlew build```

```./graldew run```
## Usage
1. Launch the app using ```./graldew run``` in terminal,
2. If you haven't already, create an account by clicking the "Sign Up" button and filling out the form,
3. Log in using your card number and pin code,
4. Use account-related functions.

## Screenshots
![signupapp](https://github.com/user-attachments/assets/99abb0b5-21a9-424e-a6ec-340d9f1a0f6e)
![bankstatementapp](https://github.com/user-attachments/assets/ad8cc148-b2d2-4741-b092-f6d387cc2792) ![loginapp](https://github.com/user-attachments/assets/32437981-24e3-4ba8-a02f-c19c561d0f0d)

## Database
To use this project you probably should make a similar database to this that I'm using.

Here are the steps I would take:
* Download [mySQL](https://dev.mysql.com/downloads/installer/).
* Set up your server.
* Make a scheme similar to this one:
  
![Scheme](https://github.com/user-attachments/assets/6bf0458c-6514-4e80-970c-f15fa261b1e7)

![Bank Table](https://github.com/user-attachments/assets/ec55bf62-c195-4c56-b4c9-3509d3dcbf3f)
![Login Table](https://github.com/user-attachments/assets/0bb37c49-c272-42cf-bc7c-a197d1680101)
![Userdata Table](https://github.com/user-attachments/assets/aa8c9ce7-17e4-40d4-af1c-4c4703e413f3)


* In ```Connector.java```:
  * In connectionString change "atmdatabase" to the name of your scheme (if you chose diffrent port you should change the "3306" too),
  * In conn change "root" to your username, and "root123" to your password.

