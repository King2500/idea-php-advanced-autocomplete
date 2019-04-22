package net.king2500.plugins.PhpAdvancedAutoComplete.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class DateTimeUtil {

    private final static HashMap<String, String> dateTimePhpToJavaPattern = new HashMap<String, String>() {{
        put("d", "dd");
        put("D", "EEE");
        put("j", "d");
        put("l", "EEEE");
        put("N", "u");
        put("z", "D");  // zero-based 0..365 ?
        put("W", "w");  // ISO-8601 ?
        put("F", "MMMM");
        put("m", "MM");
        put("M", "MMM");
        put("n", "M");
        put("o", "Y");
        put("Y", "yyyy");
        put("y", "yy");
        put("A", "a");
        put("g", "h");
        put("G", "H");
        put("h", "hh");
        put("H", "HH");
        put("i", "mm");
        put("s", "ss");
        put("v", "SSS");
        put("e", "zzzz");
        put("O", "Z");
        put("P", "XXX");
        put("T", "zzz");
        put("c", "yyyy-MM-dd'T'HH:mm:ssXXX");
        put("r", "EEE, d MMM yyyy HH:mm:ss Z");
    }};

    public static String formatPhpDateTime(String phpFormat) {
        StringBuilder str = new StringBuilder();
        for (char phpChar : phpFormat.toCharArray()) {
            String phpCharStr = Character.toString(phpChar);
            if (!dateTimePhpToJavaPattern.containsKey(phpCharStr)) {
                switch (phpChar) {
                    case 'S':
                        // S -> st, nd, rd, th
                        String day = formatDateTime("d");
                        switch (day.charAt(day.length() - 1)) {
                            case '1':
                                str.append("st");
                                continue;

                            case '2':
                                str.append("nd");
                                continue;

                            case '3':
                                str.append("rd");
                                continue;

                            default:
                                str.append("th");
                                continue;
                        }

                    case 'w':
                        // w -> 0 (Sun) ... 6 (Sat)
                        String weekday = formatDateTime("u");
                        if (weekday.equals("7")) {
                            weekday = "0";
                        }
                        str.append(weekday);
                        continue;

                    case 't':
                        // t -> Number of days in month
                        String month = formatDateTime("M");
                        String year = formatDateTime("y");
                        byte numDays = 31;
                        if ("4".equals(month) || "6".equals(month) || "9".equals(month) || "11".equals(month)) {
                            numDays = 30;
                        }
                        if ("2".equals(month)) {
                            numDays = (byte) (isLeapYear(Integer.parseInt(year)) ? 29 : 28);
                        }
                        str.append(numDays);
                        continue;

                    case 'L':
                        // L -> Leap year = 0 or 1
                        String y = formatDateTime("y");
                        str.append(isLeapYear(Integer.parseInt(y)) ? '1' : '0');
                        continue;

                    case 'a':
                        // a -> am/pm in lowercase
                        String dayTime = formatDateTime("a");
                        str.append(dayTime.toLowerCase());
                        continue;

                    case 'I':
                        // I -> DST = 0 or 1
                        String dst = TimeZone.getDefault().inDaylightTime(new Date()) ? "1" : "0";
                        str.append(dst);
                        continue;

                    case 'B':
                        // B -> Swatch 000 .. 999
                        str.append(getCurrentTimeInBeats());
                        continue;

                    case 'u':
                        // u -> Milliseconds 000000
                        long millis = System.currentTimeMillis();
                        str.append(millis - ((int)(millis / 1000L)) * 1000L);
                        continue;

                    case 'Z':
                        // Z -> timezone offset in seconds
                        int tzOffset = TimeZone.getDefault().getOffset(new Date().getTime()) / 1000;
                        str.append(tzOffset);
                        continue;

                    case 'U':
                        // U -> unix timestamp (seconds)
                        str.append(System.currentTimeMillis() / 1000L);
                        continue;
                }
                // unsupported?
                if (Character.isLetter(phpChar)) {
                    return "";
                }
                str.append(phpChar);
                continue;
            }
            String javaPattern = dateTimePhpToJavaPattern.get(phpCharStr);
            str.append(formatDateTime(javaPattern));
        }
        return str.toString();
    }

    private static String formatDateTime(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }

    private static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    private static int getCurrentTimeInBeats() {
        java.util.Calendar cal = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT+01:00"));
        //noinspection UnnecessaryLocalVariable
        int beats = (int) ((cal.get(java.util.Calendar.SECOND) + (cal.get(java.util.Calendar.MINUTE) * 60) + (cal.get(java.util.Calendar.HOUR_OF_DAY) * 3600)) / 86.4);
        return beats;
    }
}
