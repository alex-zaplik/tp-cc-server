package edu.pwr.tp.server.user;

public enum  Directories {
    UP (0,-1),
    DOWN (0,1),
    RIGHT (1,0),
    LEFT (-1,0),
    UP_RIGHT (1,-1),
    DOWN_LEFT (-1,1);

    public final int x;
    public final int y;

    Directories(int x, int y) {
        this.x=x;
        this.y=y;
    }
}
