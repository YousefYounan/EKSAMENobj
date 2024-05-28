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
}

