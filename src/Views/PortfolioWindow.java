package Views;

import Models.AssetType;

import javax.swing.*;
import java.awt.*;

public class PortfolioWindow extends BaseWindow {
    private Container container;
    private JList<AssetType> investedAssetTypes = new JList<>();

    public PortfolioWindow() {
        super();

        investedAssetTypes.setCellRenderer(new AssetTypeCellRenderer());

        container = getContentPane();
        container.setLayout(new GridLayout(1,1));
        container.add(investedAssetTypes);

        investedAssetTypes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    var selectedAssetType = investedAssetTypes.getSelectedValue();
                }
            }
        });
    }

    public JList<AssetType> getInvestedAssetTypes() {
        return investedAssetTypes;
    }
}
