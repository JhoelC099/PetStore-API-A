package com.nttdata.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PetStoreStepDef {

    private Response response;
    private String endpoint;
    private Map<String, Object> requestBody;

    @Given("que configuro el endpoint {string}")
    public void configuroElEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @When("envío una solicitud con los siguientes detalles de la orden:")
    public void envioSolicitudConDetalles(Map<String, String> orderDetails) {
        requestBody = new HashMap<>();
        orderDetails.forEach((key, value) -> requestBody.put(key, value.matches("\\d+") ? Integer.parseInt(value) : value));

        response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    @Then("el código de respuesta debe ser {int}")
    public void validarCodigoDeRespuesta(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @And("el cuerpo de la respuesta debe contener los detalles de la orden")
    public void validarDetallesDeLaRespuesta() {
        Assert.assertEquals(requestBody.get("id"), response.jsonPath().getInt("id"));
        Assert.assertEquals(requestBody.get("petId"), response.jsonPath().getInt("petId"));
        Assert.assertEquals(requestBody.get("cantidad"), response.jsonPath().getInt("quantity"));
    }

    @When("envío la solicitud")
    public void envioLaSolicitud() {
        response = given()
                .header("Content-Type", "application/json")
                .when()
                .get(endpoint);
    }

    @And("el cuerpo de la respuesta debe contener los detalles correctos de la orden")
    public void validarDetallesCorrectos() {
        Assert.assertNotNull(response.jsonPath().get("id"));
        Assert.assertNotNull(response.jsonPath().get("petId"));
    }
}
