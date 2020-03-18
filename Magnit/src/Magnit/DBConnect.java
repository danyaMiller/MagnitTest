package Magnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
    private String url = "jdbc:mysql://localhost:3306/magnit";
    private String userName = "root";
    private String password = "password";
    private int n;
    private Connection connection;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setN(Integer n) {
        this.n = n;
    }


    DBConnect(String url, String userName, String password, int n) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.n = n;

    }
    DBConnect() {

    }
    void setConnection() {
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection(url,userName,password);
         } catch (ClassNotFoundException | SQLException e) {
             e.printStackTrace();
         }
    }
    void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void fillInTable() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS TEST(FIELD INT PRIMARY KEY)");
            statement.execute("DELETE FROM TEST");
            statement.close();
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO TEST VALUES (?)");
            for (int i = 1; i < n; i++) {
                statement1.setInt(1,i);
                statement1.executeUpdate();
            }
            statement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    List<Integer> select() {
        List<Integer> result = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM TEST");

            while (rs.next()) {
                result.add(rs.getInt("FIELD"));
            }
            st.close();
            rs.close();

         } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }




}
