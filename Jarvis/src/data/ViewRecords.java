package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewRecords {

	Connection con;

    public ViewRecords() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
        try {
            con=DriverManager.getConnection("jdbc:hsqldb:mydatabase","SA","");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public void read() throws IOException {
        try {
            PreparedStatement pst=con.prepareStatement("select * from contacts");
            pst.clearParameters();
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

}