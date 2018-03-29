package com.michael.prototype;

import com.michael.prototype.entity.Circle;
import com.michael.prototype.entity.Point;

/**
 * Michael created at 2018/3/11 22:42
 */
public class CloneTest {
    public static void main(String[] args) {
        Circle c = new Circle(new Point(0,0),10);
        try {
            Circle c2 = (Circle)c.clone();
            System.out.println(c);
            System.out.println(c2);
            System.out.println(c == c2);
            System.out.println("----------------------");
            System.out.println(c.getP());
            System.out.println(c2.getP());
            System.out.println(c.getP() == c2.getP());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
