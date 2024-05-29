package eksamen.hotelldb;

import java.sql.Date;
import java.sql.Timestamp;
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


    public void searchRooms() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Oppgi start dato (YYYY-MM-DD): ");
            String startDatoStr = scanner.next();
            final Date startDato = Date.valueOf(startDatoStr);

            System.out.println("Oppgi slutt dato (YYYY-MM-DD): ");
            String sluttDatoStr = scanner.next();
            final Date sluttDato = Date.valueOf(sluttDatoStr);

            System.out.println("Oppgi minimum pris: ");
            final double minPrice = scanner.nextDouble();

            System.out.println("Oppgi maksimum pris: ");
            final double maxPrice = scanner.nextDouble();

            // Søker etter ledige rom basert på pris og dato for opphold.
            ArrayList<ArrayList<Object>> availableRooms = db.searchAvailableRooms(startDato, sluttDato, minPrice, maxPrice);

            // Viser ledige rom til bruker
            System.out.println("Available Rooms:");
            printTableData("Available Rooms", availableRooms, new String[]{"Rom ID", "Rom Nummer", "RomType", "Pris"});
        } catch (InputMismatchException e) {
            System.out.println("Ugyldig data. Vennligst oppgi korrekt data.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ugyldig dato format. Vennligst bruk YYYY-MM-DD.");
        }
    }

    public void bookRoom() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Oppgi kundeID; ");
            int kundeID = scanner.nextInt();

            System.out.println("Oppgi start dato (YYYY-MM-DD): ");
            String startDatoStr = scanner.next();
            final Date startDato = Date.valueOf(startDatoStr);

            System.out.println("Oppgi slutt dato (YYYY-MM-DD): ");
            String sluttDatoStr = scanner.next();
            final Date sluttDato = Date.valueOf(sluttDatoStr);

            System.out.println("Velg ønsket rom ID: ");
            int romID = scanner.nextInt();

            db.bookRoom(kundeID, romID, startDato, sluttDato);
            System.out.println("Rommet ble booket! ReservasjonsID: ");
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

            // Får timestamp for avbestilling
            Timestamp avbestillingDato = new Timestamp(System.currentTimeMillis());

            // Kansellerer bestillingen i reservasjon og legger den til i 'avbestilt' tabell
            db.cancelReservation(reservasjonID, avbestillingDato);
            System.out.println("Reservasjon kansellert og lagt til i avbestillinger!");
        } catch (InputMismatchException e) {
            System.out.println("Ugyldig data. Vennligst oppgi en gyldig Reservasjon ID (et tall).");
        }
    }

    // Printer tabelldata hvis tilgjengelig
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
}
