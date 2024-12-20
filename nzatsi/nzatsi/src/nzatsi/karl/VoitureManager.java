package nzatsi.karl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureManager {
    private static final String FILE_NAME = "voitures.txt";

    public static List<Voiture> lireVoitures() throws IOException {
        List<Voiture> voitures = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                voitures.add(Voiture.fromString(line));
            }
        }

        return voitures;
    }

    public static void ajouterVoiture(Voiture voiture) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(voiture.toString());
            writer.newLine();
        }
    }

    public static void supprimerVoiture(String titre) throws IOException {
        List<Voiture> voitures = lireVoitures();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Voiture voiture : voitures) {
                if (!voiture.getTitre().equalsIgnoreCase(titre)) {
                    writer.write(voiture.toString());
                    writer.newLine();
                }
            }
        }
    }

    public static void modifierVoiture(String ancienTitre, Voiture nouvelleVoiture) throws IOException {
        List<Voiture> voitures = lireVoitures();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Voiture voiture : voitures) {
                if (voiture.getTitre().equalsIgnoreCase(ancienTitre)) {
                    writer.write(nouvelleVoiture.toString());
                } else {
                    writer.write(voiture.toString());
                }
                writer.newLine();
            }
        }
    }
}
