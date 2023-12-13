package testcases;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.module.jsv.JsonSchemaValidator;

public class TC02_CreateBooking extends TestBase{

    private String firtName="Jim";
    @Test(priority = 1 , description = "Create new Booking with valid data")
    public void createNewBookingWithValidData_P(){
        // 1- given ( param - auth- header -body)
        // 2- when (action method supported with path)
        // 3- then (assertion)
        firtName="shhh";
        given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-Type","application/json").body("{\n" +
                        "    \"firstname\" : \""+firtName+"\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when().post("/booking")
                .then().log().all().assertThat().body("booking.firstname",equalTo(firtName))
                .body(JsonSchemaValidator.
                        matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/main/java/parameterization/schema.json")));

        Response resp=given().log().all().header("Content-Type","application/json").body("{\n" +
                "    \"firstname\" : \""+firtName+"\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}")
                .when().post("/booking")
                .then().log().all().assertThat().body("booking.firstname",equalTo(firtName)).extract().response();
        resp.prettyPrint();
        Assert.assertEquals(resp.jsonPath().getString("booking.firstname"),firtName);// hard assert
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(resp.jsonPath().getString("booking.firstname"),firtName);
        softAssert.assertEquals(resp.jsonPath().getString("booking.lastname"),"Brown");
        softAssert.assertAll();

    }
    @Test(priority = 2,description = "Create New Booking with Invalid Data")
    public void createNewBookingWithInvalidData_N(){
        given().log().all().header("Content-Type","application/json").body("{\n" +
                        "    \"firstname\" : \"Jim\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"test-01-01\",\n" +
                        "        \"checkout\" : \"test-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}")
                .when().post("/booking")
                .then().log().all().statusCode(200).extract().response();
    }

}
