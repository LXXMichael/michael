package com.michael.adapter;

/**
 * Michael created at 2018/3/19 22:39
 */
public class AdapterTest {
    public static void main(String[] args) {
        TypeCLine line = new TypeCLine(new StandardLine());
        line.charge();
    }
}
