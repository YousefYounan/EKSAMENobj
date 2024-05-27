package eksamen.hotelldb;

import java.util.ArrayList;

public class Rom extends Database{
    ArrayList<ArrayList<Object>> romListe = getRomListe();
    public void printut() {

        System.out.println(romListe);
    }


}
