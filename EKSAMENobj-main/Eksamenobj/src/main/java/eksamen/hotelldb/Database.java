package eksamen.hotelldb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, ArrayList<ArrayList<Object>>> tableData = new HashMap<>();
    private String url = "jdbc:postgresql://localhost:5432/hotell";
    private String user = "hotellsjef";
    private String password = "eksamen2024";

    public Map<String, ArrayList<ArrayList<Object>>> getTableData() {
        return tableData;
    }


    public ArrayList<ArrayList<Object>> getTable(String tableName) {
        return tableData.getOrDefault(tableName, new ArrayList<>());
    }

    public void setTable(String tableName, ArrayList<ArrayList<Object>> data) {
        tableData.put(tableName, data);
        if ("tblReservasjon".equals(tableName)) {
            ArrayList<ArrayList<Object>> reservasjonListe = tableData.getOrDefault(tableName, new ArrayList<>());
            ArrayList<Object> nyRad = new ArrayList<>();
            nyRad.add(data.size() + 1);
            nyRad.add(data.get(0).get(1)); // Kunde ID
            nyRad.add(data.get(0).get(2)); // Rom ID
            nyRad.add(data.get(0).get(3)); // Start dato
            nyRad.add(data.get(0).get(4)); // Slutt dato
            nyRad.add(data.get(0).get(5)); // Status

            reservasjonListe.add(nyRad);
            tableData.put(tableName, reservasjonListe);
        }
    }

    // Henter informasjon fra databasen
    public void databasehenting() {
        try {
            Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            fetchTableData(connection, "tblRom");
            fetchTableData(connection, "tblKunde");
            fetchTableData(connection, "tblReservasjon");
            fetchTableData(connection, "tblInnsjekking");
            fetchTableData(connection, "tblUtsjekking");
            fetchTableData(connection, "tblAvbestilling");
            System.out.println("All data fetched from the database successfully!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> searchByKey(String tableName, String keyColumn, int keyValue) {
        ArrayList<ArrayList<Object>> searchResult = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName + " WHERE " + keyColumn + " = " + keyValue;
            ResultSet resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                searchResult.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    public ArrayList<ArrayList<Object>> searchAvailableRooms(Date startDate, Date endDate, double minPrice, double maxPrice) {
        ArrayList<ArrayList<Object>> availableRooms = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            // SQL query som søker basert på kriterer.
            String query = "SELECT * FROM tblRom WHERE pris >= " + minPrice + " AND pris <= " + maxPrice +
                    " AND romID NOT IN (" +
                    "   SELECT romID FROM tblReservasjon WHERE ('" + startDate + "' BETWEEN startDato AND sluttDato" +
                    "   OR '" + endDate + "' BETWEEN startDato AND sluttDato)" +
                    "   OR ('" + startDate + "' <= startDato AND '" + endDate + "' >= sluttDato)" +
                    ")";

            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                availableRooms.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRooms;
    }


    // For å booke et rom
    public int bookRoom(int kundeID, int romID, Date startDato, Date sluttDato) {
        int reservasjonID = -1;
        String insertSQL = "INSERT INTO tblReservasjon (kundeID, romID, startDato, sluttDato, status) VALUES (?, ?, ?, ?, 'bestilt')";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, kundeID);
            preparedStatement.setInt(2, romID);
            preparedStatement.setDate(3, startDato);
            preparedStatement.setDate(4, sluttDato);
            preparedStatement.executeUpdate();

            // For å hente generert ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservasjonID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Booking feilet, ingen ID hentet");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservasjonID;
    }

    // For å kansellere en reservasjon
    public void cancelReservation(int reservasjonID, Timestamp avbestillingDato) {
        String updateSQL = "UPDATE tblReservasjon SET status = 'avbestilt' WHERE reservasjonID = ?";
        String insertSQL = "INSERT INTO tblAvbestilling (reservasjonID, avbestillingDato) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
            // Oppdaterer statusen i reservasjoner til 'avbestilt'
            updateStatement.setInt(1, reservasjonID);
            updateStatement.executeUpdate();

            // Setter avbestilling inn i  tblAvbestilling
            insertStatement.setInt(1, reservasjonID);
            insertStatement.setTimestamp(2, avbestillingDato);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Henter data fra tabell
    private void fetchTableData(Connection connection, String tableName) throws SQLException {
        ArrayList<ArrayList<Object>> tableRows = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("Fetching data from table: " + tableName);
            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                tableRows.add(row);
            }
            tableData.put(tableName, tableRows);
            System.out.println("Fetched data for " + tableName + ": " + tableRows);
        }
    }






    // LEGGE TIL OG SLETTE ROM --------------------------------------------
    public void LeggeTilRom(int romID, String romNummer, String romType, int pris) {
        String insertSQL = "INSERT INTO tblrom (romid, romnummer, romtype, pris) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, romID);
            preparedStatement.setString(2, romNummer);
            preparedStatement.setString(3, romType);
            preparedStatement.setInt(4, pris);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SletteRom(int romID) {
        String insertSQL = "DELETE FROM tblrom WHERE romid = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, romID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
