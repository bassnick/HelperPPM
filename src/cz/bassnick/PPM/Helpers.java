package cz.bassnick.PPM;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helpers {

/*    public static void main(String[] args) {
        boolean ret = Helpers.compare(0.001f,0.002f-0.001f);
        System.out.println(ret);
    }
  */
public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd;
    }

    public static boolean compare (float f1, float f2) {
        if (f1 == f2) {
            System.out.println("true");

            return true;
        }else {
            System.out.println("false");
            return false;
        }
    }
}