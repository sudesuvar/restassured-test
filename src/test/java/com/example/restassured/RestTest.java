package com.example.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getPostById() {
        Response response = given()
                .queryParam("sample", "value") // örnek query param, zorunlu değil
                .log().all()
                .when()
                .get("/posts/1");

        System.out.println("=== GET RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Time: " + response.getTime() + "ms");
        System.out.println("Body:\n" + response.getBody().asPrettyString());

        response.then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .time(lessThan(1000L));
    }

    @Test
    public void loginMockPostRequest() {
        Response response = given()
                .contentType("application/json")
                .body("{ \"title\": \"test title\", \"body\": \"test body\", \"userId\": 1 }")
                .log().all()
                .when()
                .post("/posts");

        System.out.println("=== POST RESPONSE ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Time: " + response.getTime() + "ms");
        System.out.println("Body:\n" + response.getBody().asPrettyString());

        response.then()
                .statusCode(201)
                .body("title", equalTo("test title"))
                .body("body", equalTo("test body"))
                .body("userId", equalTo(1))
                .time(lessThan(1000L));
    }

}
