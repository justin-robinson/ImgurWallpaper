package pw.jor.imgurwallpaper.image;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by jrobinson on 10/26/15.
 */
public class Tester {

    private boolean allPassed = true;

    public void testImage (
            Container image,
            Predicate<Container> tester,
            Consumer<Container> block
    ) {
        if ( tester.test(image) ) {
            block.accept(image);
        } else {
            allPassed = false;
        }
    }

    public boolean allPassed () {
        return this.allPassed;
    }
}
