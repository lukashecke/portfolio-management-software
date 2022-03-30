package Views;

import Controls.AssetList;

import javax.swing.*;
import java.awt.*;

public class PortfolioWindow extends BaseWindow {
    private Container container;

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);

    private AssetList investedETFs = new AssetList();
    private AssetList investedCrypto = new AssetList();
    private AssetList investedMetals = new AssetList();

    public PortfolioWindow() {
        super();

        container = getContentPane();
        container.setLayout(new BorderLayout());

        tabbedPane.addTab("ETFs", investedETFs);
        tabbedPane.addTab("Krypto", investedCrypto);
        tabbedPane.addTab("Edelmetalle", investedMetals);

        container.add(tabbedPane, BorderLayout.CENTER);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BorderLayout());
        JButton button = new JButton("Neues Investment");
        button.addActionListener((e) -> {
            var window = new AddInvestmentWindow();
            window.showWindow("Neues Investment",750,550);
        });
        buttonPane.add(button, BorderLayout.EAST);
        buttonPane.setBackground(Color.white);
        container.add(buttonPane, BorderLayout.SOUTH);
    }

    public AssetList getInvestedETFs() {
        return investedETFs;
    }

    public AssetList getInvestedCrypto() {
        return investedCrypto;
    }

    public AssetList getInvestedMetals() {
        return investedMetals;
    }
}
