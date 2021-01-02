package com.sinacomsys.prj1.model;

public class Vector {
    private int source;
    private int destination;

    public Vector() {
        this.source = -1;
        this.destination = -1;
    }

    public Vector(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        if (source < 0) {
            throw new IllegalArgumentException("source node can not be negative");
        }
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        if (destination < 0) {
            throw new IllegalArgumentException("destinationination node can not be negative");
        }
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                '}';
    }
}
