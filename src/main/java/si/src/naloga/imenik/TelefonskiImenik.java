package si.src.naloga.imenik;

import si.src.naloga.kontakt.Kontakt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.EOFException;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class TelefonskiImenik {

    private List<Kontakt> seznamKontaktov;

    public TelefonskiImenik() {
        seznamKontaktov = new ArrayList<>();
    }

    public void ustvariKontakt() {
        Kontakt kontakt = new Kontakt();

        kontakt.setId(1);
        kontakt.setIme("Tokyo");
        kontakt.setPriimek("Oliviera");
        kontakt.setNaslov("Madrid 123");
        kontakt.setElektronskaPosta("casa@gmail.com");
        kontakt.setTelefon("+386 51 051 051");
        kontakt.setMobilniTelefon("+386 52 052 052");
        kontakt.setOpomba("bella ciao");

        seznamKontaktov.add(kontakt);
        kontakt = new Kontakt();

        kontakt.setId(2);
        kontakt.setIme("Helsi");
        kontakt.setPriimek("Lalal");
        kontakt.setNaslov("Madrid 12344");
        kontakt.setElektronskaPosta("casadepapel@gmail.com");
        kontakt.setTelefon("+386 51 052 451");
        kontakt.setMobilniTelefon("+386 00 052 052");
        kontakt.setOpomba("bella ciao helsi");

        seznamKontaktov.add(kontakt);
        kontakt = new Kontakt();

        kontakt.setId(3);
        kontakt.setIme("Rio");
        kontakt.setPriimek("Flores");
        kontakt.setNaslov("Barcelona 123");
        kontakt.setElektronskaPosta("casaaaa@gmail.com");
        kontakt.setTelefon("+386 50 000 454");
        kontakt.setMobilniTelefon("+386 11 111 111");
        kontakt.setOpomba("me gusta");

        seznamKontaktov.add(kontakt);
    }

    public static void izpisiMenuZaUrejanje() {

        System.out.println("");
        System.out.println("");
        System.out.println("Izberite podatek kontakta, ki ga želite spremeniti ali vpišite 99 za izhod iz urejanja:");
        System.out.println("-----------------------------------");
        System.out.println("1 - ime");
        System.out.println("2 - priimek");
        System.out.println("3 - naslov");
        System.out.println("4 - elektronskaPosta");
        System.out.println("5 - telefon");
        System.out.println("6 - mobilniTelefon");
        System.out.println("7 - opomba");
        System.out.println("");
        System.out.println("99 - izhod iz urejanja");
        System.out.println("----------------------------------");
        System.out.println("");

    }

    public static Kontakt poisciID(List<Kontakt> seznamKontaktov, int tempID, String input) {
        for (Kontakt kont : seznamKontaktov) {
            if (kont.getId() == tempID) {
                return kont;
            } 
        }
        System.out.println("Ni kontakta s tem ID-jem");
        return null;
    }

    /**
     * Metoda izpiše vse kontakte
     */
    public void izpisiVseKontakte() {
        
        if (seznamKontaktov.size() == 0) {
            System.out.println();
            System.out.println("V imeniku še ni nobenega kontakta. Dodajte kontakt in poskusite znova.");
        } else {
            for (Kontakt kontakt : seznamKontaktov) {
                System.out.println();
                System.out.println(kontakt.toString());
            }
        }
    }

    /**
     * Metoda doda nov kontakt v imenik
     * onemogočimo dodajanje dupliciranega kontakta
     */

    public void dodajKontakt() {

        Scanner sc = new Scanner(System.in);
        Kontakt kontakt = new Kontakt();
        System.out.print("Vnesite id: ");

        boolean preveri = false;
        String input = "";

        while(!preveri) {
            input = sc.next();
            try {
                kontakt.setId(Integer.parseInt(input));
                preveri = true;
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite število: ");
            } 
        }

        System.out.print("Vnesite ime: ");
        kontakt.setIme(sc.next());
        System.out.print("Vnesite priimek: ");
        kontakt.setPriimek(sc.next());
        System.out.print("Vnesite naslov: ");
        kontakt.setNaslov(sc.next());
        System.out.print("Vnesite elektronsko pošto: ");
        kontakt.setElektronskaPosta(sc.next());
        System.out.print("Vnesite telefon: ");
        kontakt.setTelefon(sc.next());
        System.out.print("Vnesite mobilni telefon: ");
        kontakt.setMobilniTelefon(sc.next());
        System.out.print("Vnesite opombo: ");
        kontakt.setOpomba(sc.next());

        boolean zeObstaja = false;

        for (Kontakt kont : seznamKontaktov) {

            if (kontakt.getId() == kont.getId()) {
                System.out.println("Kontakt s tem Id-jem že obstaja. Vnesite drugačen Id.");
                zeObstaja = true;
            }
            
            else if (kontakt.equals(kont)) {
                System.out.println("Kontakt že obstaja. Vnesite drugačne podatke.");
                zeObstaja = true;
            } 
        }

        if (!zeObstaja) {
            seznamKontaktov.add(kontakt);
            System.out.println("Kontakt uspešno dodan.");
        }
    }

    /**
     * Metoda popravi podatke na obstoječem kontaktu
     * ID kontakta ni mogoče spreminjati
     */
    public void urediKontakt() {

        izpisiVseKontakte();

        Scanner sc = new Scanner(System.in);
        Kontakt kontakt = new Kontakt();
        boolean preveri = false;
        String akcija = "";
        String input = "";
        int tempID = 0;
        boolean z = false;

        System.out.println();
        System.out.print("Vnesite ID kontakta, ki ga želite spremeniti: ");

        while(!preveri) {
            input = sc.next();
            try {
                tempID = Integer.parseInt(input);
                preveri = true;
                izpisiMenuZaUrejanje();
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite število: ");
            }
        }

        //System.out.println("zelimo spremenit kontakt z id = " + tempID);

        boolean flag = false;

        while (!"99".equals(akcija)) {
            akcija = sc.next();
            Kontakt k;

            switch (akcija) {

                case "99":
                    System.out.println("Urejanje končano.");
                    flag = true;
                    break;

                case "1":
                    System.out.println("Vpišite novo ime kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setIme(input);

                    break;
                case "2":
                    System.out.println("Vpišite nov priimek kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setPriimek(input);
                    break;
                case "3":
                    System.out.println("Vpišite nov naslov kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setNaslov(input);
                    break;
                case "4":
                    System.out.println("Vpišite novo elektronsko pošto kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setElektronskaPosta(input);
                    break;
                case "5":
                    System.out.println("Vpišite nov telefon kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setTelefon(input);
                    break;
                case "6":
                    System.out.println("Vpišite nov mobilni telefon kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setMobilniTelefon(input);
                    break;
                case "7":
                    System.out.println("Vpišite novo opombo kontakta: ");
                    input = sc.next();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setOpomba(input);
                    break;
                default:
                    System.out.println("Napačna izbira!");
                    break;
            }
            
            if (flag) {
                flag = false;
                break;
            }
            izpisiMenuZaUrejanje();            
        }
    }

    /**
     * Brisanje kontakta po ID-ju
     */
    public void izbrisiKontaktPoId() {

        Scanner sc = new Scanner(System.in);
        Kontakt kontakt = new Kontakt();
        System.out.print("Vnesite id: ");

        boolean preveri = false;
        String input = "";
        int tempID = 0;

        while(!preveri) {
            input = sc.next();
            try {
                tempID = Integer.parseInt(input);
                preveri = true;
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite število: ");
            }
        }

        for (Kontakt kont : seznamKontaktov) {
            if (kont.getId() == tempID) {
                seznamKontaktov.remove(kont);
                break;
            }
        }
    }
    /**
     * Izpis kontakta po ID-ju
     */
    public void izpisiKontaktZaId() {

        Scanner sc = new Scanner(System.in);
        Kontakt kontakt = new Kontakt();
        System.out.print("Vnesite id: ");

        boolean preveri = false;
        String input = "";
        int tempID = 0;

        while(!preveri) {
            input = sc.next();
            try {
                tempID = Integer.parseInt(input);
                preveri = true;
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite število: ");
            }
        }

        for (Kontakt kont : seznamKontaktov) {
            if (kont.getId() == tempID) {
                System.out.println();
                System.out.println(kont.toString());
                break;
            }
        }
    }

    /**
     * Izpis kontakta po ID-ju
     */
    public void izpisiSteviloKontaktov() {
        System.out.println();
        System.out.println("Število kontaktov v telefonskem imeniku je " + seznamKontaktov.size() + ".");
    }

    /**
     * Serializiraj seznam kontoktov na disk.
     * Ime datoteke naj bo "kontakti.ser"
     */
    public void serializirajSeznamKontaktov() { // readObject, writeObject methods
        
        try {
            FileOutputStream fileOut = new FileOutputStream("./src/main/resources/imenik.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            for (Kontakt kontakt : seznamKontaktov) {
                objectOut.writeObject(kontakt);
            }

            //objectOut.writeObject(seznamKontaktov);
            objectOut.close();
            fileOut.close();
            System.out.println("Serializiran seznam je shranjen v ./src/main/resources/imenik.ser");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pereberi serializiran seznam kontakotv iz diska
     */
    public void naloziSerializiranSeznamKontaktov() {

        Kontakt kontakt = null;

        try {
            FileInputStream fileIn = new FileInputStream("./src/main/resources/imenik.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            while(true) {
                try {
                    kontakt = (Kontakt) objectIn.readObject();
                    System.out.println(kontakt.toString());
                } catch (EOFException f) {
                    break;
                }
            }
            objectIn.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException n) {
            n.printStackTrace();
            return;
        }
    }

    /**
     * Izvozi seznam kontakov CSV datoteko.
     * Naj uporabnik sam izbere ime izhodne datoteke.
     */
    public void izvoziPodatkeVCsvDatoteko() {

        try {
            Kontakt kont = new Kontakt();
            FileWriter fw = new FileWriter("./src/main/resources/imenik.csv");
            fw.append(kont.prvaVrsticaCSV());
            for (Kontakt kontakt : seznamKontaktov) {
                fw.append(kontakt.podatkiCSV());
            }
            
            fw.flush();
            fw.close();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
