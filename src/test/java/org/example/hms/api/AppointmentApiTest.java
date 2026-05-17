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

public class AppointmentApiTest {

    private static final String BASE_URL = ConfigReader.getApiBaseUrl() + "/appointments";
    private Long createdAppointmentId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ConfigReader.getApiBaseUrl();
    }

    @Test(description = "GET /api/appointments returns seeded appointment")
    public void testGetAllAppointments() {
        given()
            .when().get(BASE_URL)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$", hasSize(greaterThanOrEqualTo(1)))
            .body("[0].patientName", equalTo("John Doe"))
            .body("[0].doctorName", equalTo("Dr. Adams"))
            .body("[0].status", equalTo("Scheduled"));
    }

    @Test(description = "POST /api/appointments creates a new appointment")
    public void testCreateAppointment() {
        String body = """
                {
                  "patientName": "Jane Smith",
                  "doctorName": "Dr. Brown",
                  "department": "Neurology",
                  "date": "2025-08-20",
                  "time": "09:30",
                  "type": "Follow-up",
                  "status": "Scheduled"
                }
                """;

        Response response = given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post(BASE_URL)
            .then()
            .statusCode(200)
            .body("patientName", equalTo("Jane Smith"))
            .body("doctorName", equalTo("Dr. Brown"))
            .body("status", equalTo("Scheduled"))
            .body("id", notNullValue())
            .extract().response();

        createdAppointmentId = response.jsonPath().getLong("id");
        Assert.assertNotNull(createdAppointmentId, "Created appointment should have an ID");
    }

    @Test(description = "PUT /api/appointments/{id} updates appointment", dependsOnMethods = "testCreateAppointment")
    public void testUpdateAppointment() {
        String body = """
                {
                  "patientName": "Jane Smith",
                  "doctorName": "Dr. Clark",
                  "department": "Neurology",
                  "date": "2025-09-01",
                  "time": "11:00",
                  "type": "Follow-up",
                  "status": "Completed"
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().put(BASE_URL + "/" + createdAppointmentId)
            .then()
            .statusCode(200)
            .body("status", equalTo("Completed"))
            .body("doctorName", equalTo("Dr. Clark"))
            .body("date", equalTo("2025-09-01"));
    }

    @Test(description = "DELETE /api/appointments/{id} removes appointment", dependsOnMethods = "testUpdateAppointment")
    public void testDeleteAppointment() {
        given()
            .when().delete(BASE_URL + "/" + createdAppointmentId)
            .then()
            .statusCode(200);

        // Verify removed from list
        given()
            .when().get(BASE_URL)
            .then()
            .statusCode(200)
            .body("id", not(hasItem(createdAppointmentId.intValue())));
    }

    @Test(description = "POST /api/appointments with Emergency type")
    public void testCreateEmergencyAppointment() {
        String body = """
                {
                  "patientName": "Emergency Patient",
                  "doctorName": "Dr. Evans",
                  "department": "Emergency",
                  "date": "2025-07-25",
                  "time": "00:00",
                  "type": "Emergency",
                  "status": "Scheduled"
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when().post(BASE_URL)
            .then()
            .statusCode(200)
            .body("type", equalTo("Emergency"))
            .body("department", equalTo("Emergency"));
    }
}
