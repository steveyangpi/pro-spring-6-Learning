package com.apress.prospring6.seventeen.controllers;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Disabled("Deploy the war to Apache Tomcat and then enable this test")
public class SingerControllerTest {

    @BeforeEach
    void setUp(){
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void johnShouldNotSeeTheDeleteButton(){
        var cfg = new FormAuthConfig("/ch17/auth","user","pass")
                .withLoggingEnabled();

        String responseStr = given()
                .contentType(ContentType.URLENC)
                .auth().form("john","doe",cfg)
                .when().get("/ch17/singer/1")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().asString();

        assertAll(
                () -> assertTrue(responseStr.contains("<div class=\"card-header\">Singer Details</div>")),
                () -> assertTrue(responseStr.contains("<td>Mayer</td>")),
                () -> assertFalse(responseStr.contains("Delete"))
        );
    }

    @Test
    void johnShouldNotBeAllowedToDeleteSinger(){
        var cfg = new FormAuthConfig("/ch17/auth","user","pass")
                .withLoggingEnabled();

        String responseStr = given()
                .contentType(ContentType.URLENC)
                .auth().form("john","doe",cfg)
                .when().delete("/ch17/singer/1")
                .then()
                .assertThat().statusCode(HttpStatus.FORBIDDEN.value())
                .extract().body().asString();
    }

    @Test
    void adminShouldSeeTheDeleteButton(){
        var cfg = new FormAuthConfig("/ch17/auth","user","pass")
                .withLoggingEnabled();

        String responseStr = given()
                .contentType(ContentType.URLENC)
                .auth().form("admin","admin",cfg)
                .when().get("/ch17/singer/1")
                .then()
                .assertThat().statusCode(HttpStatus.OK.value())
                .extract().body().asString();

        assertAll(
                () -> assertTrue(responseStr.contains("<div class=\"card-header\">Singer Details</div>")),
                () -> assertTrue(responseStr.contains("<td>Mayer</td>")),
                () -> assertTrue(responseStr.contains("Delete"))
        );
    }
}
