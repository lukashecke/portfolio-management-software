package Controls;

import Business.DBConnection;
import Views.AssetCellRenderer;
import Views.BaseWindow;
import Views.InvestmentsWindow;
import Views.PortfolioWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.SwingUtilities.getAncestorOfClass;

public class AssetList<Asset> extends JList {
    public AssetList() {
        super();
        this.setCellRenderer(new AssetCellRenderer());

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Models.Asset selectedAsset = (Models.Asset)getSelectedValue();
                    var window = new InvestmentsWindow(selectedAsset);
                    var title = selectedAsset.getName() + " (" + selectedAsset.getShortName() + ")";
                    window.showWindow(title, 900, 450);
                    window.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            var parentWindow = (PortfolioWindow)getAncestorOfClass(BaseWindow.class, getParent());
                            parentWindow.refresh(DBConnection.getInstance().GetInvestedAssets());
                            parentWindow.revalidate();
                            parentWindow.repaint();
                        }
                    });
                }
            }
        });
    }
}
