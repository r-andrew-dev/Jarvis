package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {

	Connection con;
    public Register() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
        try {
            con=DriverManager.getConnection("jdbc:hsqldb:mydatabase","SA","");
            con.createStatement().executeUpdate("create table contacts (name varchar(45),email varchar(45),phone varchar(45))");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public void insert(String name, String email, String phone) throws IOException {

        try {
            PreparedStatement pst=con.prepareStatement("insert into contacts values(?,?,?)");
            pst.clearParameters();
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}