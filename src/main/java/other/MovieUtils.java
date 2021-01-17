package other;

public abstract class MovieUtils {

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static double roundOff(double value) {
        return Math.round(value * 100)  / 100.0;
    }

    public static String roundOffToStr(double value) {
        double roundOff = Math.round(value * 100)  / 100.0;
        return String.valueOf(roundOff);
    }
}
