package eksamen.hotelldb;

import java.util.ArrayList;
import java.util.Scanner;

public class Reservasjoner {
    private Database db;

    public Reservasjoner(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> reservasjonListe = db.getTable("tblReservasjon");
        printTableData("tblReservasjon", reservasjonListe, new String[]{"Reservasjon ID", "Kunde ID", "Rom ID", "Start Dato", "Slutt Dato", "Status"});
    }

    public void searchByKey() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Reservasjon ID to search for: ");
        int reservasjonID = scanner.nextInt();
        ArrayList<ArrayList<Object>> searchResult = db.searchByKey("tblReservasjon", "reservasjonID", reservasjonID);
        printTableData("Search Result", searchResult, new String[]{"Reservasjon ID", "Kunde ID", "Rom ID", "Start Dato", "Slutt Dato", "Status"});
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
