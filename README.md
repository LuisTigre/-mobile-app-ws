# -mobile-app-ws
# Spring Boot CRUD API with Authentication and AWS Deployment

This project is a Spring Boot application showcasing a CRUD API with user authentication, powered by Spring Security. The application has been deployed on Amazon AWS using EC2 for hosting and Elastic Beanstalk for streamlined deployment.

## Key Components

### Exceptions
- `AppExceptionHandler.java`: Global exception handler for handling application-wide exceptions.
- `UserServiceException.java`: Custom exception class for user service-related errors.

### Entities
- `AddressEntity.java`: JPA entity representing an address.
- `UserEntity.java`: JPA entity representing a user.

### Repositories
- `AddressRepository.java`: Repository for managing addresses.
- `UserRepository.java`: Repository for managing users.

### Security
- `AppProperties.java`: Configuration properties for the application.
- `AuthenticationFilter.java`: Filter for handling user authentication.
- `AuthorizationFilter.java`: Filter for handling user authorization.
- `SecurityConstants.java`: Constants related to security.
- `WebSecurity.java`: Configuration class for Spring Security.

### Services
- `AddressServiceImpl.java`: Implementation of the service for managing addresses.
- `UserServiceImpl.java`: Implementation of the service for managing users.

### Controllers
- `UserController.java`: Controller for handling user-related operations.

### Models
#### Request
- `AddressRequestModel.java`: Request model for address-related requests.
- `TaskRequestModel.java`: Request model for task-related requests.
- `UserDetailsRequestModel.java`: Request model for user details update.
- `UserLoginRequestModel.java`: Request model for user login.

#### Response
- `AddressesRest.java`: Response model for addresses.
- `ErrorMessage.java`: Model for representing error messages.
- `OperationsStatusModel.java`: Model for representing the status of operations.
- `TaskRest.java`: Response model for tasks.
- `UserRest.java`: Response model for users.

### Application
- `MobileAppWsApplication.java`: Main class for running the Spring Boot application.
- `SpringApplicationContext.java`: Utility class for accessing the Spring application context.

## AWS Deployment

The application has been deployed on Amazon AWS using EC2 for hosting and Elastic Beanstalk for simplified deployment. You can access the deployed API on the provided AWS endpoints.

## Usage

1. Configure the database properties in `application.properties`.
2. Run the application using `mvn spring-boot:run`.

Feel free to customize the project based on your specific requirements and extend functionalities as needed.

## Testing
The project includes test classes under `src/test` for testing various components.

## Contributing
Contributions are welcome! Feel free to open issues or pull requests.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
