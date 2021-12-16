package com.example.wwweb3;

import com.example.wwweb3.controller.PointResultController;
import com.example.wwweb3.model.DatabaseManager;
import com.example.wwweb3.model.PointResult;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PointsBean {

    private String errorWindow;

    private final PointResultController pointResultController = new PointResultController();
    private List<PointResult> pointResultList;
    private List<String> sessionsID;
    private DatabaseManager dbManager = new DatabaseManager();

    private String x;
    private String xFromCanvas;

    private String y;
    private String yFromCanvas;

    private boolean rSelect1;
    private boolean rSelect2;
    private boolean rSelect3;
    private boolean rSelect4;
    private boolean rSelect5;
    private String rFromCanvas;

    public PointsBean() {}

    private List<String> getRValues() {
        return new ArrayList<String>() {
            {
                if (rSelect1) add("1");
                if (rSelect2) add("2");
                if (rSelect3) add("3");
                if (rSelect4) add("4");
                if (rSelect5) add("5");
            }
        };
    }

    public void addResultToBD() {
        if (pointResultList.size() == 0) {
            dbManager.createTable();
        }
        List<String> rValues = getRValues();
        for (String r : rValues) {
            String id = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
            PointResult pointResult = pointResultController.createPointResult(takeID(id), x, y, r, false);
            dbManager.insert(pointResult);
            pointResultList.add(0, pointResult);
        }
    }

    public void addResultFromCanvasToDB() {
        if (pointResultList.size() == 0) {
            dbManager.createTable();
        }
        String id = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        PointResult pointResult = pointResultController.createPointResult(takeID(id), xFromCanvas, yFromCanvas, rFromCanvas, true);
        dbManager.insert(pointResult);
        pointResultList.add(0, pointResult);
    }

    public void clearTable() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        dbManager.deleteAll(takeID(id));
        pointResultList = new ArrayList<>();
    }

    private int takeID(String id) {
        if (sessionsID == null) {
            sessionsID = new ArrayList<String>();
            sessionsID.add(id);
            return sessionsID.indexOf(id);
        }
        if (!sessionsID.contains(id)) {
            sessionsID.add(id);
        }
        return sessionsID.indexOf(id) + 1;
    }

    public String getX() {
        return x;
    }

    public String getxFromCanvas() {
        return xFromCanvas;
    }

    public String getY() {
        return y;
    }

    public String getyFromCanvas() {
        return yFromCanvas;
    }

    public String getrFromCanvas() {
        return rFromCanvas;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setxFromCanvas(String xFromCanvas) {
        this.xFromCanvas = xFromCanvas;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setyFromCanvas(String yFromCanvas) {
        this.yFromCanvas = yFromCanvas;
    }

    public void setrFromCanvas(String rFromCanvas) {
        this.rFromCanvas = rFromCanvas;
    }

    public String getErrorWindow() {
        return errorWindow;
    }

    public void setErrorWindow(String errorWindow) {
        this.errorWindow = errorWindow;
    }

    public List<PointResult> getPointResultList() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        pointResultList = dbManager.getAll(takeID(id));
        return pointResultList;
    }

    public boolean isrSelect1() {
        return rSelect1;
    }

    public boolean isrSelect2() {
        return rSelect2;
    }

    public boolean isrSelect3() {
        return rSelect3;
    }

    public boolean isrSelect4() {
        return rSelect4;
    }

    public boolean isrSelect5() {
        return rSelect5;
    }

    public void setrSelect1(boolean rSelect1) {
        this.rSelect1 = rSelect1;
    }

    public void setrSelect2(boolean rSelect2) {
        this.rSelect2 = rSelect2;
    }

    public void setrSelect3(boolean rSelect3) {
        this.rSelect3 = rSelect3;
    }

    public void setrSelect4(boolean rSelect4) {
        this.rSelect4 = rSelect4;
    }

    public void setrSelect5(boolean rSelect5) {
        this.rSelect5 = rSelect5;
    }
}
