package Controls;

import Views.AssetCellRenderer;
import Views.InvestmentsWindow;

import javax.swing.*;

public class AssetList<Asset> extends JList {
    public AssetList() {
        super();
        this.setCellRenderer(new AssetCellRenderer());

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Models.Asset selectedAsset = (Models.Asset)getSelectedValue();
                    var window = new InvestmentsWindow();
                    var title = selectedAsset.getName() + " (" + selectedAsset.getShortName() + ")";
                    window.showWindow(title, 900, 450);
                }
            }
        });
    }
}
