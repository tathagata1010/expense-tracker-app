### AuthService for Expense Tracker Application

This Spring Boot-based authentication service secures user login and registration using JWT tokens, handling both access and refresh tokens.

#### Key Features:
- **User Authentication**: Secure login and registration.
- **JWT Tokens**: Access and refresh tokens for session management.
- **Password Security**: Secure password storage with BCrypt.

### Project Structure

1. **JwtService**:
    - Handles JWT creation, validation, and claims extraction.
    - Uses a static secret key for signing tokens.
  
2. **RefreshTokenService**:
    - Manages creation, verification, and expiration of refresh tokens.
    - Uses a repository to persist tokens.
  
3. **UserDetailServiceImpl**:
    - Implements `UserDetailsService` for loading user-specific data.
    - Validates user registration details and password encoding.
    - Publishes user events to Kafka for further processing.

4. **UserInfoProducer**:
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

### Gradle Build Configuration
Here is the `build.gradle` file to configure your Gradle project:

```gradle
plugins {
    id 'application'
    id 'java'
    id 'org.springframework.boot' version '3.2.2'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'org.expenseTracker'
version = '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap:4.1.1'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.modelmapper:modelmapper:3.2.0'
    implementation 'mysql:mysql-connector-java:8.0.33'
    implementation 'org.springframework.kafka:spring-kafka:3.1.1'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    implementation 'io.jsonwebtoken:jjwt-api:0.12.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.5'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
    testImplementation platform('org.junit:junit-bom:5.9.1')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

test {
    useJUnitPlatform()
}
```

### Running the Application
1. **Clone and Navigate**:
    ```sh
    git clone https://github.com/tathagata1010/expense-tracker-app.git
    cd expense-tracker-app/authservice
    ```

2. **Build and Run**:
    ```sh
    ./gradlew clean build
    ./gradlew bootRun
    ```

### License
This project is licensed under the MIT License.

For more details, refer to the project repository: [Expense Tracker AuthService](https://github.com/tathagata1010/expense-tracker-app).
