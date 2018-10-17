# RideForce Matching Service

This service handles the following endpoints (see the API documentation in
the gateway service repo for all endpoints and their explanations):






## Environment variables

Environment variables are used for sensitive data that should not be exposed
in the public Git repository. The following is a comprehensive list of all
environment variables that are necessary for proper program execution:

- `JDBC_URL`: the database url
- `JDBC_USERNAME`: the database username
- `JDBC_PASSWORD`: the database password
- `USER_SERVICE_USERNAME`: The username for authenticating a User Service Request
- `USER_SERVICE_PASSWORD`: The password for authenticating a User Service Request
