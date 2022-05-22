package br.com.codeup.social.rest;

import br.com.codeup.social.rest.dto.CreateUserRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
class UserResourceTest {

    @Test
    @DisplayName("Criar um usuario com sucesso.")
    public void createUserTest() {

        var user = new CreateUserRequest();
        user.setName("Cliente teste");
        user.setAge(20);

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                .when()
                        .post("/users")
                .then()
                        .extract().response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

}