package org.quarkus.api.fruit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class FruitEndpointTest {

    @Test
    public void testListAllFruit() {
        //List all, should have all 3 fruit the database has initially:
        given()
              .when().get("/fruit")
              .then()
              .statusCode(200)
              .body(
                    containsString("Cherry"),
                    containsString("Apple"),
                    containsString("Banana")
                    );

        //Delete the Cherry:
        given()
              .when().delete("/fruit/1")
              .then()
              .statusCode(204)
              ;

        //List all, cherry should be missing now:
        given()
              .when().get("/fruit")
              .then()
              .statusCode(200)
              .body(
                    not( containsString("Cherry") ),
                    containsString("Apple"),
                    containsString("Banana")
              );
    }

}
