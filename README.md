# Expense Tracker Application

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


### User Service for Expense Tracker Application

The User Service handles user management, including user creation, updates, and retrieval. It integrates with Kafka for consuming user events and MySQL for data persistence.

#### Key Features:
- **Kafka Consumer**: Listens to user events and updates or creates user records in the database.
- **REST API**: Provides endpoints for creating/updating and retrieving user information.
- **Custom Deserializer**: Deserializes user information from Kafka messages.

### Project Structure

1. **AuthServiceConsumer**:
    - Consumes messages from Kafka and processes user data.
    - Uses `UserService` to create or update user records.

2. **UserController**:
    - REST API endpoints for user operations.
    - **Endpoints**:
        - `POST /user/v1/CreateUpdate`: Creates or updates user information.
        - `GET /user/v1/getUser`: Retrieves user information.

3. **UserInfoDeserializer**:
    - Custom deserializer for converting Kafka messages to `UserInfoDTO` objects.

4. **UserService**:
    - Core business logic for user operations.
    - **Methods**:
        - `createOrUpdateUser(UserInfoDTO)`: Creates or updates a user.
        - `getUser(UserInfoDTO)`: Retrieves user information.

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
    cd expense-tracker-app/userservice
    ```

2. **Build and Run**:
    ```sh
    ./gradlew clean build
    ./gradlew bootRun
    ```

### Configuration Properties

```properties
spring.kafka.bootstrap-servers=192.168.56.101:9092
spring.kafka.consumer.group-id=userinfo-consumer-group
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.expenseTracker.userService.deserializer.UserInfoDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
spring.kafka.topic-json.name=user_service
spring.kafka.consumer.properties.spring.json.type.mapping=org.expenseTracker.userService.entities.UserInfoDTO

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:192.168.56.101}:${MYSQL_PORT:3306}/${MYSQL_DB:userservice}?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
spring.datasource.username=tatha
spring.datasource.password=password
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
server.port=9810
logging.level.org.springframework=DEBUG
logging.level.org.hibernate=DEBUG
logging.level.root=DEBUG
```

### License
This project is licensed under the MIT License.

For more details, refer to the project repository: [Expense Tracker AuthService](https://github.com/tathagata1010/expense-tracker-app).
