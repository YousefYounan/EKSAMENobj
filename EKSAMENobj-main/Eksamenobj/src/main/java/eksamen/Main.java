package eksamen;

import eksamen.hotelldb.Database;
import eksamen.hotelldb.Reservasjoner;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.databasehenting();



        displayAllData(db);

        Reservasjoner reservasjoner = new Reservasjoner(db);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    reservasjoner.viewReservations(db);
                    break;
                case 2:
                    reservasjoner.bookRoom();
                    break;
                case 3:
                    reservasjoner.cancelReservation();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (choice != 4);
        scanner.close();
    }

    private static void displayAllData(Database db) {
        printTableData("tblRom", db);
        printTableData("tblKunde", db);
        printTableData("tblReservasjon", db);
        printTableData("tblInnsjekking", db);
        printTableData("tblUtsjekking", db);
        printTableData("tblAvbestilling", db);
    }

    private static void displayMenu() {
        System.out.println("\n=== Hotel Management System ===");
        System.out.println("1. Se din Reservasjon");
        System.out.println("2. Book et rom");
        System.out.println("3. Kanseller Reservasjon");
        System.out.println("4. Avslutt");
        System.out.print("Enter your choice: ");
    }

    private static void printTableData(String tableName, Database db) {
        ArrayList<ArrayList<Object>> tableData = db.getTable(tableName);
        System.out.println("Data for table: " + tableName);
        for (ArrayList<Object> row : tableData) {
            for (Object cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
