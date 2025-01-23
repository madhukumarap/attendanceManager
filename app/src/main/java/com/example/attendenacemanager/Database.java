package com.example.attendenacemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
    private String url = "jdbc:mysql://localhost/attendancemanager";
    private String user = "user";
    private String pass = "";
    private Statement statement;
    public Database() {
        //establish the new connection
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Class> getAllClasses() {
        ArrayList<Class> classes = new ArrayList<>();
        try {
            String select = "SELECT * FROM `classes`";
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                Class c = new Class(rs.getInt("ID"), rs.getString("ClassName"));
                classes.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classes;
    }
    public  int getNextClassID() {
        int id = 0;
        ArrayList<Class> classes = getAllClasses();
        int size = classes.size();
        if(size !=0) {
            int last = size -1;
            Class lastClass = classes.get(last);
            id = lastClass.getID() +1;
        }
        return id;
    }
}
