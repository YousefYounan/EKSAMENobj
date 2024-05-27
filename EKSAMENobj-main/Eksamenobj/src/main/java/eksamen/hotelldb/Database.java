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

    public void databasehenting() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            fetchTableData(connection, "tblRom");
            fetchTableData(connection, "tblKunde");
            fetchTableData(connection, "tblReservasjon");
            fetchTableData(connection, "tblInnsjekking");
            fetchTableData(connection, "tblUtsjekking");
            fetchTableData(connection, "tblAvbestilling");
            System.out.println("All data fetched from the database successfully!");
        } catch (SQLException e) {
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

    private void fetchTableData(Connection connection, String tableName) throws SQLException {
        ArrayList<ArrayList<Object>> tableRows = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName;
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Fetching data from table: " + tableName);
            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                switch (tableName) {
                    case "tblRom":
                        row.add(resultSet.getInt("romID"));
                        row.add(resultSet.getString("romnummer"));
                        row.add(resultSet.getString("romtype"));
                        row.add(resultSet.getDouble("pris"));
                        break;
                    case "tblKunde":
                        row.add(resultSet.getInt("kundeID"));
                        row.add(resultSet.getString("navn"));
                        row.add(resultSet.getString("epost"));
                        row.add(resultSet.getString("telefon"));
                        break;
                    case "tblReservasjon":
                        row.add(resultSet.getInt("reservasjonID"));
                        row.add(resultSet.getInt("kundeID"));
                        row.add(resultSet.getInt("romID"));
                        row.add(resultSet.getDate("startDato"));
                        row.add(resultSet.getDate("sluttDato"));
                        row.add(resultSet.getString("status"));
                        break;
                    case "tblInnsjekking":
                        row.add(resultSet.getInt("innsjekkingID"));
                        row.add(resultSet.getInt("reservasjonID"));
                        row.add(resultSet.getTimestamp("innsjekkingDato"));
                        break;
                    case "tblUtsjekking":
                        row.add(resultSet.getInt("utsjekkingID"));
                        row.add(resultSet.getInt("reservasjonID"));
                        row.add(resultSet.getTimestamp("utsjekkingDato"));
                        break;
                    case "tblAvbestilling":
                        row.add(resultSet.getInt("avbestillingID"));
                        row.add(resultSet.getInt("reservasjonID"));
                        row.add(resultSet.getTimestamp("avbestillingDato"));
                        break;
                }
                tableRows.add(row);
            }
            resultSet.close();
            tableData.put(tableName, tableRows);

            System.out.println("Fetched data for " + tableName + ": " + tableRows);
        }
    }
}
