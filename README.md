# User Manual

## How to Use the Application

Follow these steps to get started with the application:

### 1. Clone the Repository

First, clone the repository from GitHub to your local machine. Open your terminal and run:

```bash

git clone <repository-url>

 ```
### 2. Configure Your Database

You need to configure the application to connect to your MySQL database. Follow these steps:

1. Open the application.properties file located in the src/main/resources directory of your project.

2. Edit the database connection settings. Update the following properties with your MySQL server details:

```properties
spring.datasource.url=jdbc:mysql://<database-url>:<port>/<database-name>
spring.datasource.username=<your-database-username>
spring.datasource.password=<your-database-password>
 ```
Replace <data> with your database credentials.

### 3. Run the Application

After configuring the database, you can start the application.

Open your web browser and go to the following URL:

```plaintext
  http://localhost:8080
 ```

The application should be running, and you will be able to access its features.
