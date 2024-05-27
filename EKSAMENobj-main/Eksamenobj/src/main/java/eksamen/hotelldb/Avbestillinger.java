package eksamen.hotelldb;

import java.sql.*;
import java.util.ArrayList;

public class Avbestillinger {
    private Database db;

    public Avbestillinger(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> avbestillingListe = db.getTable("tblAvbestilling");
        printTableData("tblAvbestilling", avbestillingListe, new String[]{"Avbestilling ID", "Reservasjon ID", "Avbestilling Dato"});
    }

    private void printTableData(String tableName, ArrayList<ArrayList<Object>> tableRows, String[] columnNames) {
        if (tableRows == null || tableRows.isEmpty()) {
            System.out.println("No data found for table: " + tableName);
            return;
        }

        // Print table name
        System.out.println("\nData for " + tableName + ":");

        // Print column headers
        for (String columnName : columnNames) {
            System.out.printf("%-15s", columnName);
        }
        System.out.println();

        // Print rows
        for (ArrayList<Object> row : tableRows) {
            for (Object cell : row) {
                System.out.printf("%-15s", cell);
            }
            System.out.println();
        }
    }

    public void cancelReservation(int reservasjonID, Timestamp avbestillingDato) {
        String url = "jdbc:postgresql://localhost:5432/hotell";
        String user = "hotellsjef";
        String password = "eksamen2024";

        String query = "INSERT INTO tblAvbestilling (reservasjonID, avbestillingDato) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservasjonID);
            preparedStatement.setTimestamp(2, avbestillingDato);
            preparedStatement.executeUpdate();
            System.out.println("Avbestilling vellykket!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}