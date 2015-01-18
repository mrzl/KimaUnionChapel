package main;

import java.util.Calendar;

/**
 * Created by mrzl on 16.01.2015.
 */
public class Utils {
    public static String timestamp () {
        Calendar now = Calendar.getInstance( );
        return String.format( "%1$ty.%1$tm.%1$td_%1$tH:%1$tM:%1$tS", now );
    }
}
