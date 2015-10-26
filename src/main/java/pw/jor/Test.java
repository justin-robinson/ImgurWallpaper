package pw.jor;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by jrobinson on 10/26/15.
 */
public class Test<T> {

    private Predicate<T> containerPredicate;
    private Consumer<T> containerConsumer;

    public Test ( Predicate<T> containerPredicate, Consumer<T> containerConsumer ) {
        this.containerPredicate = containerPredicate;
        this.containerConsumer = containerConsumer;
    }

    public boolean test ( T t ) {
        return this.containerPredicate.test(t);
    }

    public void accept ( T t ) {
        this.containerConsumer.accept(t);
    }
}
