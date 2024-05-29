package eksamen.hotelldb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Innsjekking {
    private Database db;

    // Constructor
    public Innsjekking(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> innsjekkingListe = db.getTable("tblInnsjekking");
        printTableData("tblInnsjekking", innsjekkingListe, new String[]{"Innsjekking ID", "Reservasjon ID", "Innsjekking Dato"});
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

    public void checkIn() {
        String url = "jdbc:postgresql://localhost:5432/hotell";
        String user = "hotellsjef";
        String password = "eksamen2024";

        Scanner inputs = new Scanner(System.in);//Lager scanner for å lese user input
        System.out.println("Skriv inn reservasjonID: ");
        String reservasjonID = inputs.nextLine(); //Setter verdien i en String
        Timestamp innsjekkingDato = new Timestamp(System.currentTimeMillis()); //Henter inn tiden

        //Query for å Registrere data
        String query = "INSERT INTO tblInnsjekking (reservasjonID, innsjekkingDato) VALUES (?, ?)";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, Integer.parseInt(reservasjonID));//Omgjør veriden til en Int
            preparedStatement.setTimestamp(2, (innsjekkingDato));
            preparedStatement.executeUpdate();
            System.out.println("Innsjekking vellykket!");


            //Henting av nyeste ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); //Henter nyeste ID
            int innsjekkingID = 0; // Lager en variabel for å lagre ID
            if(generatedKeys.next()){ //Gjør en test på ID
                innsjekkingID = generatedKeys.getInt(1); // Legger Verdien i ID inn i innsjekkingID
            }


            //Legger til data inn i Hash Map.
            ArrayList<ArrayList<Object>> InnsjekkingListe = db.getTable("tblInnsjekking");
            ArrayList<Object> nyRad = new ArrayList<>(); //Lager en ny rad inn i ArrayListen
            nyRad.add(innsjekkingID); // InnsjekkingID
            nyRad.add(reservasjonID); // ReservasjonID
            nyRad.add(innsjekkingDato); //InnsjekingsDato

            InnsjekkingListe.add(nyRad);//legger til rad inn i InnsjekkingListe

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


