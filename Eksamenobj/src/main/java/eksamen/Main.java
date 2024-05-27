package eksamen;

//huske å importere javafx

import eksamen.hotelldb.Database;

public class Main {
    public static void main(String[] args) {
         Database db = new Database();
         db.databasehenting();

    }
}