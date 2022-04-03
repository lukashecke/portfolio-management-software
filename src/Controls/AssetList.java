package Controls;

import Views.BaseWindow;
import Views.AssetWindow;
import Views.PortfolioWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.SwingUtilities.getAncestorOfClass;

/**
 * Listenansicht für Assets, die sowohl den Namen, als auch die Gesamtinvestition eines Assets anzeigt.
 * @author Lukas Hecke
 */
public class AssetList<Asset> extends JList {
    public AssetList() {
        super();
        this.setCellRenderer(new AssetListCellRenderer());

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Models.Asset selectedAsset = (Models.Asset)getSelectedValue();
                    var window = new AssetWindow(selectedAsset);
                    var title = selectedAsset.getName() + " (" + selectedAsset.getShortName() + ")";
                    window.showWindow(title, 1100, 450);
                    window.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            var parentWindow = (PortfolioWindow)getAncestorOfClass(BaseWindow.class, getParent());
                            parentWindow.refresh(Models.Asset.getAssets());
                            parentWindow.revalidate();
                            parentWindow.repaint();
                        }
                    });
                }
            }
        });
    }
}
