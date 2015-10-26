package pw.jor;

import pw.jor.imgurwallpaper.image.Container;

import java.util.ArrayList;

/**
 * Created by jrobinson on 10/26/15.
 */
public class Tester {

    private ArrayList<Test> tests = new ArrayList<>();

    public boolean test( Container image ) {

        boolean allPassed = true;

        for ( Test tester : this.tests ) {
            if (tester.test(image)) {
                tester.accept(image);
            } else {
                allPassed = false;
            }
        }

        return allPassed;
    }

    public void addTest(Test test) {
        tests.add(test);
    }
}
