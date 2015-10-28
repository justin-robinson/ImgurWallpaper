package pw.jor;

/**
 * Mimics unsafe Thread class functionality
 *
 */
public abstract class SafeThread implements Runnable {

    protected Thread thread;
    private Thread thisThread;
    private boolean threadSuspended = false;

    /**
     * Abstract function to do thread work
     */
    public abstract void action();

    /**
     * Interface method from Runnable
     */
    public void run () {

        // get pointer to this thread so stop() can work
        thisThread = Thread.currentThread();

        // run user-defined action
        action();
    }

    /**
     * Starts the thread
     */
    public void start() {
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Stops the thread
     */
    public synchronized void stop () {
        thread = null;
        notifyAll();
    }

    /**
     * Suspends the thread
     */
    public void suspend() {
        threadSuspended = true;
    }

    /**
     * Resumes the thread
     */
    public synchronized void resume() {
        threadSuspended = false;
        notifyAll();
    }

    /**
     * Stalls the thread until resume() is called
     *
     * Call this when your run method has a chance to suspend
     */
    public void blockSuspended() {
        if ( threadSuspended ) {
            synchronized (this) {
                while( threadSuspended && isAlive() ) {
                    try {
                        wait();
                    } catch ( InterruptedException e ) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Checks that we haven't called stop()
     *
     * @return whether or not this thread has been stopped
     */
    public boolean isAlive() {
        return thread == thisThread;
    }

    /**
     * Whether or not the thread is suspended
     * @return whether or not the thread is suspended
     */
    public boolean isSuspended () {
        return this.threadSuspended;
    }

    /**
     * Gets the Thread object
     *
     * @return the Thread object
     */
    public Thread getThread () {
        return this.thread;
    }
}
