package eksamen.hotelldb;

import eksamen.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class Database extends Main {

    ArrayList<ArrayList<Object>> romListe = new ArrayList<>();

    public ArrayList<ArrayList<Object>> getRomListe() {
        return romListe;
    }

    public void setRomListe(ArrayList<ArrayList<Object>> romListe) {
        this.romListe = romListe;
    }

    public void databasehenting() {

        String url = "jdbc:postgresql://localhost:5432/hotell";
        String user = "hotellsjef";
        String password = "eksamen2024";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM tblRom";
                ResultSet resultSetRom = statement.executeQuery(query);


                while (resultSetRom.next()) {
                    ArrayList<Object> row = new ArrayList<>();
                    row.add(resultSetRom.getInt("romID"));
                    row.add(resultSetRom.getString("romnummer"));
                    row.add(resultSetRom.getString("romtype"));
                    row.add(resultSetRom.getDouble("pris"));
                    romListe.add(row);
                }
               // for (ArrayList<Object> row : romListe) {
               //     for (Object elem : row) {
               //         System.out.print(elem + " ");
               //     }
               //     System.out.println();
               // }

                resultSetRom.close();
                System.out.println("Connection established successfully!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}




