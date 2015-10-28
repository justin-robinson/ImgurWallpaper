package pw.jor;

import java.util.ArrayList;

/**
 * Groups Tests together and returns global single result
 *
 * @author jrobinson
 * @since 10/26/15
 */
public class Tester<T> {

    private ArrayList<Test<T>> tests = new ArrayList<>();

    /**
     * Runs all currently added Tests and returns result
     *
     * @param subject Object to test
     * @return test result
     */
    public boolean test( T subject ) {

        boolean allPassed = true;

        for ( Test<T> tester : this.tests ) {
            if ( ! tester.test(subject) ) {
                allPassed = false;
            }
        }

        return allPassed;
    }

    /**
     * Adds test to list of test to run
     *
     * @param test added to list of tests to run
     */
    public void addTest(Test<T> test) {
        tests.add(test);
    }
}
