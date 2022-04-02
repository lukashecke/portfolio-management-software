package Views;

import Business.DBConnection;
import Controls.AssetList;
import Models.Asset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class PortfolioWindow extends BaseWindow {
    private Container container;

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    JPanel infoPanel = new JPanel();
    JLabel totalSum = new JLabel();

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

        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(new JLabel("Gesamtinvestition:"), BorderLayout.WEST);
        totalSum.setText(DBConnection.getInstance().getTotalInvestment());
        infoPanel.add(totalSum, BorderLayout.CENTER);
        infoPanel.add(new JLabel("+ 50 € (2)"), BorderLayout.EAST);
        container.add(infoPanel, BorderLayout.NORTH);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BorderLayout());
        JButton button = new JButton("Neues Investment");
        button.addActionListener((e) -> {
            var window = new AddInvestmentWindow();
            window.showWindow("Neues Investment",750,550);
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    refresh(DBConnection.getInstance().GetAssets());
                    revalidate();
                    repaint();
                }
            });
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

    public void refresh(Collection<? extends Asset> investedAssets) {
        // Tabs
        DefaultListModel<Asset> etfListModel = new DefaultListModel<>();
        etfListModel.addAll(investedAssets.stream().filter(t -> t.getType().getId() == 1).collect(toList()));
        this.getInvestedETFs().setModel(etfListModel);
        this.getInvestedETFs().repaint();

        DefaultListModel<Asset> cryptoListModel = new DefaultListModel<>();
        cryptoListModel.addAll(investedAssets.stream().filter(t -> t.getType().getId() == 2).collect(toList()));
        this.getInvestedCrypto().setModel(cryptoListModel);
        this.getInvestedCrypto().repaint();

        DefaultListModel<Asset> metalLstModel = new DefaultListModel<>();
        metalLstModel.addAll(investedAssets.stream().filter(t -> t.getType().getId() == 3).collect(toList()));
        this.getInvestedMetals().setModel(metalLstModel);
        this.getInvestedMetals().repaint();

        // Übersicht
        this.totalSum.setText(DBConnection.getInstance().getTotalInvestment());
        this.totalSum.repaint();
    }
}
