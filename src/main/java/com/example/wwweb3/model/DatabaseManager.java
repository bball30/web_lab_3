package com.example.wwweb3.model;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    private String DB_URL = "jdbc:postgresql://localhost:3455/studs";
    private String USER = "";
    private String PASS = "";
    private String JDBC_DRIVER = "org.postgresql.Driver";
    private Connection connection;

    private final String TABLE = "result_table";

    private final String TABLE_ID = "id";
    private final String TABLE_X = "x";
    private final String TABLE_Y = "y";
    private final String TABLE_R = "r";
    private final String TABLE_TIME = "time";
    private final String TABLE_VALID = "valid";
    private final String TABLE_HIT = "hit";

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + " ("
            + TABLE_ID + " INTEGER, " + TABLE_X + " FLOAT, " + TABLE_Y + " FLOAT, "
            + TABLE_R  + " INTEGER, " + TABLE_TIME + " VARCHAR(20), " + TABLE_VALID
            + " VARCHAR(20), " + TABLE_HIT + " VARCHAR(20))";
    private final String INSERT_RESULT = "INSERT INTO " + TABLE +
            " (" + TABLE_ID + ", " + TABLE_X + ", " + TABLE_Y + ", " +
            TABLE_R + ", " + TABLE_TIME + ", " +
            TABLE_VALID + ", " + TABLE_HIT + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_TABLE = "DELETE FROM " + TABLE +
            " WHERE " + TABLE_ID + " = ?";
    private final String SELECT_ALL_TABLE = "SELECT * FROM " + TABLE +
            " WHERE " + TABLE_ID + " = ?";

    public DatabaseManager() {
        connect();
    }

    private void connect() {
        try {
            getPropertiesFromFile();
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPropertiesFromFile() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseManager.class.getResourceAsStream("/config.properties")) {
            properties.load(inputStream);
            DB_URL = properties.getProperty("db.host");
            USER = properties.getProperty("db.login");
            PASS = properties.getProperty("db.password");
            JDBC_DRIVER = properties.getProperty("db.driver");
        }
    }

    private PreparedStatement doPreparedStatement(String sqlStatement, boolean generateKeys) throws SQLException {
        PreparedStatement preparedStatement;
        try {
            if (connection == null) throw new SQLException();
            int autoGeneratedKeys = generateKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
            preparedStatement = connection.prepareStatement(sqlStatement, autoGeneratedKeys);
            return preparedStatement;
        } catch (SQLException e) {
            if (connection == null) {
                e.printStackTrace();
            }
            throw new SQLException();
        }
    }


    private void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement == null) return;
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = doPreparedStatement(CREATE_TABLE, false);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    public void insert(PointResult pointResult) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = doPreparedStatement(INSERT_RESULT, false);
            preparedStatement.setInt(1, pointResult.getId());
            preparedStatement.setDouble(2, pointResult.getX());
            preparedStatement.setDouble(3, pointResult.getY());
            preparedStatement.setInt(4, pointResult.getR());
            preparedStatement.setString(5, pointResult.getTime());
            preparedStatement.setString(6, Boolean.toString(pointResult.isValid()));
            preparedStatement.setString(7, Boolean.toString(pointResult.isHit()));
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    public void deleteAll(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = doPreparedStatement(DELETE_TABLE, false);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    private PointResult returnHit(ResultSet resultSet) throws SQLException {
        double x = resultSet.getDouble(TABLE_X);
        double y = resultSet.getDouble(TABLE_Y);
        int r = resultSet.getInt(TABLE_R);
        String time = resultSet.getString(TABLE_TIME);
        boolean valid = resultSet.getBoolean(TABLE_VALID);
        boolean hit = resultSet.getBoolean(TABLE_HIT);
        return new PointResult(x, y, r, time, valid, hit);
    }

    public List<PointResult> getAll(int id) {
        List<PointResult> table = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = doPreparedStatement(SELECT_ALL_TABLE, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                table.add(0, returnHit(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
        }
        return table;
    }
}
