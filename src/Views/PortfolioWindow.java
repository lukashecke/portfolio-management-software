package Views;

import Models.Asset;

import javax.swing.*;
import java.awt.*;

public class PortfolioWindow extends BaseWindow {
    private Container container;
    private JList<Asset> assets = new JList<>();

    public PortfolioWindow() {
        super();

        container = getContentPane();
        container.setLayout(new GridLayout(1,1));
        container.add(assets);

        assets.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    var selectedAsset = assets.getSelectedValue();
                }
            }
        });
    }

    public JList<Asset> getAssets() {
        return assets;
    }
}
