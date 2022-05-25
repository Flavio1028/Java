package br.com.codeup.social.rest;

import br.com.codeup.social.rest.dto.CreateUserRequest;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @TestHTTPResource("/users")
    URL apiURL;
    @Test
    @DisplayName("Criar um usuario com sucesso.")
    @Order(1)
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

    @Test
    @DisplayName("Criar um usuario com erro.")
    @Order(2)
    public void createUserValidationErrorTest() {

        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                .when()
                        .post(apiURL)
                .then()
                        .extract().response();

        assertEquals(422, response.statusCode());
        assertEquals("Validation Error", response.jsonPath().getString("message"));

        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));
    }

    @Test
    @DisplayName("Listar todos os usuarios.")
    @Order(3)
    public void listAllUsersTest() {

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(apiURL)
        .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }

}