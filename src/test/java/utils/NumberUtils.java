package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static BigDecimal floatToBigDecimal(Float f) {


        if (f == null || f.isInfinite() || f.isNaN()) {
            return BigDecimal.ZERO;
        }
        try {
            BigDecimal bd = BigDecimal.valueOf(f);
            return bd.setScale(3, RoundingMode.HALF_UP).stripTrailingZeros();

        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
