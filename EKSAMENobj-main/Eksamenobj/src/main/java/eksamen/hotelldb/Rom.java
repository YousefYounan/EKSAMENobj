package eksamen.hotelldb;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Rom {
    private Database db;

    public Rom(Database db) {
        this.db = db;
    }

    public void printut() {
        ArrayList<ArrayList<Object>> romListe = db.getTable("tblRom");
        printTableData("tblRom", romListe, new String[]{"Rom ID", "Romnummer", "Romtype", "Pris"});
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
    public boolean checkIfRoomExists(int romID) {
        Map<String, ArrayList<ArrayList<Object>>> tableData = db.getTableData();
        ArrayList<ArrayList<Object>> romListe = tableData.get("tblRom");
        for (ArrayList<Object> row : romListe) {
            int id = (int) row.get(0); // Assuming the first column is the ID
            if (id == romID) {
                return true;
            }
        }
        return false;
    }

    // Legge til Rom
    public void LeggTilRom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Skriv inn ID for rommet du vil legge til: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Skriv inn rom nummer:  ");
        String romNummer = scanner.nextLine();

        System.out.println("Skriv inn rom type:  ");
        String romType = scanner.nextLine();

        System.out.println("Skriv inn pris for rommet: ");
        int pris = scanner.nextInt();


        db.LeggeTilRom(id, romNummer, romType, pris);

        ArrayList<ArrayList<Object>> romListe = db.getTable("tblRom");
        ArrayList<Object> nyRad = new ArrayList<>();
        nyRad.add(id); // Rom ID
        nyRad.add(romNummer); // Romnummer
        nyRad.add(romType); // Romtype
        nyRad.add(pris); // Pris

        romListe.add(nyRad);

        System.out.println("Rommet ble lagt til!");
    }

    // Slette Rom
    public void sletteRom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Skriv inn ID for rommet du vil slette: ");
        int id = scanner.nextInt();

        db.SletteRom(id);
       ArrayList<ArrayList<Object>> romListe = db.getTable("tblRom");
       romListe.removeIf(rad -> (int) rad.get(0) == id);

        System.out.println("Rommet ble slettet!");
    }
}
