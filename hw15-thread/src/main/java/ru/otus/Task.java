package ru.otus;

public class Task implements Runnable{
    private final int id;
    private final Sequence sequence;
    public Task(int id, Sequence sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    public void run()
    {
        sequence.action(id);

    }
}
