package eksamen;

//huske å importere javafx

import eksamen.hotelldb.database;

public class Main {
    public static void main(String[] args) {
         database db = new database();
         db.databasehenting();

    }
}