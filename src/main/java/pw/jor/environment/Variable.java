package pw.jor.environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helpers for OS environment variables
 *
 * @author jrobinson
 * @since 10/23/15
 */
public class Variable {

    /*
     * Regex to detect windows cmdl variables
     */
    public static final String WINDOWS_CMD = "\\%([A-Za-z0-9]+)\\%";

    /**
     * Expands system variables in a string
     *
     * @param input string possibly containing environment variables
     * @return expanded version of input string
     */
    public static String expand( String input, String pattern ) {

        String output = input;

        Pattern expr = Pattern.compile(pattern);
        Matcher matcher = expr.matcher(input);

        // find a new variable
        while (matcher.find()) {

            // get value from system
            String envValue = System.getenv(matcher.group(1).toUpperCase());

            // ensure we have a string
            envValue = envValue == null ? "" : envValue.replace("\\", "\\\\");

            // replace all instances of this variable
            Pattern subexpr = Pattern.compile(Pattern.quote(matcher.group(0)));
            output = subexpr.matcher(input).replaceAll(envValue);
        }

        return output;

    }
}
