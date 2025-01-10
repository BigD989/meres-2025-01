/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kalapacsvetes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Sportolo> sportolok = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("kalapacsvetes.txt"))) {
            br.readLine();
            String sor;
            while ((sor = br.readLine()) != null) {
                String[] adatok = sor.split(";");
                int helyezes = Integer.parseInt(adatok[0]);
                double eredmeny = Double.parseDouble(adatok[1].replace(",", "."));
                String nev = adatok[2];
                String orszagkod = adatok[3];
                String helyszin = adatok[4];
                String datum = adatok[5];

                sportolok.add(new Sportolo(helyezes, eredmeny, nev, orszagkod, helyszin, datum));
            }
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl beolvasása során.");
        }

        int dobasokSzama = sportolok.size();
        System.out.println("A fájlban összesen " + dobasokSzama + " dobás eredménye szerepel.");
        
        double magyarokEredmeny = 0;
        int magyarokSzama = 0;

        for (Sportolo sportolo : sportolok) {
            if (sportolo.getOrszagkod().equals("HUN")) {
                magyarokEredmeny += sportolo.getEredmeny();
                magyarokSzama++;
            }
        }

        if (magyarokSzama > 0) {
            double atlag = magyarokEredmeny / magyarokSzama;
            System.out.printf("A magyar sportolók dobásainak átlageredménye: %.2f m\n", atlag);
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Kérem adjon meg egy évszámot: ");
        int ev = scanner.nextInt();

        boolean talaltDobas = false;

        for (Sportolo sportolo : sportolok) {
            if (sportolo.getDatum().startsWith(String.valueOf(ev))) {
                System.out.println(sportolo.getNev() + " (" + sportolo.getEredmeny() + " m)");
                talaltDobas = true;
            }
        }

        if (!talaltDobas) {
            System.out.println("Az adott évben nem került be egy dobás eredménye sem a legjobbak közé.");
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("magyarok.txt"))) {
            for (Sportolo sportolo : sportolok) {
                if (sportolo.getOrszagkod().equals("HUN")) {
                    writer.write(sportolo.getHelyezes() + ";" + sportolo.getEredmeny() + ";" + sportolo.getNev() + ";" + sportolo.getOrszagkod() + ";" + sportolo.getHelyszin() + ";" + sportolo.getDatum());
                    writer.newLine();
                }
            }
            System.out.println("A magyar sportolók eredményei sikeresen mentésre kerültek a magyarok.txt fájlba.");
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl mentésekor.");
        }
    }
}