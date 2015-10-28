package pw.jor;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Tests input for user-defined condition and runs an accept or reject clause based on it's boolean output
 *
 * @author jrobinson
 * @since 10/26/15
 */
public class Test<T> {

    private Predicate<T> predicate;
    private Consumer<T> acceptConsumer;
    private Consumer<T> rejectConsumer;

    /**
     * Constructor
     *
     * @param predicate test to run
     * @param acceptConsumer ran on true
     * @param rejectConsumer ran on false
     */
    public Test ( Predicate<T> predicate, Consumer<T> acceptConsumer, Consumer<T> rejectConsumer ) {
        this.predicate = predicate;
        this.acceptConsumer = acceptConsumer;
        this.rejectConsumer = rejectConsumer;
    }

    /**
     * Tests t and runs accept or reject consumer based on boolean output
     *
     * @param subject object being tested
     * @return test result
     */
    public boolean test ( T subject ) {

        boolean pass = this.predicate.test(subject);

        if ( pass ) {
            this.acceptConsumer.accept(subject);
        } else {
            this.rejectConsumer.accept(subject);
        }

        return pass;
    }

}
