package utils;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class VerificationUtils {

    @Step("Verification of two string values")
    public static void verifyTwoValues(String expectedValue, String actualValue) {
        Assertions.assertEquals(expectedValue, actualValue, "Expected value (" + expectedValue + ") doesn't match actual (" + actualValue);
    }

    @Step("Verification of two BigDecimal values")
    public static void verifyTwoValues(BigDecimal expectedValue, BigDecimal actualValue) {
        Assertions.assertEquals(expectedValue, actualValue, "Expected value (" + expectedValue + ") doesn't match actual (" + actualValue);
    }

    @Step("Verification of two int values")
    public static void verifyTwoValues(int expectedValue, int actualValue) {
        Assertions.assertEquals(expectedValue, actualValue, "Expected value (" + expectedValue + ") doesn't match actual (" + actualValue);
    }
}
