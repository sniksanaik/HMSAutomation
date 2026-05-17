package org.example.hms.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.hms.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PatientApiTest {

    private static final String BASE_URL = ConfigReader.getApiBaseUrl() + "/patients";
    private Long createdPatientId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.getApiBaseUrl();
    }

    @Test(description = "GET /api/patients returns list with seeded data")
    public void testGetAllPatients() {
        given()
            .when().get(BASE_URL)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$", hasSize(greaterThanOrEqualTo(2)))
            .body("[0].name", equalTo("John Doe"))
            .body("[1].name", equalTo("Jane Smith"));
    }

    @Test(description = "GET /api/patients/1 returns John Doe")
    public void testGetPatientById() {
        given()
            .when().get(BASE_URL + "/1")
            .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("John Doe"))
            .body("department", equalTo("Cardiology"))
            .body("bloodGroup", equalTo("O+"));
    }

    @Test(description = "POST /api/patients creates a new patient")
    public void testCreatePatient() {
        String body = """
                {
                  "name": "Test Patient",
                  "age": 40,
                  "gender": "Male",
                  "bloodGroup": "AB+",
                  "email": "test@example.com",
                  "phone": "555-9999",
                  "department": "Oncology",
                  "insurance": true,
                  "emergency": false,
                  "admissionDate": "2025-06-01"
                }
                """;

        Response response = given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post(BASE_URL)
            .then()
            .statusCode(200)
            .body("name", equalTo("Test Patient"))
            .body("department", equalTo("Oncology"))
            .body("id", notNullValue())
            .extract().response();

        createdPatientId = response.jsonPath().getLong("id");
        Assert.assertNotNull(createdPatientId, "Created patient should have an ID");
    }

    @Test(description = "PUT /api/patients/{id} updates patient", dependsOnMethods = "testCreatePatient")
    public void testUpdatePatient() {
        String body = """
                {
                  "name": "Test Patient Updated",
                  "age": 41,
                  "gender": "Male",
                  "bloodGroup": "AB+",
                  "department": "Radiology",
                  "insurance": false,
                  "emergency": false
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().put(BASE_URL + "/" + createdPatientId)
            .then()
            .statusCode(200)
            .body("name", equalTo("Test Patient Updated"))
            .body("department", equalTo("Radiology"))
            .body("age", equalTo(41));
    }

    @Test(description = "DELETE /api/patients/{id} removes patient", dependsOnMethods = "testUpdatePatient")
    public void testDeletePatient() {
        given()
            .when().delete(BASE_URL + "/" + createdPatientId)
            .then()
            .statusCode(200);

        // Verify patient no longer exists
        given()
            .when().get(BASE_URL + "/" + createdPatientId)
            .then()
            .statusCode(200)
            .body(equalTo("null"));
    }

    @Test(description = "GET /api/patients/2 returns Jane Smith with emergency flag")
    public void testGetPatientWithEmergencyFlag() {
        given()
            .when().get(BASE_URL + "/2")
            .then()
            .statusCode(200)
            .body("name", equalTo("Jane Smith"))
            .body("emergency", equalTo(true))
            .body("insurance", equalTo(false));
    }
}
