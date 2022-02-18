package com.example.electrictime;

public class VehicleData {

    private String title;
    private int imgId;
    private double range;
    private double speed;

    public VehicleData(String title, int imgId, double range, double speed) {
        this.title = title;
        this.imgId = imgId;
        this.range = range;
        this.speed = speed;
    }

    public VehicleData(String title, int imgId) {
        this.title = title;
        this.imgId = imgId;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
