package org.acme.hibernate.reactive;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.emptyString;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(TestcontainersTestResource.class)
public class FruitsEndpointTest {

    @Test
    public void testListAllFruits() {
        //List all, should have all 3 fruits the database has initially:
        given()
            .when()
            .get("/fruits")
            .then()
            .statusCode(200)
            .body(
                containsString("Cherry"),
                containsString("Apple"),
                containsString("Banana"));

        //Create the Pear:
        given()
            .when()
            .body("{\"name\" : \"Pear\"}")
            .contentType("application/json")
            .post("/fruits")
            .then()
            .statusCode(201)
            .body(
                containsString("\"id\":"),
                containsString("\"name\":\"Pear\""));
    }

}
