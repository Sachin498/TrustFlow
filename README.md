# User login and registration page

The application includes a user management system with registration, login, profile management, and validation logic.

## Project Structure

The project is organized into several key packages and folders:

1. `com.example`
    - `models`: Contains data classes and models used in the application.
    - `plugins`: Contains core plugins and configurations for the Ktor application.
        - `HTTP.kt`: Manages HTTP settings.
        - `Routing.kt`: Defines the application's routing structure.
        - `Security.kt`: Manages security-related features (e.g., authentication).
    - `repositories`: Contains repository logic to interact with data.
    - `services`: Contains service classes responsible for business logic.


2. `resources`
    - `static`: Contains static resources such as CSS and JavaScript files for styling and logic for client-side validation.

    - `templates`: Contains HTML templates rendered by the application.
        - `edit_profile.html`: Template for editing user profiles.
        - `login.html`: Template for the login page.
        - `profile.html`: Template for viewing user profiles.
        - `register.html`: Template for user registration.
    - `application.yaml`: Application configuration file (if present).
    - `logback.xml`: Configuration for logging.

3. `test`
    - `com.example`
      Contains test files for testing different modules of the application:
        - `ApplicationTest.kt`: Tests the application startup and core features.
        - `RepositoriesTest.kt`: Tests repository logic.
        - `RoutingTest.kt`: Tests routing functionality.
        - `ServicesTest.kt`: Tests the service layer of the application.

## Running the Application

### Prerequisites

Before you can run this application, ensure you have the following installed:

- JDK 11+
- Kotlin
- Gradle
- MySQL - Download and install from [MySQL website](https://dev.mysql.com/downloads/installer/)

### Database Configuration
In the `Application.kt` file, you need to configure the application to connect to your MySQL instance. You need to update the details of the instance like username, password and url to the instance.

### Steps to Run

1. Clone the Repository:

   ```bash
   git clone https://github.com/yourusername/yourproject.git
   cd yourproject
   ```

2. Build the Project:

    ```bash
    ./gradlew build
    ```

3. Run the Application:
    ```bash
    ./gradlew run
    ```

This will provide a link on which you can use in your web browser. 

### Application

Upon launching the application, you are greeted with a Login Page that provides options to either Log In or Register for a new account.
1. Registration Page:
   When you click Register, you are redirected to the Registration Page, where you can add your details and create your profile. Each of the fields there have their own criteria, make sure to adhere to the syntax. Upon successful registration, you will be redirected back to the Login Page. 
2. Login Page: On the Login Page, enter your credentials(user email and password) to log in. If your credentials are incorrect, you will receive an error message. After successful login, you will be taken to your Profile Page.
3. Profile Management:
   On your Profile Page, you can view all your account details and you have the following options:
   - Logout: Safely exit your session.
   - Delete Account: Permanently remove your account and all associated data.
   - Update Profile: Modify your details as needed. Ensure that updates adhere to the same validation rules as during registration.

### Running Tests

To run the unit and integration tests, use the following command:
```bash
./gradlew test
```
