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
            // Hovedmeny
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n=== Velg ved å skrive inn tallet ===: ");
            System.out.println("1. Admin del for å legge til eller slette rom");
            System.out.println("2. Resepsjonist del for innsjekking og utsjekking");
            System.out.println("3. Gjest del for å søke etter og bestille rom, samt se og avbestille egne reservasjoner");
            System.out.println("4. Avslutt programmet");
            int valg = scanner.nextInt();

            // Tui for Admin del for slette og legge til rom
            if (valg == 1) {
                boolean romTilbake = false;
                while (!romTilbake) {
                    System.out.println("\n=== Velg ved å skrive inn tallet === ");
                    System.out.println("1. Legge til rom");
                    System.out.println("2. Slette rom");
                    System.out.println("3. Se liste av rom");
                    System.out.println("4. Gå tilbake til hoved meny");
                    int valgRom = scanner.nextInt();

                    // Legge til rom
                    if (valgRom == 1) {
                        rom.LeggTilRom();
                    }
                    // Slette rom
                    if (valgRom == 2) {
                        rom.sletteRom();
                    }
                    // Print liste av rom
                    if (valgRom == 3) {
                        rom.printut();
                    }
                    // tilbake til hovedmeny
                    if (valgRom == 4) {
                        romTilbake = true;
                    }
                }



            }
            // Tui for Resepsjonist del for innsjekking og utsjekking
            if (valg == 2) {
                boolean resepsjonistTilbake = false;
                while (!resepsjonistTilbake) {
                    System.out.println("\n=== Velg ved å skrive inn tallet ===");
                    System.out.println("1. Legg til Innsjekk");
                    System.out.println("2. Legg til Utsjekk");
                    System.out.println("3. Tilbake til hovedmeny");
                    int valgResepsjon = scanner.nextInt();

                    // Innsjekking
                    if (valgResepsjon == 1) {
                        innsjekking.checkIn();
                    }

                    // Utsjekking
                    if (valgResepsjon == 2) {
                        utsjekking.checkOut();
                    }

                    // tilbake til hovedmeny
                    if (valgResepsjon == 3) {
                        resepsjonistTilbake = true;
                    }


                }
            }
            // Tui for Gjest del for å søke etter og bestille rom, samt se og avbestille egne reservasjoner
            if (valg == 3) {
                boolean gjestTilbake = false;

                while (!gjestTilbake) {
                    System.out.println("\n=== Velg ved å skrive inn tallet ===");
                    System.out.println("1. Se din Reservasjon");
                    System.out.println("2. Søk etter et rom");
                    System.out.println("3. Book et rom");
                    System.out.println("4. Kanseller Reservasjon");
                    System.out.println("5. Gå tilbake til hoved meny");
                    int valgGjest = scanner.nextInt();

                    // Se reservasjon på id
                    if (valgGjest == 1) {
                        reservasjoner.viewReservations(db);
                    }
                    // Søk etter rom for bestilling
                    if (valgGjest == 2) {
                        reservasjoner.searchRooms();
                    }
                    // reservere rom
                    if (valgGjest == 3) {
                        reservasjoner.bookRoom();
                    }
                    // avbestille reservasjon
                    if (valgGjest == 4) {
                        avbestillinger.cancelReservation();
                    }
                    // tilbake til hovedmeny
                    if (valgGjest == 5) {
                        gjestTilbake = true;
                    }

                }
            }

            // avslutt programmet
            if (valg == 4) {
                avslutt = true;
            }
        }
    }
}

