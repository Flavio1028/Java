package br.com.codeup.social.rest;

import br.com.codeup.social.domain.model.Follower;
import br.com.codeup.social.domain.model.User;
import br.com.codeup.social.domain.repository.FollowerRepository;
import br.com.codeup.social.domain.repository.UserRepository;
import br.com.codeup.social.rest.dto.FollowerRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FollowerResourceTest {

    @Inject
    FollowerRepository repository;
    @Inject
    UserRepository userRepository;
    Long userId;

    Long followerId;

    @BeforeEach
    @Transactional
    void setUP() {

        //usuario padrão dos testes
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();

        var follower = new User();
        follower.setAge(30);
        follower.setName("Ciclano");
        userRepository.persist(follower);
        followerId = follower.getId();

        // Cria seguidor
        var followerEntity = new Follower();
        followerEntity.setFollower(follower);
        followerEntity.setUser(user);
        repository.persist(followerEntity);

    }

    @Test
    @Order(1)
    public void saveUserAsFollowerTest() {

        var body = new FollowerRequest();
        body.setFollowerId(userId);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", userId)
        .when()
                .put()
        .then()
                .statusCode(409)
                .body(Matchers.is("You can't follow yourself"));
    }

    @Test
    @Order(2)
    public void userNotFoundFollowTest() {

        var body = new FollowerRequest();
        var idTest = 999;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", idTest)
                .when()
                .put()
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(3)
    public void FollowerUserTest() {

        var body = new FollowerRequest();
        body.setFollowerId(followerId);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .pathParam("userId", userId)
        .when()
                .put()
        .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(5)
    public void userNotFoundListTest() {

        var idTest = 999;

        given()
                .contentType(ContentType.JSON)
                .pathParam("userId", idTest)
                .when()
        .get()
                .then()
        .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(4)
    public void listFollowersTest() {
        var response =
        given()
                .contentType(ContentType.JSON)
                .pathParam("userId", userId)
        .when()
                .get()
        .then()
                .extract().response();

        var cont = response.jsonPath().get("followersCount");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals(1, cont);

    }

    @Test
    @Order(6)
    public void userNotFoundDeleteTest() {

        var idTest = 999;

        given()
                .pathParam("userId", idTest)
                .queryParam("followerId", followerId)
        .when()
                .delete()
        .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(6)
    public void followerDeleteTest() {

        given()
                .pathParam("userId", userId)
                .queryParam("followerId", followerId)
        .when()
                .delete()
        .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

}