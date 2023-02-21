package functions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import utils.VerificationUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static utils.NumberUtils.floatToBigDecimal;

public class CashbackFunctions {
    /**
     * @param priceTicket    - ticket price
     * @param dayToDeparture - amount of days which left till deadline
     * @return - response
     */
    public static Response createCashbackServiceJson(BigDecimal priceTicket, Long dayToDeparture) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("priceTicket", priceTicket);
        requestBody.put("dayToDeparture", dayToDeparture);
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.body(requestBody.toString());
        System.out.println(requestBody);
        return request.post();
    }

    /**
     * @param response         - use response from createCashbackServiceJson()
     * @param percentExpected  - expected percent value
     * @param cashbackExpected - expected cashback value
     */
    public static void verifyCashbackServiceJson(Response response, String percentExpected, BigDecimal cashbackExpected) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        //Get actual percent value
        String percentActual = jsonPathEvaluator.get("percent");
        //Get actual cashback
        BigDecimal cashbackActual;
        if (jsonPathEvaluator.get("cashback") instanceof Float) {
            cashbackActual = floatToBigDecimal(jsonPathEvaluator.get("cashback"));
        } else {
            Integer i = jsonPathEvaluator.get("cashback");
            cashbackActual = BigDecimal.valueOf(i.longValue()).setScale(2, RoundingMode.HALF_UP);
        }

        //Check expected percent value which should contain % symbol
        percentExpected = percentExpected.substring(percentExpected.length() - 1).contains("%") ? percentExpected : percentExpected + "%";

        //Verification part
        VerificationUtils.verifyTwoValues(percentExpected, percentActual);
        VerificationUtils.verifyTwoValues(cashbackExpected, cashbackActual);
    }
}
