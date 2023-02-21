package restTests;

import endpoints.Endpoints;
import functions.CashbackFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.VerificationUtils;

import java.math.BigDecimal;
import java.util.stream.Stream;


public class CashbackServicePositiveTests extends FunctionalTest {

    @BeforeAll
    public static void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = FunctionalTest.baseUrl;
        // Setting BasePath once for class
        RestAssured.basePath = Endpoints.CALCULATE.toString();
    }

    @ParameterizedTest
    @MethodSource("lessThanTenDaysTestData")
    public void lessThanTenDaysTest(BigDecimal ticketPrice, long dayToDeparture) {

        //Creates special JSON and sends post-request
        Response response = CashbackFunctions.createCashbackServiceJson(ticketPrice, dayToDeparture);
        //Check status code
        int statusCode = response.getStatusCode();
        VerificationUtils.verifyTwoValues(HttpStatus.SC_OK, statusCode);
        //Check response data
        CashbackFunctions.verifyCashbackServiceJson(response, "0", new BigDecimal("0.00"));

    }

    @ParameterizedTest
    @MethodSource("betweenTwentyAndTenDaysTestData")
    public void betweenTwentyAndTenDaysTest(BigDecimal ticketPrice, long dayToDeparture) {

        //Creates special JSON and sends post-request
        Response response = CashbackFunctions.createCashbackServiceJson(ticketPrice, dayToDeparture);
        //Check status code
        int statusCode = response.getStatusCode();
        VerificationUtils.verifyTwoValues(HttpStatus.SC_OK, statusCode);
        //Calculate expected cashback
        BigDecimal expectedCashback = ticketPrice.divide(new BigDecimal(2));
        System.out.println(expectedCashback);
        //Check response data
        CashbackFunctions.verifyCashbackServiceJson(response, "50", expectedCashback);

    }

    @Test
    public void moreThanTwentyDaysTest() {
        BigDecimal ticketPrice = new BigDecimal("2600.99");
        long dayToDeparture = 21;

        //Creates special JSON and sends post-request
        Response response = CashbackFunctions.createCashbackServiceJson(ticketPrice, dayToDeparture);
        //Check status code
        int statusCode = response.getStatusCode();
        VerificationUtils.verifyTwoValues(HttpStatus.SC_OK, statusCode);
        //Check response data
        CashbackFunctions.verifyCashbackServiceJson(response, "100", ticketPrice);

    }

    private static Stream<Arguments> lessThanTenDaysTestData() {
        return Stream.of(
                Arguments.of(new BigDecimal("123.44"), 0),
                Arguments.of(new BigDecimal("1000.99"), 10)
        );
    }

    private static Stream<Arguments> betweenTwentyAndTenDaysTestData() {
        return Stream.of(
                Arguments.of(new BigDecimal("500.00"), 20),
                Arguments.of(new BigDecimal("323.33"), 11)
        );
    }
}

