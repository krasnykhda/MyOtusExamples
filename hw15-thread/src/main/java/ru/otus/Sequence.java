package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sequence {
    private static final Logger logger = LoggerFactory.getLogger(Sequence.class);
    private int nextThreadId = 1;
    private final int min, max;
    private final int maxNumberOfThreads;
    private Integer lastValue = 0;
    private Direction sequenceDirection = Direction.INC;

    private void start() {
        for (int id = 1; id <= maxNumberOfThreads; id++) {
            Runnable task = new Task(id, this);
            new Thread(task).start();
        }
    }

    public synchronized void action(int currentId) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (nextThreadId != currentId) {
                    this.wait();
                }
                setValue();
                setNextThreadId();
                logger.info(lastValue.toString());
                sleep();
                notifyAll();
                logger.info("after notify");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void setNextThreadId() {
        if (nextThreadId >= 1) {
            nextThreadId++;
        }
        if (nextThreadId == maxNumberOfThreads+1) {
            nextThreadId = 1;
        }
    }

    private void setValue() {
        if (nextThreadId == 1) {
            if (sequenceDirection.equals(Direction.INC)) {
                lastValue++;
                if (lastValue == max) {
                    sequenceDirection = Direction.DEC;
                }
            } else {
                lastValue--;
                if (lastValue == min) {
                    sequenceDirection = Direction.INC;
                }
            }
        }
    }

    public Sequence(int min, int max, int maxNumberOfThreads) {
        this.min = min;
        this.max = max;
        this.maxNumberOfThreads = maxNumberOfThreads;
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence(1, 10, 2);
        sequence.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
