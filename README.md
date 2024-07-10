### AuthService for Expense Tracker Application

This Spring Boot-based authentication service secures user login and registration using JWT tokens, handling both access and refresh tokens.

#### Key Features:

- **User Authentication**: Secure login and registration.

- **JWT Tokens**: Access and refresh tokens for session management.

- **Password Security**: Secure password storage with BCrypt.

### Project Structure

1\. **JwtService**:

    - Handles JWT creation, validation, and claims extraction.

    - Uses a static secret key for signing tokens.

2\. **RefreshTokenService**:

    - Manages creation, verification, and expiration of refresh tokens.

    - Uses a repository to persist tokens.

3\. **UserDetailServiceImpl**:

    - Implements `UserDetailsService` for loading user-specific data.

    - Validates user registration details and password encoding.

    - Publishes user events to Kafka for further processing.

4\. **UserInfoProducer**:

    - Publishes user events to Kafka using a custom serializer.

### Configuration

- **Database**: Configured with MySQL properties for user and token storage.

- **Kafka**: Configured for producing user events.

### Key Endpoints

- **User Registration**: `/auth/v1/signup`

    ```json

    {

      "username": "tathan",

      "password": "P@ssw0rd!",

      "firstName": "Tathagata",

      "lastName": "Nandi",

      "phoneNumber": "6290377154",

      "email": "tatha@gmail.com"

    }

    ```

- **User Login**: `/auth/v1/login`

    ```json

    {

      "username": "tathan",

      "password": "P@ssw0rd!"

    }

    ```

- **Refresh Token**: `/auth/v1/refreshToken`

    ```json

    {

      "refreshToken": "your_jwt_refresh_token"

    }

    ```

### Sample `application.yml` Configuration

```yaml

spring:

  datasource:

    url: jdbc:mysql://localhost:3306/your_database

    username: your_username

    password: your_password

  jpa:

    hibernate:

      ddl-auto: update

    show-sql: true

jwt:

  secret: your_jwt_secret

  expiration: 3600 # Access token expiration in seconds

  refresh_expiration: 86400 # Refresh token expiration in seconds

server:

  port: 9898

spring.kafka:

  producer:

    bootstrap-servers: 192.168.56.101:9092

    value-serializer: org.expenseTracker.authService.serializer.UserDetailsSerializer

  topic-json:

    name: user_service

```

### Running the Application

1\. **Clone and Navigate**:

    ```sh

    git clone https://github.com/tathagata1010/expense-tracker-app.git

    cd expense-tracker-app/authservice

    ```

2\. **Build and Run**:

    ```sh

    mvn clean install

    mvn spring-boot:run

    ```

### License

This project is licensed under the MIT License.

For more details, refer to the project repository: [Expense Tracker AuthService](https://github.com/tathagata1010/expense-tracker-app).
