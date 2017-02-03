package com.david.cavasdemo;

/**
 * Created by Administrator on 2016/9/27.
 */
public class PieData{
    private int color;
    private float angle;
    private int value;
    private float percentage ;

    public PieData(int color, float angle, int value, float percentage) {
        this.color = color;
        this.angle = angle;
        this.value = value;
        this.percentage = percentage;
    }

    public float getAngle() {

        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getPercentage() {
        return percentage;

    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }




    public void setColor(int color) {
        this.color = color;
    }


    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }



    public int getColor() {
        return color;
    }
}
