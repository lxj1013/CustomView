package cn.com.lxj.customview;

/**
 * DATE 2019/4/4 18:43
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class Point {
    public Point() {
    }

    public Point(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    private int pointX;
    private int pointY;

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }
}
