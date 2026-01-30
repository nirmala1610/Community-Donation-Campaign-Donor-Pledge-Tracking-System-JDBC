# Community-Donation-Campaign-Donor-Pledge-Tracking-System-JDBC
Construct a console-based Java application that enables a non-profit organization to  manage recurring donation campaigns, register donors, and track individual pledges and  payments.The system clearly separates master data (donors and campaigns) from 
transactional pledge records, and enforces business rules so that invalid deletions, over
pledging, and inconsistent payment statuses are avoided. 
The application includes the following operations: - Register New Donor - View Donor Details / View All Donors - Create Donation Campaign - View Campaign Details / View All Campaigns - Record Donor Pledge to Campaign (Transactional) - Record Payment against Pledge (Transactional) - List Pledges by Donor / by Campaign - Close Campaign (with validation) - Remove Donor (only when safe) 
Below is a brief description of each task: 
• Register New Donor: Inserts a donor master record with validated details such as 
donorID, full name, city, and preferred contact. 
• Create Donation Campaign: Defines a fundraising campaign with target amount, 
start/end dates, and status (PLANNED/ACTIVE/CLOSED). 
• Record Donor Pledge (Transactional): Validates that donor and campaign exist and 
campaign is ACTIVE; records a pledge amount and initial payment status in a single 
transaction. 
• Record Payment against Pledge (Transactional): Updates the amount paid and status 
(PARTIALLY_PAID / FULLY_PAID) atomically so that no partial updates are left in 
the database. 
• Close Campaign: Ensures all pledges are either fully paid or explicitly written off 
before moving campaign status to CLOSED. 
• Remove Donor: Prevents deletion if active pledges exist for that donor; allows removal 
only when all pledges are fully settled or written off according to organizational policy.
