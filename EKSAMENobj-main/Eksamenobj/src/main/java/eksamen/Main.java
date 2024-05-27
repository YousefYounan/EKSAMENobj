package eksamen;

import eksamen.hotelldb.*;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.databasehenting();

        Rom rom = new Rom(db);
        rom.printut();

        Kunder kunder = new Kunder(db);
        kunder.printut();

        Reservasjoner reservasjoner = new Reservasjoner(db);
        reservasjoner.printut();

        Innsjekking innsjekking = new Innsjekking(db); // Corrected class name
        innsjekking.printut(); // Corrected method invocation

        Utsjekking utsjekking = new Utsjekking(db); // Corrected class name
        utsjekking.printut(); // Corrected method invocation

        Avbestillinger avbestillinger = new Avbestillinger(db);
        avbestillinger.printut();

        searchByKey(db);
    }
    // Her blir du spurt om informasjon for å søke etter rom
    public static void searchByKey(Database db) {
        Scanner scanner = new Scanner(System.in);
        // Her trenger du tabellnavn
        System.out.println("Enter the table name to search in (e.g., tblReservasjon): ");
        String tableName = scanner.nextLine();
        // Her trenger du kolonnenavn, f.eks romID
        System.out.println("Enter the key column name: ");
        String keyColumn = scanner.nextLine();
        // Her oppgir du romID f.eks 2.
        System.out.println("Enter the key value: ");
        int keyValue = scanner.nextInt();
        ArrayList<ArrayList<Object>> searchResult = db.searchByKey(tableName, keyColumn, keyValue);
        printSearchResult(searchResult);
    }

    private static void printSearchResult(ArrayList<ArrayList<Object>> searchResult) {
        System.out.println("Search Result:");
        for (ArrayList<Object> row : searchResult) {
            for (Object cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }
}

