package com.Yuri;

public class Percept {
    public boolean isDirty;
    public Environment.tileType tileStatus;

    public Percept(boolean isDirty, Environment.tileType tileStatus) {
        this.isDirty = isDirty;
        this.tileStatus = tileStatus;
    }
}
