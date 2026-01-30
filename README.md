🏦 Community Donation Campaign, Donor & Pledge Tracking System (JDBC)

A console-based Java application designed for non-profit organizations to manage donation campaigns, donors, pledges, and payments using JDBC with Oracle Database.
The system strictly separates master data (donors and campaigns) from transactional data (pledges and payments) and enforces strong business rules to maintain data integrity.

📌 Project Overview

This application helps a non-profit organization:

Register and manage donors

Create and manage donation campaigns

Record pledges and payments securely

Prevent invalid operations using business validations

Maintain consistent transactional data using JDBC transactions

The project follows a layered architecture with clear separation of concerns:

Presentation Layer (Console)
        ↓
Service Layer (Business Logic)
        ↓
DAO Layer (Database Access)
        ↓
Oracle Database

⚙️ Technologies Used

Java (JDK 8+)

JDBC

Oracle Database (XEPDB1 / 21c)

SQL

Eclipse / IntelliJ IDEA

🧩 Modules & Responsibilities
1️⃣ Donor Management (Master Data)

Register new donors with validation

View donor details

List all donors

Remove donor only when no active pledges exist

2️⃣ Campaign Management (Master Data)

Create donation campaigns

View campaign details

List all campaigns

Close campaigns only after pledge validation

3️⃣ Pledge & Payment Management (Transactional Data)

Record pledges against active campaigns

Record payments against pledges

Track partial and full payments

Enforce atomic transactions (commit/rollback)

🔐 Business Rules Enforced
Rule	Description
Donor must exist	Pledge cannot be created without valid donor
Campaign must be ACTIVE	Pledges only allowed for active campaigns
No overpayment	Paid amount cannot exceed pledge amount
Transaction safety	Payments and pledges use commit/rollback
Campaign close validation	All pledges must be settled or written off
Safe donor removal	Donor can be deleted only when no active pledges exist
🚀 Supported Operations

Register New Donor

View Donor Details / View All Donors

Create Donation Campaign

View Campaign Details / View All Campaigns

Record Donor Pledge (Transactional)

Record Payment against Pledge (Transactional)

List Pledges by Donor

List Pledges by Campaign

Close Campaign (with validation)

Remove Donor (safe delete only)

🗄️ Database Schema
DONOR_TBL

Stores donor master data.

CAMPAIGN_TBL

Stores campaign master data.

PLEDGE_TBL

Stores transactional pledge and payment data.

A sequence (pledgeId_seql) is used to generate unique pledge IDs.

🔄 Transaction Handling

Critical operations such as pledge recording and payment updates are handled using JDBC transactions:

connection.setAutoCommit(false);
commit();   // on success
rollback(); // on failure


This ensures no partial or inconsistent updates are stored.

🧪 How to Run the Project

Create Oracle DB schema using the provided SQL script

Update database credentials in DBUtil.java

Compile and run DonateMain.java

View console output for operation status

Verify changes directly in Oracle DB

📂 Project Structure

com.donate
│
├── app        → Main console application
├── bean       → Entity classes
├── dao        → Data Access Objects
├── service    → Business logic & validations
└── util       → DB utility & custom exceptions

🎯 Learning Outcomes

Hands-on JDBC transaction management

Layered architecture design

Real-world business rule validation

Oracle DB integration

Exception handling & safe data operations

Clean separation of master and transactional data


