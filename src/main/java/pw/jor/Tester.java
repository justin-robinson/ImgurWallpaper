package pw.jor;

import java.util.ArrayList;

/**
 * Created by jrobinson on 10/26/15.
 */
public class Tester<T> {

    private ArrayList<Test<T>> tests = new ArrayList<>();

    public boolean test( T t ) {

        boolean allPassed = true;

        for ( Test<T> tester : this.tests ) {
            if ( ! tester.test(t) ) {
                allPassed = false;
            }
        }

        return allPassed;
    }

    public void addTest(Test<T> test) {
        tests.add(test);
    }
}
