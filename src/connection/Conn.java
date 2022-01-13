package connection;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conn {

    private static Connection connection;

    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://localhost/claymusicstore";
            String user = "root";
            String password = "";

            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error to Call Connection", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return connection;
    }
}
