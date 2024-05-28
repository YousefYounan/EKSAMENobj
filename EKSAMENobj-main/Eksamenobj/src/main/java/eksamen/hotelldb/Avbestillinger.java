package eksamen.hotelldb;

import java.sql.*;
import java.util.*;

public class Avbestillinger {
    private Database db;

    private Map<String, ArrayList<ArrayList<Object>>> tableData = new HashMap<>();
    private String url = "jdbc:postgresql://localhost:5432/hotell";
    private String user = "hotellsjef";
    private String password = "eksamen2024";

    public Map<String, ArrayList<ArrayList<Object>>> getTableData() {
        return tableData;
    }
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

        // Printer tabellnavn
        System.out.println("\nData for " + tableName + ":");

        // Printer kolonne overskrifter
        for (String columnName : columnNames) {
            System.out.printf("%-15s", columnName);
        }
        System.out.println();

        // Printer rader
        for (ArrayList<Object> row : tableRows) {
            for (Object cell : row) {
                System.out.printf("%-15s", cell);
            }
            System.out.println();
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Oppgi Reservasjon ID for å kansellere ditt opphold: ");
            int reservasjonID = scanner.nextInt();

            // Får timestamp for avbestilling
            Timestamp avbestillingDato = new Timestamp(System.currentTimeMillis());

            // Henter cancelreservation med reservasjonID og avbestillingDato
            db.cancelReservation(reservasjonID, avbestillingDato);
            System.out.println("Reservasjon kansellert og lagt til i avbestillinger!");
        } catch (InputMismatchException e) {
            System.out.println("Ugyldig data. Vennligst oppgi en gyldig Reservasjon ID (et tall).");
        }
    }
}