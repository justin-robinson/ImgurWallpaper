package pw.jor.environment;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jrobinson on 10/23/2015.
 */
public class Variable {

    public static final String WINDOWS_CMD = "\\%([A-Za-z0-9]+)\\%";

    public static String expand( String input ) {

        String output = input;

        String pattern = WINDOWS_CMD;
        Pattern expr = Pattern.compile(pattern);
        Matcher matcher = expr.matcher(input);

        while (matcher.find()) {
            String envValue = System.getenv(matcher.group(1).toUpperCase());

            envValue = envValue == null ? "" : envValue.replace("\\", "\\\\");

            Pattern subexpr = Pattern.compile(Pattern.quote(matcher.group(0)));
            output = subexpr.matcher(input).replaceAll(envValue);
        }

        return output;

    }
}
