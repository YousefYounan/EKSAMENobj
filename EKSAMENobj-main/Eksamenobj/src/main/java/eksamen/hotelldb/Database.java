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

    }


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

    public void bookRoom(int kundeID, int romID, Date startDato, Date sluttDato) {
        String insertSQL = "INSERT INTO tblReservasjon (kundeID, romID, startDato, sluttDato, status) VALUES (?, ?, ?, ?, 'bestilt')";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, kundeID);
            preparedStatement.setInt(2, romID);
            preparedStatement.setDate(3, startDato);
            preparedStatement.setDate(4, sluttDato);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(int reservasjonID) {
        String updateSQL = "UPDATE tblReservasjon SET status = 'avbestilt' WHERE reservasjonID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, reservasjonID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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


}
