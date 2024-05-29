package eksamen.hotelldb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Utsjekking {
    private Database db;

    // Constructor
    public Utsjekking(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> utsjekkingListe = db.getTable("tblUtsjekking");
        printTableData("tblUtsjekking", utsjekkingListe, new String[]{"Utsjekking ID", "Reservasjon ID", "Utsjekking Dato"});
    }

    private void printTableData(String tableName, ArrayList<ArrayList<Object>> tableRows, String[] columnNames) {
        if (tableRows == null || tableRows.isEmpty()) {
            System.out.println("No data found for table: " + tableName);
            return;
        }

        // Printer tabell navn
        System.out.println("\nData for " + tableName + ":");

        // Printer kolonne navn
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

    public void checkOut() {
        String url = "jdbc:postgresql://localhost:5432/hotell";
        String user = "hotellsjef";
        String password = "eksamen2024";

        //Endre til Cheeckout
        Scanner inputs = new Scanner(System.in);//Lager scanner for å lese user input
        System.out.println("Skriv inn reservasjonID: ");
        String reservasjonID = inputs.nextLine(); //Setter verdien i en String
        Timestamp utsjekkingDato = new Timestamp(System.currentTimeMillis()); //Henter inn tiden

        
        String query = "INSERT INTO tblUtsjekking (reservasjonID, utsjekkingDato) VALUES (?, ?)";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(reservasjonID));
            preparedStatement.setTimestamp(2, utsjekkingDato);
            preparedStatement.executeUpdate();
            System.out.println("Utsjekking vellykket!");



            //Henting av nyeste ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); //Henter nyeste ID
            int utsjekkingID = 0; // Lager en variabel for å lagre ID
            if(generatedKeys.next()){ //Gjør en test på ID
                utsjekkingID = generatedKeys.getInt(1); // Legger Verdien i ID inn i innsjekkingID
            }

            //Legger til data inn i Hash Map.
            ArrayList<ArrayList<Object>> utsjekkingListe = db.getTable("tblUtsjekking");
            ArrayList<Object> nyRad = new ArrayList<>(); //Lager en ny rad inn i ArrayListen
            nyRad.add(utsjekkingID); // utsjekkingID
            nyRad.add(reservasjonID); // ReservasjonID
            nyRad.add(utsjekkingDato); //InnsjekingsDato

            utsjekkingListe.add(nyRad);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


//Scanner input for å få inn data
// - Ha en egen funskjon som heter leggtilInnsjekk (som legger innsjekk basert på innput verider inn i ArrayList)
//Ta verdiene sette de inn i ArrayList/Hash map
