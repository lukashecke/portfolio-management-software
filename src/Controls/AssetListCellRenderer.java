package Controls;

import Models.Asset;

import javax.swing.*;
import java.awt.*;

/**
 * Zellenansicht, die sowohl den Namen, als auch die Gesamtinvestition eines Assets für die dazugehörige Listensicht anzeigt.
 * @author Lukas Hecke
 */
public class AssetListCellRenderer implements ListCellRenderer<Asset> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Asset> list, Asset value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel nameLabel = new JLabel(value.getName());
        double investedSum = Asset.getInvestedSumForAsset(value.getId());
        JLabel investmentSumLabel = new JLabel(investedSum + "€");
        panel.add(nameLabel, BorderLayout.WEST);
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
            panel.setForeground(UIManager.getColor(foreground));
            // Hintergrund
            panel.setBackground(UIManager.getColor(background));
        }
        return panel;
    }
}
