package eksamen.hotelldb;

import java.util.ArrayList;

public class Kunder {
    private Database db;

    public Kunder(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> kundeListe = db.getTable("tblKunde");
        printTableData("tblKunde", kundeListe, new String[]{"Kunde ID", "Navn", "Epost", "Telefon"});
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
}
