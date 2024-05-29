package eksamen.hotelldb;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
public class Kunder {
    private Database db;

    public Kunder(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> kundeListe = db.getTable("tblKunde");
        printTableData("tblKunde", kundeListe, new String[]{"Kunde ID", "Navn", "Epost", "Telefon"});
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

    public void leggTilKunde(){
        String url = "jdbc:postgresql://localhost:5432/hotell";
        String user = "hotellsjef";
        String password = "eksamen2024";

        Scanner inputs = new Scanner(System.in);//Lager scanner for å lese user input

        System.out.println("Skriv inn navn: ");
        String navn = inputs.nextLine();


        System.out.println("Skriv inn epost: ");//Lager scanner for å lese user input
        String epost = inputs.nextLine();


        System.out.println("Skriv inn telefon: ");//Lager scanner for å lese user input telefon
        String telefon = inputs.nextLine();

        //Query for å Registrere data
        String query = "INSERT INTO tblKunde (navn, epost,telefon) VALUES (?, ?, ?)";


        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, (navn));
            preparedStatement.setString(2, (epost));
            preparedStatement.setInt(3,Integer.parseInt(telefon));
            preparedStatement.executeUpdate();

            System.out.println("Ny kunde registrert! ");

            //Henting av nyeste ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys(); //Henter nyeste ID
            int kundeID = 0; // Lager en variabel for å lagre ID
            if(generatedKeys.next()){ //Gjør en test på ID
                kundeID = generatedKeys.getInt(1); // Legger Verdien i ID inn i innsjekkingID
            }

            //Legger til data inn i Hash Map.
            ArrayList<ArrayList<Object>> kundeListe = db.getTable("tblKunde");
            ArrayList<Object> nyRad = new ArrayList<>(); //Lager en ny rad inn i ArrayListen
            nyRad.add(kundeID); // InnsjekkingID
            nyRad.add(navn); // ReservasjonID
            nyRad.add(epost); //InnsjekingsDato
            nyRad.add(telefon); //Telefon

            kundeListe.add(nyRad);//legger til rad inn i InnsjekkingListe
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
