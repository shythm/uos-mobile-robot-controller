package uos.teamkernel.common;

import java.lang.Math;

public class Point {
    private int x, y;

    public Point() {
        this.x = this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Point getPoint() {
        Point p = new Point(this.x, this.y);
        return p;
    }

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDistance(Point p) {
        return Math.abs(this.x - p.getX()) + Math.abs(this.y - p.getY());
    }

    public Point[] getAdjPointList() {
        Point[] AdjPointList = new Point[4];
        int[] dx = { 0, 0, -1, 1 }, dy = { -1, 1, 0, 0 };
        for (int i = 0; i < 4; i++)
            AdjPointList[i] = new Point(this.x + dx[i], this.y + dy[i]);
        return AdjPointList;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
