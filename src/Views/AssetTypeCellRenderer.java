package Views;

import Models.AssetType;

import javax.swing.*;
import java.awt.*;

public class AssetTypeCellRenderer implements ListCellRenderer<AssetType> {
    @Override
    public Component getListCellRendererComponent(JList<? extends AssetType> list, AssetType value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel nameLabel = new JLabel(value.getName());
        JLabel investmentSumLabel = new JLabel("5€");
        panel.add(nameLabel, BorderLayout.WEST);
        // TODO: hier total investment summe aus neuer prepared statement
        panel.add(investmentSumLabel, BorderLayout.EAST);


        // Muss aufgerufen werden ansonsten hat this.setBackground keine Wirkung
        panel.setOpaque(true);

        // Element aus der Liste ist markiert
        if(isSelected){
            // Standard Schriftfarbe für ein markiertes Listen Element zurück
            var foreground = UIManager.getColor("List.selectionForeground");
            // Standard Hintergrundfarbe für ein markiertes Listen Element zurück
            var background = UIManager.getColor("List.selectionBackground");

            panel.setForeground(foreground);
            nameLabel.setForeground(foreground);
            investmentSumLabel.setForeground(foreground);
            panel.setBackground(background);
        }
        // Element aus der Liste ist nicht markiert
        else{
            // Schriftfarbe
            var foreground = UIManager.getColor("List.foreground");
            // Hintergrundfarbe
            var background = UIManager.getColor("List.background");



            // Schriftfarbe
            panel.setForeground(UIManager.getColor("List.foreground"));
            // Hintergrund
            panel.setBackground(UIManager.getColor("List.background"));
        }
        return panel;
    }
}
