package com.michael.prototype.entity;

import java.io.*;

/**
 * Michael created at 2018/3/11 22:41
 * 深层复制需要实现Serializable接口
 */
public class Circle implements Cloneable,Serializable {
    private Point p;

    private int r;

    public Circle(Point p, int r) {
        this.p = p;
        this.r = r;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    /**
     * 浅层复制，直接复制引用
     */
    /*@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }*/

    /**
     * 深层复制，复制所有对象
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return deepClone();
    }

    private Object deepClone() {
        ByteArrayOutputStream ops = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream ips = null;
        ObjectInputStream ois = null;
        try {
            ops = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(ops);
            oos.writeObject(this);
            oos.flush();

            ips = new ByteArrayInputStream(ops.toByteArray());
            ois = new ObjectInputStream(ips);
            return ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(null != ops){
                    ops.close();
                }
                if(null != oos){
                    oos.close();
                }
                if(null != ips){
                    ips.close();
                }
                if(null != ois){
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
