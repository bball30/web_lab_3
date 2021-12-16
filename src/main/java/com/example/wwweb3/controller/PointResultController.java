package com.example.wwweb3.controller;

import com.example.wwweb3.model.PointResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PointResultController {
    public PointResult createPointResult(int id, String x, String y, String r, boolean fromCanvas) {
        double xValue = Math.floor(Double.parseDouble(x) * 100) / 100;
        double yValue = Math.floor(Double.parseDouble(y) * 100) / 100;
        int rValue = Integer.parseInt(r);
        boolean valid = true;
        if (!fromCanvas) valid = validate(xValue, yValue, rValue);
        boolean hit = valid && checkHit(xValue, yValue, rValue);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new PointResult(id, xValue, yValue, rValue, time, valid, hit);
    }

    private boolean validate(double x, double y, double r) {
        return validateX(x) && validateY(y) && validateR(r);
    }

    private boolean validateX(double x) {
        return x >= -2 && x <= 2;
    }

    private boolean validateY(double y) {
        return y > -5 && y < 5;
    }

    private boolean validateR(double r) {
        return r >= 1 && r <= 5;
    }

    private boolean checkHit(double xValue, double yValue, double rValue) {
        return checkTriangle(xValue, yValue, rValue) || checkRectangle(xValue, yValue, rValue) || checkCircle(xValue, yValue, rValue);
    }

    private boolean checkTriangle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && yValue <= 0 && yValue >= xValue  - rValue ;
    }

    private boolean checkCircle(double xValue, double yValue, double rValue) {
        return xValue <= 0 && yValue >= 0 && Math.sqrt(xValue * xValue + yValue * yValue) <= rValue;
    }

    private boolean checkRectangle(double xValue, double yValue, double rValue) {
        return xValue >= 0 && xValue <= rValue && yValue >= 0 && yValue <= rValue / 2;
    }
}
