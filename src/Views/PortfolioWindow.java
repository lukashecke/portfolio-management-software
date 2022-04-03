package Views;

import Business.DBConnection;
import Controls.AssetList;
import Models.Asset;
import Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class PortfolioWindow extends BaseWindow {
    private Container container;

    private JLabel sumTitle;
    private JLabel profitNonprofit;

    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    JPanel infoPanel = new JPanel();
    JLabel totalSum = new JLabel();

    private AssetList investedETFs = new AssetList();
    private AssetList investedCrypto = new AssetList();
    private AssetList investedMetals = new AssetList();

    /**
     * Erzeugt die Komponenten für das InvestmentWindow.
     * @author  Lukas Hecke
     * @author  Namandeep Singh
     */

    public PortfolioWindow() {
        super();

        container = getContentPane();
        container.setLayout(new BorderLayout());

        tabbedPane.addTab("ETFs", investedETFs);
        tabbedPane.addTab("Krypto", investedCrypto);
        tabbedPane.addTab("Edelmetalle", investedMetals);

        container.add(tabbedPane, BorderLayout.CENTER);

        infoPanel.setLayout(new GridLayout(1, 2));

        JPanel links = new JPanel();
        JPanel rechts = new JPanel();

        links.setLayout(new GridLayout(2,1));
        rechts.setLayout(new BorderLayout());

        sumTitle = new JLabel("Gesamtinvestition:");
        sumTitle.setFont(Constants.MIDDELPLAINFONT);

        totalSum.setText(DBConnection.getInstance().getTotalInvestment());
        totalSum.setFont(Constants.MIDDLEFONT);

        // hinter der Zahl ein euro

        profitNonprofit = new JLabel("^ 50€ / 2%           ");
        profitNonprofit.setFont(Constants.LARGEFONT);

        links.add(sumTitle, 0);
        links.add(totalSum, 1);
        rechts.add(profitNonprofit, BorderLayout.EAST);

        infoPanel.add(links, 0);
        infoPanel.add(rechts, 1);

        container.add(infoPanel, BorderLayout.PAGE_START);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BorderLayout());
        JButton investmentButton = new JButton("+ Neues Investment"); //Hier wird ein Button erzeugt für die neue Investitionen
        investmentButton.setFont(Constants.MIDDLEFONT);
        investmentButton.addActionListener((e) -> {
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

        buttonPane.add(investmentButton, BorderLayout.EAST);
        buttonPane.setBackground(Color.LIGHT_GRAY);
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
        this.totalSum.setText("       " + DBConnection.getInstance().getTotalInvestment() + " €");
        this.totalSum.repaint();
    }
}
