package net.objecthunter.exp4j;

import java.util.EmptyStackException;

class ArrayStack {
    private double[] data;
    private int idx;

    ArrayStack() {
        this(5);
    }

    ArrayStack(int initialCapacity) {
        if (initialCapacity > 0) {
            this.data = new double[initialCapacity];
            this.idx = -1;
            return;
        }
        throw new IllegalArgumentException("Stack's capacity must be positive");
    }

    /* access modifiers changed from: package-private */
    public void push(double value) {
        int i = this.idx + 1;
        double[] dArr = this.data;
        if (i == dArr.length) {
            double[] temp = new double[(((int) (((double) dArr.length) * 1.2d)) + 1)];
            System.arraycopy(dArr, 0, temp, 0, dArr.length);
            this.data = temp;
        }
        double[] temp2 = this.data;
        int i2 = this.idx + 1;
        this.idx = i2;
        temp2[i2] = value;
    }

    /* access modifiers changed from: package-private */
    public double peek() {
        int i = this.idx;
        if (i != -1) {
            return this.data[i];
        }
        throw new EmptyStackException();
    }

    /* access modifiers changed from: package-private */
    public double pop() {
        int i = this.idx;
        if (i != -1) {
            double[] dArr = this.data;
            this.idx = i - 1;
            return dArr[i];
        }
        throw new EmptyStackException();
    }

    /* access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.idx == -1;
    }

    /* access modifiers changed from: package-private */
    public int size() {
        return this.idx + 1;
    }
}
