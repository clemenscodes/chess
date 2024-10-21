package model;

import java.util.concurrent.BlockingQueue;

class Reader<T> implements IReader<T> {

    private BlockingQueue<T> sharedQueue;
    private T data;
    private T buffer;

    Reader(BlockingQueue<T> sharedQueue) {
        setSharedQueue(sharedQueue);
    }

    public T read() {
        try {
            if (getBuffer() != null) {
                setData(getBuffer());
                setBuffer(null);
                return getData();
            } else {
                setData(getSharedQueue().take());
                setBuffer(getData());
            }
        } catch (InterruptedException ignored) {
        }
        return getData();
    }

    public T peek() {
        return getSharedQueue().peek();
    }

    public void flush() {
        while (getSharedQueue().peek() != null) {
            getSharedQueue().remove();
        }
        setData(null);
        setBuffer(null);
    }

    private BlockingQueue<T> getSharedQueue() {
        return sharedQueue;
    }

    private void setSharedQueue(BlockingQueue<T> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    private T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    private T getBuffer() {
        return buffer;
    }

    private void setBuffer(T buffer) {
        this.buffer = buffer;
    }
}
