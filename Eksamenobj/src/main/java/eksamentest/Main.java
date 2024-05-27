package eksamentest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        // JDBC URL, username and password of PostgreSQL server
        String url = "jdbc:postgresql://localhost:5432/hotell"; // Replace with your PostgreSQL database URL
        String user = "hotellsjef"; // Replace with your PostgreSQL username
        String password = "eksamen2024"; // Replace with your PostgreSQL password

        // Load JDBC driver (optional for modern Java versions, as the driver is auto-loaded)
        try {
            Class.forName("org.postgresql.Driver"); // Load the PostgreSQL JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Establish a connection to the database
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            // Create a statement object to execute queries
            try (Statement statement = connection.createStatement()) {

                // Execute a SQL query and retrieve the result set from tblRom
                String query = "SELECT romID, romnummer, romtype, pris FROM tblRom";
                ResultSet resultSet = statement.executeQuery(query);

                // Iterate through the result set and print each record
                while (resultSet.next()) {
                    int romID = resultSet.getInt("romID");
                    String romnummer = resultSet.getString("romnummer");
                    String romtype = resultSet.getString("romtype");
                    double pris = resultSet.getDouble("pris");
                    System.out.println("RomID: " + romID + ", Romnummer: " + romnummer + ", Romtype: " + romtype + ", Pris: " + pris);
                }

                // Close the result set
                resultSet.close();
                System.out.println("Fått connetion!");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}