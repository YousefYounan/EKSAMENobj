package eksamen;

import eksamen.hotelldb.Database;
import eksamen.hotelldb.Rom;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.databasehenting();

        Rom rom = new Rom(db); // Pass database instance to Rom
        rom.printut();
    }
}
