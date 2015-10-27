package pw.jor;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by jrobinson on 10/26/15.
 */
public class Test<T> {

    private Predicate<T> predicate;
    private Consumer<T> acceptConsumer;
    private Consumer<T> rejectConsumer;

    public Test ( Predicate<T> predicate, Consumer<T> acceptConsumer, Consumer<T> rejectConsumer ) {
        this.predicate = predicate;
        this.acceptConsumer = acceptConsumer;
        this.rejectConsumer = rejectConsumer;
    }

    public boolean test ( T t ) {

        boolean pass = this.predicate.test(t);

        if ( pass ) {
            this.accept(t);
        } else {
            this.reject(t);
        }

        return pass;
    }

    public void accept ( T t ) {
        this.acceptConsumer.accept(t);
    }

    public void reject ( T t ) {
        this.rejectConsumer.accept(t);
    }
}
