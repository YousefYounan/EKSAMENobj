package eksamen.hotelldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Reservasjoner {
    private Database db;

    public Reservasjoner(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> reservasjonListe = db.getTable("tblReservasjon");
        printTableData("tblReservasjon", reservasjonListe, new String[]{"Reservasjon ID", "Kunde ID", "Rom ID", "Start Dato", "Slutt Dato", "Status"});
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

    public void placeReservation(int kundeID, int romID, Date startDato, Date sluttDato) {
        String url = "jdbc:postgresql://localhost:5432/hotell";
        String user = "hotellsjef";
        String password = "eksamen2024";

        String query = "INSERT INTO tblReservasjon (kundeID, romID, startDato, sluttDato, status) VALUES (?, ?, ?, ?, 'Bekreftet')";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, kundeID);
            preparedStatement.setInt(2, romID);
            preparedStatement.setDate(3, (java.sql.Date) startDato);
            preparedStatement.setDate(4, (java.sql.Date) sluttDato);
            preparedStatement.executeUpdate();
            System.out.println("Reservasjon plassert vellykket!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
