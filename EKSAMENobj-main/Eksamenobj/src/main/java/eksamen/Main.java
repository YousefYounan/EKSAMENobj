package eksamen;

//huske å importere javafx

import eksamen.hotelldb.Database;
import eksamen.hotelldb.Rom;

public class Main {
    public static void main(String[] args) {
         Database db = new Database();
         db.databasehenting();

         Rom rom = new Rom();
         rom.printut();

    }
}