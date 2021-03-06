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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.EOFException;
import java.sql.*;

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
        System.out.println("Izberite podatek kontakta, ki ga ??elite spremeniti ali vpi??ite 99 za izhod iz urejanja:");
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
     * Metoda izpi??e vse kontakte
     */
    public void izpisiVseKontakte() {
        
        if (seznamKontaktov.size() == 0) {
            System.out.println();
            System.out.println("V imeniku ni nobenega kontakta. Dodajte kontakt in poskusite znova.");
        } else {
            for (Kontakt kontakt : seznamKontaktov) {
                System.out.println(kontakt.toString());
            }
        }
    }

    /**
     * Metoda doda nov kontakt v imenik
     * onemogo??imo dodajanje dupliciranega kontakta
     */

    public void dodajKontakt() {

        Scanner sc = new Scanner(System.in);
        Kontakt kontakt = new Kontakt();
        System.out.print("Vnesite id: ");

        boolean preveri = false;
        String input = "";

        while(!preveri) {
            input = sc.nextLine();
            try {
                kontakt.setId(Integer.parseInt(input));
                preveri = true;
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite ??tevilo: ");
            } 
        }

        System.out.print("Vnesite ime: ");
        kontakt.setIme(sc.nextLine());
        System.out.print("Vnesite priimek: ");
        kontakt.setPriimek(sc.nextLine());
        System.out.print("Vnesite naslov: ");
        kontakt.setNaslov(sc.nextLine());
        System.out.print("Vnesite elektronsko po??to: ");
        kontakt.setElektronskaPosta(sc.nextLine());
        System.out.print("Vnesite telefon: ");
        kontakt.setTelefon(sc.nextLine());
        System.out.print("Vnesite mobilni telefon: ");
        kontakt.setMobilniTelefon(sc.nextLine());
        System.out.print("Vnesite opombo: ");
        kontakt.setOpomba(sc.nextLine());

        boolean zeObstaja = false;

        for (Kontakt kont : seznamKontaktov) {

            if (kontakt.getId() == kont.getId()) {
                System.out.println("Kontakt s tem Id-jem ??e obstaja. Vnesite druga??en Id.");
                zeObstaja = true;
            }
            
            else if (kontakt.equals(kont)) {
                System.out.println("Kontakt ??e obstaja. Vnesite druga??ne podatke.");
                zeObstaja = true;
            } 
        }

        if (!zeObstaja) {
            seznamKontaktov.add(kontakt);
            System.out.println("Kontakt uspe??no dodan.");
        }
    }

    /**
     * Metoda popravi podatke na obstoje??em kontaktu
     * ID kontakta ni mogo??e spreminjati
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
        System.out.print("Vnesite ID kontakta, ki ga ??elite spremeniti: ");

        while(!preveri) {
            input = sc.nextLine();
            try {
                tempID = Integer.parseInt(input);
                preveri = true;
                izpisiMenuZaUrejanje();
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite ??tevilo: ");
            }
        }

        boolean flag = false;

        //TODO: popravi scanner za imena in ostale stringe

        while (!"99".equals(akcija)) {
            akcija = sc.nextLine();
            Kontakt k;

            switch (akcija) {

                case "99":
                    System.out.println("Urejanje kon??ano.");
                    flag = true;
                    break;

                case "1":
                    System.out.println("Vpi??ite novo ime kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setIme(input);
                    break;
                case "2":
                    System.out.println("Vpi??ite nov priimek kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setPriimek(input);
                    break;
                case "3":
                    System.out.println("Vpi??ite nov naslov kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setNaslov(input);
                    break;
                case "4":
                    System.out.println("Vpi??ite novo elektronsko po??to kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setElektronskaPosta(input);
                    break;
                case "5":
                    System.out.println("Vpi??ite nov telefon kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setTelefon(input);
                    break;
                case "6":
                    System.out.println("Vpi??ite nov mobilni telefon kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setMobilniTelefon(input);
                    break;
                case "7":
                    System.out.println("Vpi??ite novo opombo kontakta: ");
                    input = sc.nextLine();
                    k = poisciID(seznamKontaktov, tempID, input);
                    k.setOpomba(input);
                    break;
                default:
                    System.out.println("Napa??na izbira!");
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
            input = sc.nextLine();
            try {
                tempID = Integer.parseInt(input);
                preveri = true;
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite ??tevilo: ");
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
            input = sc.nextLine();
            try {
                tempID = Integer.parseInt(input);
                preveri = true;
            } catch (NumberFormatException e) {
                System.out.print("Napaka! Vnesite ??tevilo: ");
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
        System.out.println("??tevilo kontaktov v telefonskem imeniku je " + seznamKontaktov.size() + ".");
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

            System.out.println("Podatki so shranjeni v ./src/main/resources/imenik.csv");
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shraniPodatkeVBazo() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ime baze, po kateri i????emo: ");
        String imeSheme = sc.nextLine();

        System.out.print("Uporabnik: ");
        String usrSheme = sc.nextLine();

        System.out.print("Geslo: ");
        String pwdSheme = sc.nextLine();

        System.out.print("Id: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Ime: ");
        String ime = sc.nextLine();

        System.out.print("Priimek: ");
        String priimek = sc.nextLine();

        System.out.print("Naslov: ");
        String naslov = sc.nextLine();

        System.out.print("Elektronska po??ta: ");
        String elektronskaPosta = sc.nextLine();

        System.out.print("Telefon: ");
        String telefon = sc.nextLine();

        System.out.print("Mobilni telefon: ");
        String mobilniTelefon = sc.nextLine();

        System.out.print("Opomba: ");
        String opomba = sc.nextLine();

        System.out.println();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+imeSheme+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", usrSheme, pwdSheme);
            Statement statement = con.createStatement();
            statement.executeUpdate("insert into tabelaKontaktov values ("+id+", '"+ime+"', '"+priimek+"','"+naslov+"','"+elektronskaPosta+"','"+telefon+"','"+mobilniTelefon+"','"+opomba+"')");
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void preberiPodatkeIzBaze() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ime baze: ");
        String imeSheme = sc.nextLine();

        System.out.print("Uporabnik: ");
        String usrSheme = sc.nextLine();

        System.out.print("Geslo: ");
        String pwdSheme = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+imeSheme+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", usrSheme, pwdSheme);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from tabelaKontaktov");

            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void isciPodatkeVBazi() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ime baze: ");
        String imeSheme = sc.nextLine();

        System.out.print("Uporabnik: ");
        String usrSheme = sc.nextLine();

        System.out.print("Geslo: ");
        String pwdSheme = sc.nextLine();

        System.out.println("Zaporedje znakov: ");
        String najdi = sc.nextLine().toLowerCase();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+imeSheme+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", usrSheme, pwdSheme);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select id, ime, priimek from tabelaKontaktov where ime like '%"+najdi+"%' or priimek like '%"+najdi+"%'");

            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void beriIzDatoteke() {

        try {
			File datoteka = new File("./src/main/resources/imenik.csv");
			BufferedReader vhod = new BufferedReader(new InputStreamReader(new FileInputStream(datoteka), "UTF-8"));
			
			String niz;
			String[] vrsticaIzDatoteke;
            vhod.readLine();
			
			while ((niz = vhod.readLine()) != null) { 
                Kontakt k = new Kontakt();
				vrsticaIzDatoteke = niz.trim().split(";");
				k.setId(Integer.parseInt(vrsticaIzDatoteke[0]));
                k.setIme(vrsticaIzDatoteke[1]);
                k.setPriimek(vrsticaIzDatoteke[2]);
                k.setNaslov(vrsticaIzDatoteke[3]);
                k.setElektronskaPosta(vrsticaIzDatoteke[4]);
                k.setTelefon(vrsticaIzDatoteke[5]);
                k.setMobilniTelefon(vrsticaIzDatoteke[6]);
                k.setOpomba(vrsticaIzDatoteke[7]);
                seznamKontaktov.add(k);
                System.out.println(k.toString());
			}

			vhod.close();
		}
		catch (Exception e)
		{
			System.out.println("Exception -> " + e.getMessage());
		}
    }
}
