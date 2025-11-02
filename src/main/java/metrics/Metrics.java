package metrics;

public class Metrics {
    private long startTime, endTime;
    private int operations;

    public void start() { startTime = System.nanoTime(); }
    public void end() { endTime = System.nanoTime(); }
    public void addOp() { operations++; }

    public long getTimeMs() { return (endTime - startTime) / 1_000_000; }
    public int getOps() { return operations; }
}
