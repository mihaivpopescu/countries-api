# Countries REST API
This project contains an API that allows searching for land routes between countries.

### Pre-requisites:
- JRE 8
- Maven
- Git

### Build and run:

1. Checkout project source code with `git clone`.

2. Build the application:
```shell script
user@user:~/countries-api$ mvn clean install
```
3. Run the application:
```shell script
user@user:~/countries-api$ mvn spring-boot:run
```
4. Start using the API by accessing: `http://localhost:8080/routing/{source}/{destination}`.




