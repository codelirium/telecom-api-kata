# Telecom API
A hypothetical lean skeleton of a service for a telecom company.

Instructions
-
* **Compile:** `$ mvn clean package`

* **Run:** `$ java -jar target/telecom-api-kata.jar`


Endpoints
-
* All the phone details can be retrieved.
  * **GET:** `curl -X GET http://localhost:8080/api/telecom/phones`

* All the phone details can be retrieved for a specific user.
  * **GET:** `curl -X GET http://localhost:8080/api/telecom/phones?customerId=12345`

* A phone account can be activated.
  * **POST** `curl -X POST http://localhost:8080/api/telecom/phones/1234567890/activations`
