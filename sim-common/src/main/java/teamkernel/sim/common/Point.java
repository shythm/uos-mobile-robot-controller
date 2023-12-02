package teamkernel.sim.common;

public class Point extends java.awt.Point {

    public Point() {
        super();
    }

    public Point(int x, int y) {
        super(x, y);
    }

    public int getDistance(Point p) {
        return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
    }

    public Point[] getAdjPointList() {
        Point[] AdjPointList = new Point[4];
        int[] dx = { -1, 0, 1, 0 }, dy = { 0, 1, 0, -1 };
        for (int i = 0; i < 4; i++)
            AdjPointList[i] = new Point(this.x + dx[i], this.y + dy[i]);
        return AdjPointList;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
