package utils;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class VerificationUtils {

    @Step
    public static void verifyTwoValues(String expectedValue, String actualValue) {
        Assertions.assertEquals(expectedValue, actualValue, "Expected value (" + expectedValue + ") doesn't match actual (" + actualValue);
    }

    @Step
    public static void verifyTwoValues(BigDecimal expectedValue, BigDecimal actualValue) {
        Assertions.assertEquals(expectedValue, actualValue, "Expected value (" + expectedValue + ") doesn't match actual (" + actualValue);
    }
}
