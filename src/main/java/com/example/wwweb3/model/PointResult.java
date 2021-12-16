package com.example.wwweb3.model;

import java.io.Serializable;
import java.util.Objects;


public class PointResult implements Serializable {
    private int id;
    private double x;
    private double y;
    private int r;
    private String time;
    private boolean valid;
    private boolean hit;
    private String sHit;
    private String sValid;


    public PointResult(int id, double x, double y, int r, String time, boolean valid, boolean hit) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = time;
        this.valid = valid;
        this.hit = hit;
    }

    public PointResult(double x, double y, int r, String time, boolean valid, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = time;
        this.valid = valid;
        this.hit = hit;
    }

    public PointResult() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getR() {
        return r;
    }

    public String getTime() {
        return time;
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isHit() {
        return hit;
    }

    public String getsHit() {
        if (hit) return "Попал";
        else return "Мимо";
    }

    public void setsHit(String sHit) {
        hit = Objects.equals(sHit, "Попал");
    }

    public String getsValid() {
        if (valid) return "Данные валидны";
        else return "Данные не валидны";
    }

    public void setsValid(String sValid) {
        hit = Objects.equals(sValid, "Данные валидны");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointResult that = (PointResult) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && r == that.r && valid == that.valid && hit == that.hit && id == that.id && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, r, time, valid, hit);
    }

    @Override
    public String toString() {
        return "PointResult{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", time='" + time + '\'' +
                ", valid=" + valid +
                ", hit=" + hit +
                '}';
    }
}
