package UserInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    // Vigtigt: Sæt versionsnummer så objekt kan læses selvom klassen er ændret!

    private static final long serialVersionUID = 12345; // bare et eller andet nr.

    public String brugernavn; // studienummer
    public String email = "hvad@ved.jeg.dk";
    public long sidstAktiv;
    public String campusnetId; // campusnet database-ID
    public String studeretning = "ukendt";
    public String fornavn = "test";
    public String efternavn = "testesen";
    public String adgangskode;
    public HashMap<String, Object> ekstraFelter = new HashMap<>();
    
    //added
    public ArrayList<Device> devices = new ArrayList<>(); 
    public long lastDataPull;
    
    public String toString() {
        return brugernavn + "/" + adgangskode;
    }

}
