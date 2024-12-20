package nzatsi.karl;

public class Voiture {
    private String titre;
    private String auteur;

    public Voiture(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    @Override
    public String toString() {
        return titre + ";" + auteur;
    }

    public static Voiture fromString(String line) {
        String[] parts = line.split(";");
        return new Voiture(parts[0], parts[1]);
    }
}