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
    public void addClass(Class c) {
        try {
            String insert = "INSERT INTO `classes`(`ID`, `ClassName`) VALUES ('" + c.getID() + "', '" + c.getClassName() + "')";
            statement.execute(insert);

            String create1 = "CREATE TABLE IF NOT EXISTS `" + c.getID() + " Session` ('ID' INTEGER, 'Subject' TEXT, 'date' DATE)";
            statement.execute(create1);

            String create2 = "CREATE TABLE IF NOT EXISTS `" + c.getID() + " Session` ('ID' INTEGER, 'FirstName' TEXT, 'LastName' TEXT, 'Email' TEXT, 'Tel' TEXT)";
            statement.execute(create2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteStudents(int id) {
        try {
            // Delete from the `classes` table
            String query = "DELETE FROM `classes` WHERE `ID` = " + id;
            statement.execute(query);

            // Drop the associated tables
            String drop1 = "DROP TABLE `" + id + "-Sessions`";
            String drop2 = "DROP TABLE `" + id + "-Students`";
            statement.execute(drop1);
            statement.execute(drop2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Student> getStudents(int id) {
        ArrayList<Student> students = new ArrayList<>();
        try{
            String query = "SELECT * FROM `"+id+" -Students` ";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Student s = new Student();
                s.setID(rs.getInt("ID"));
                s.setFirstName(rs.getString("FristName"));
                s.setLastName(rs.getString("LastName"));
                s.setEmail(rs.getString("Email"));
                s.setTel(rs.getString("Tel"));
                students.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  students;
    }
    public int getNextStudentID(int classID) {
        int id = 0;
        ArrayList<Student> student = getStudents(classID);
        int size = student.size();
        if (size != 0){
            int last = size -1;
            Student lastStudent = student.get(last);
            id = lastStudent.getID()+1;
        }
        return  id;
    }
    public  Student getStudent(int classID,int studentID){
        Student student = new Student();
        try{
            String select = "SELECT `ID`, `FirstName`, `LastName`, `Email`, `Tel` FROM `" + classID + "-Students` WHERE `ID` = "+studentID+";";
            ResultSet rs = statement.executeQuery(select);
            rs.next();
            student.setID(rs.getInt("ID"));
            student.setFirstName(rs.getString("FirstName"));
            student.setLastName(rs.getString("LastName"));
            student.setEmail(rs.getString("Email"));
            student.setTel(rs.getString("Tel"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }

    public void addStudent(int classID, Student s) {
        try{
            String query = "INSERT INTO `" + classID + " - Students` " +
                    "(`ID`, `FirstName`, `LastName`, `Email`, `Tel`) VALUES " +
                    "('" + s.getID() + "', '" + s.getFirstName() + "', '" + s.getLastName() + "', '" + s.getEmail() + "', '" + s.getTel() + "')";
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateStudent(int classID, Student s) {
        try{
            String query = "UPDATE `" + classID + " - Students` " +
                    "SET `FirstName` = '" + s.getFirstName() +
                    "', `LastName` = '" + s.getLastName() +
                    "', `Email` = '" + s.getEmail() +
                    "', `Tel` = '" + s.getTel() + "' " +
                    "WHERE `ID` = '" + s.getID() + "'";
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteStudent (int classID, int studentID) throws SQLException {
        String delete = "DELETE FROM `"+classID+" - Students ` WHERE `ID` = "+studentID+";";
        try {
            statement.execute(delete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Session> getSession(int classID) {
        ArrayList<Session> sessions = new ArrayList<>();
        try{
            String query = "SELECT * FROM `"+classID+" - Session`;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Session s = new Session();
                s.setID(rs.getInt("ID"));
                s.setSubject(rs.getString("Subject"));
                s.setDate(rs.getString("Date"));
                sessions.add(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  sessions;
    }
    public  int getNextSessionID(int classID){
        int id = 0;
        ArrayList<Session> sessions = getSession(classID);
        int size = sessions.size();
        if (size != 0){
            int last = size -1;
            Session lastSession = sessions.get(last);
            id = lastSession.getID()+1;
        }
        return id;
    }
}

