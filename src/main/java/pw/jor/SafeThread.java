package pw.jor;

/**
 * Mimics unsafe Thread class functionality
 *
 */
public abstract class SafeThread implements Runnable {

    protected Thread thread;
    private Thread thisThread;
    private boolean threadSuspended = false;

    public abstract void action();

    public void run () {
        thisThread = Thread.currentThread();
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
     * Checks that the thread is alive
     * @return boolean
     */
    public boolean isAlive() {
        return this.thread != null && this.thread.isAlive();
    }

    /**
     * Stalls the thread until resume() is called
     *
     * Call this when your run method has a chance to suspend
     */
    public void blockSuspended() {
        if ( threadSuspended ) {
            synchronized (this) {
                while( threadSuspended && threadActive() ) {
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
     * @return boolean
     */
    public boolean threadActive () {
        return thread == thisThread;
    }
}
