package restTests;

import endpoints.Endpoints;
import functions.CashbackFunctions;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.VerificationUtils;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class CashbackServiceNegativeTests extends FunctionalTest {
    @BeforeAll
    public static void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = FunctionalTest.baseUrl;
        // Setting BasePath once for class
        RestAssured.basePath = Endpoints.CALCULATE.toString();
    }

    @ParameterizedTest
    @MethodSource("invalidRangeTestData")
    public void invalidRangeTest(BigDecimal ticketPrice, long dayToDeparture) {

        //Creates special JSON and sends post-request
        Response response = CashbackFunctions.createCashbackServiceJson(ticketPrice, dayToDeparture);
        //Check status code
        int statusCode = response.getStatusCode();
        VerificationUtils.verifyTwoValues(HttpStatus.SC_INTERNAL_SERVER_ERROR, statusCode);

    }

    @ParameterizedTest
    @MethodSource("invalidArgumentTypeTestData")
    public void invalidArgumentTypeTest(String ticketPrice, String dayToDeparture) {
        //Creates JSON and fills it with invalid data
        JSONObject requestBody = new JSONObject();
        requestBody.put("priceTicket", ticketPrice);
        requestBody.put("dayToDeparture", dayToDeparture);
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.body(requestBody.toString());
        System.out.println(requestBody);
        Response response = request.post();
        //Check status code
        int statusCode = response.getStatusCode();
        VerificationUtils.verifyTwoValues(HttpStatus.SC_INTERNAL_SERVER_ERROR, statusCode);

    }

    @Test
    public void emptyBodyTest() {
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        Response response = request.post();
        //Check status code
        int statusCode = response.getStatusCode();
        VerificationUtils.verifyTwoValues(HttpStatus.SC_INTERNAL_SERVER_ERROR, statusCode);

    }

    private static Stream<Arguments> invalidRangeTestData() {
        return Stream.of(
                Arguments.of(new BigDecimal("-10.50"), 20),
                Arguments.of(BigDecimal.ZERO, 20),
                Arguments.of(new BigDecimal("10.50"), -10)
        );
    }

    private static Stream<Arguments> invalidArgumentTypeTestData() {
        return Stream.of(
                Arguments.of("Invalid ticket price", null),
                Arguments.of(null, "Invalid day to departure"),
                Arguments.of("", "")
        );
    }
}
