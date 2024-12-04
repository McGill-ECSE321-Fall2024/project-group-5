# Online Game Store
## F2024 - ECSE 321: Introduction to Software Engineering
### Project Overview

In a team of 6, we are tasked with developing a web application that will allow the expansion of the independent game shop with an online store. We will do this by gathering requirements, designing a multi-tier software solution to satisfy those requirements, implementing the system, validating that the system is satisfying the requirements, and developing a release pipeline to automate the software delivery process. To simplify, we will follow the four key activities in the software engineering process: specification, development, validation and evolution. 

The main features of the web application are listed as follows:
1.	Customer Accounts: Without having to create an account, customers will be able to browse the catalog of games. While customers with accounts, will also be able to purchase games, track their order history, save items to a wish list for future purchases, and post reviews for the games they have purchased.
2.	Employee Accounts: Employees will each own an account for management purposes, allowing them to update the inventory, and request the addition and removal games as well as specify their category. 
3.	Manager Account: In addition to having all the functionalities of an employee account, manager accounts will also be able to approve requests made by employees to publish games to the catalog. They will also be able to reply to reviews.
4.	Reviews: The system will also allow the general public to pay for a parking spot, without the need of an account.

   
### Meet the Team
| Team Member | GitHub | Role | Major | Year|
|-------------|---------------|---------------| ---------------|-------------|
| Ana-Maria Floarea | [anafloarea](https://github.com/anafloarea)  | Software Developer | Mechanical Engineering | U4 |
| Alisha Malik | [alishamalik00](https://github.com/alishamalik00)  | Software Developer | Software Engineering | U2 |
| Viviane-Laura Tain   | [vivianeltain](https://github.com/vivianeltain) | Software Developer| Mechanical Engineering | U4|
| Romain Teyssier| [rom618](https://github.com/rom618) | Software Developer | Computer Engineering | U3 |
| Caroline Thom | [carolinethom2](https://github.com/carolinethom2)  | Software Developer | Mechanical Engineering | U4 |
| Reswanth Reji Pillai | [jumpman786](https://github.com/jumpman786)  | Software Developer | Software Engineering | U2 |

## Application Installation
### Prerequisites
* PostgreSQL version 17
  - username: postgres
  - password: postgres
  - port: 5432 
* Java 23
* Node.js v22.11.0 (or higher)
* Npm 10.9.0 (or higher)

### Setup Database
1. Open command line
2. Access postgres: `psql -U postgres` and enter password `postgres`
3. Create database: `create database parkinglotdb;`
4. Quit: `\q`

### Build and Start Application Bakcend on Local Machine
1. Inside the backend directory: `cd /path/to/project-group-5/GameStore-Backend`
2. Build grade: `./gradlew build`
3. Run application:
  - via Gradle: `./gradlew bootRun`
  - via an IDE: run the application from `/GameStore-Backend/src/main/java/ca/mcgill/ecse321/gamestore/GameStoreApplication.java`
4. Application should be started on `localhost:8080`

### Build and Start Application Frontend on Local Machine
0. Backend should be started first
1. Inside the frontend directory: `cd /path/to/project-group-5/GameStore-Frontend`
2. Install node modules with `npm install`
3. Start the Vite server with `npm run dev`
4. The frontend server should be started on `localhost:5173`

\* Note that this runs a development server, a production version should be built using `npm run build` and then ran using a webserver module such as `serve`

### Deliverable Contributions 
| Team Member | Deliverable 1 | Deliverable 2 |  Deliverable 3 |
|-------------|---------------|---------------| ---------------|
| Ana-Maria Floarea | 18   | 35 | 0 |
| Alisha Malik | 13   | 30 | 0 |
| Viviane-Laura Tain   | 19   | 38 | 0 |
| Romain Teyssier| 20   | 45 | 10 |
| Caroline Thom | 19   | 32 | 0 |
| Reswanth Reji Pillai | 18   | 30 | 0 |
