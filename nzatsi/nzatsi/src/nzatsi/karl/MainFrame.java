package nzatsi.karl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel tableModel;
    private JTable table;

    public MainFrame() {
        
    	
    	setTitle("Gestion de Location de Voitures");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 700);
        setLocationRelativeTo(null);

        // Table
        tableModel = new DefaultTableModel(new String[]{"Titre", "Auteur"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // BoutonS
        JButton ajouterButton = new JButton("Ajouter");
        JButton supprimerButton = new JButton("Supprimer");
        JButton modifierButton = new JButton("Modifier");
        JButton rafraichirButton = new JButton("Rafraîchir");

        ajouterButton.addActionListener(this::ajouterVoiture);
        supprimerButton.addActionListener(this::supprimerVoiture);
        modifierButton.addActionListener(this::modifierVoiture);
        rafraichirButton.addActionListener(this::rafraichirListe);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(rafraichirButton);

        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        rafraichirListe(null);
    }

    private void ajouterVoiture(ActionEvent e) {
        String titre = JOptionPane.showInputDialog(this, "Titre de la voiture:");
        String auteur = JOptionPane.showInputDialog(this, "Auteur de la voiture:");

        if (titre != null && auteur != null) {
            try {
                VoitureManager.ajouterVoiture(new Voiture(titre, auteur));
                rafraichirListe(null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerVoiture(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String titre = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                VoitureManager.supprimerVoiture(titre);
                rafraichirListe(null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une voiture.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void modifierVoiture(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String ancienTitre = (String) tableModel.getValueAt(selectedRow, 0);
            String ancienAuteur = (String) tableModel.getValueAt(selectedRow, 1);

            String nouveauTitre = JOptionPane.showInputDialog(this, "Nouveau titre :", ancienTitre);
            String nouveauAuteur = JOptionPane.showInputDialog(this, "Nouvel auteur :", ancienAuteur);

            if (nouveauTitre != null && nouveauAuteur != null) {
                try {
                    VoitureManager.modifierVoiture(ancienTitre, new Voiture(nouveauTitre, nouveauAuteur));
                    rafraichirListe(null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une voiture à modifier.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void rafraichirListe(ActionEvent e) {
        try {
            List<Voiture> voitures = VoitureManager.lireVoitures();
            tableModel.setRowCount(0); // Clear existing rows
            for (Voiture voiture : voitures) {
                tableModel.addRow(new Object[]{voiture.getTitre(), voiture.getAuteur()});
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}