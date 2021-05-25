package br.rj.rveiga.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI="http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        RestAssured.given()
                .log().all()
                .when()
                .get("/todo")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void deveAdicionarTarefa(){
        RestAssured.given()
                .body("{ \"task\": \"test via api\", \"dueDate\": \"2022-10-12\" }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201);
    }

    @Test
    public void naoDeveAdcionarTarefaInvalida(){
        RestAssured.given()
                .body("{ \"task\": \"test via api\", \"dueDate\": \"2020-10-12\" }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
        .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}


