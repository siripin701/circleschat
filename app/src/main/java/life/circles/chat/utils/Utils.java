package life.circles.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {
    public static SimpleDateFormat enUsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public static boolean isEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }
}
