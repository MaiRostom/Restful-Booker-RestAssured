package testcases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC01_Auth extends TestBase{

    // check auth request with valid data [positive scenario]
    String username="admin";
    String password="password123";
    @Test(priority = 1, description = "Check auth request with valid data")
    public void checkAuthWithValidData_P(){
      // 1- given (auth , param , body , header)
        // 2- when action (get - post -put , delete) supported with requets path
       // 3- then (assertion)
        given().log().all().header("Content-Type","application/json").body("{\n" +
                "    \"username\" : \""+username+"\",\n" +
                "    \"password\" : \""+password+"\"\n" +
                "}")
                .when().post("/auth").then().log().all().statusCode(200);
    }
    @Test(priority = 2, description = "Check auth request with Invalid email")
    public void checkAuthWithInvalidEmail_N(){
        // 1- given (auth , param , body , header)
        // 2- when action (get - post -put , delete) supported with requets path
        // 3- then (assertion)
        username="test@test.com";
        Response resp=given().log().all().header("Content-Type","application/json").body("{\n" +
                        "    \"username\" : \""+username+"\",\n" +
                        "    \"password\" : \""+password+"\"\n" +
                        "}")
                .when().post("/auth").then().log().all().statusCode(200).extract().response();
        Assert.assertEquals(resp.jsonPath().getString("reason"),"Bad credentials");
    }
    @Test(priority = 3, description = "Check auth request with Invalid password")
    public void checkAuthWithInvalidPassword_N(){
        // 1- given (auth , param , body , header)
        // 2- when action (get - post -put , delete) supported with requets path
        // 3- then (assertion)
        password="1234djjj";
        given().log().all().header("Content-Type","application/json").body("{\n" +
                        "    \"username\" : \""+username+"\",\n" +
                        "    \"password\" : \""+password+"\"\n" +
                        "}")
                .when().post("/auth").then().log().all().statusCode(200);
    }
    @Test(priority = 4, description = "Check auth request with Invalid email and password")
    public void checkAuthWithInvalidEmailPassword_N(){
        // 1- given (auth , param , body , header)
        // 2- when action (get - post -put , delete) supported with requets path
        // 3- then (assertion)
        username="sdsad";
        password="1234djjj";
        given().log().all().header("Content-Type","application/json").body("{\n" +
                        "    \"username\" : \""+username+"\",\n" +
                        "    \"password\" : \""+password+"\"\n" +
                        "}")
                .when().post("/auth").then().log().all().statusCode(200);
    }
}
