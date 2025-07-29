Project Title
SleepyHollow: Employee Recognition System — Server-side and Android Client Application

Project Overview:
SleepyHollow is a full-stack enterprise database application designed to manage an employee recognition program. The system includes server-side business logic implemented in Java (Servlet and JSP) and a native Android client app that interacts seamlessly with the backend services. This project demonstrates the design and development of a 3-tier architecture connecting a database, a server, and a mobile client.

Key Features:
- Server-Side Components
- Login Servlet: Secure employee authentication with username and password validation.
- Employee Info JSP: Retrieves employee details and total sales.
- Transactions JSP: Lists all transactions associated with an employee.
- Transaction Details JSP: Provides detailed information about a specific transaction, including products and quantities.
- Award IDs JSP: Retrieves awards earned by employees.
- Granted Details JSP: Displays award dates and locations.
- Transfer JSP: Facilitates point transfers between employees.
All server components leverage JDBC to perform optimized queries against the Oracle SleepyHollow database and return structured data formatted for efficient parsing by the Android client.

Android Client Application:
- Multi-activity Android app developed in Android Studio.
- Includes login, employee profile display (with photo), transaction browsing, award viewing, and point transfer functionalities.
- Communicates with server-side APIs via HTTP requests to fetch and update data dynamically.
- Employs intuitive UI elements such as spinners and list views to present information clearly.

Technologies Used:
- Java Servlet API and JSP
- JDBC with Oracle Database
- Apache Tomcat Server
- Android Studio (Java)
- HTTP networking and JSON-like response parsing
- Basic UI/UX design for mobile apps

How to Run:
- Deploy the sleepyhollow web folder on an Apache Tomcat server configured with the SleepyHollow Oracle database backend.
- Run the Android app in an emulator or device connected to the same network.
- The app interacts with the server-side components through specified URLs.
- Sample employee images are stored in the server’s images folder, loaded by SSN.
