package eksamen.hotelldb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Reservasjoner {
    private Database db;

    public Reservasjoner(Database db) {
        this.db = db;
    }


    public void viewReservations(Database db) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Reservation ID to view your reservation details: ");
        int reservationID = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        ArrayList<ArrayList<Object>> searchResult = db.searchByKey("tblReservasjon", "reservasjonID", reservationID);
        printTableData("Search Result", searchResult, new String[]{"Reservasjon ID", "Kunde ID", "Rom ID", "Start Dato", "Slutt Dato", "Status"});
    }


    public void bookRoom() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Oppgi Kunde ID: ");
            int kundeID = scanner.nextInt();

            System.out.println("Oppgi Rom ID til rommet du ønsker: ");
            int romID = scanner.nextInt();

            System.out.println("Oppgi start dato (YYYY-MM-DD): ");
            String startDatoStr = scanner.next();
            Date startDato = Date.valueOf(startDatoStr);

            System.out.println("Oppgi slutt date (YYYY-MM-DD): ");
            String sluttDatoStr = scanner.next();
            Date sluttDato = Date.valueOf(sluttDatoStr);

            db.bookRoom(kundeID, romID, startDato, sluttDato);
            System.out.println("Rommet ble booket!");
        } catch (InputMismatchException e) {
            System.out.println("Ugyldig data. Vennligst oppgi korrekt data.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig dato format. Vennligst bruk YYYY-MM-DD.");
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Oppgi Reservasjon ID for å kansellere ditt opphold: ");
            int reservasjonID = scanner.nextInt();
            db.cancelReservation(reservasjonID);
            System.out.println("Reservasjon kansellert!");
        } catch (InputMismatchException e) {
            System.out.println("Ugyldig data. Vennligst oppi en gyldig Reservasjon ID (et tall).");
        }
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
