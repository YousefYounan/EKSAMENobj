package eksamen;

import eksamen.hotelldb.*;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.databasehenting();

        Rom rom = new Rom(db);
        Kunder kunder = new Kunder(db);
        Reservasjoner reservasjoner = new Reservasjoner(db);
        Innsjekking innsjekking = new Innsjekking(db);
        Utsjekking utsjekking = new Utsjekking(db);
        Avbestillinger avbestillinger = new Avbestillinger(db);


        // TUI
        boolean avslutt = false;
        while (!avslutt) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Velg hva du vil gjøre ved å skrive inn tallet: ");
            System.out.println("1. Admin del for å legge til eller slette rom");
            System.out.println("2. Resepsjonist del for innsjekking og utsjekking");
            System.out.println("3. Gjest del for å søke etter og bestille rom, samt se og avbestille egne reservasjoner");
            System.out.println("4. Avslutt programmet");
            int valg = scanner.nextInt();

            if (valg == 1) {
                boolean romTilbake = false;
                while (!romTilbake) {
                    System.out.println("Velg mellom å legge til rom eller slette ved å skrive inn tallet: ");
                    System.out.println("1. Legge til rom");
                    System.out.println("2. Slette rom");
                    System.out.println("3. Se liste av rom");
                    System.out.println("4. Gå tilbake til hoved meny");
                    int valgRom = scanner.nextInt();

                    if (valgRom == 1) {
                        rom.LeggTilRom();
                    }
                    if (valgRom == 2) {
                        rom.sletteRom();
                    }
                    if (valgRom == 3) {
                        rom.printut();
                    }
                    if (valgRom == 4) {
                        romTilbake = true;
                    }
                }



            }


            if (valg == 4) {
                avslutt = true;
            }
        }
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

