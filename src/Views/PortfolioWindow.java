package Views;

import Business.DBConnection;
import Controls.AssetList;
import Models.Asset;
import Models.Investment;
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

    JPanel content;
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    JPanel infoPanel = new JPanel();
    JLabel totalSum = new JLabel();
    JScrollPane loggingPane;
    public JTextArea loggingOutput = new JTextArea();

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
        if (DBConnection.getInstance().isAdmin()) {
            // Nur Admin soll Logging bekommen
            container.setLayout(new GridLayout(2,1));
        } else {
            container.setLayout(new GridLayout(1,1));
        }

        content = new JPanel();
        content.setLayout(new BorderLayout());

        tabbedPane.addTab("ETFs", investedETFs);
        tabbedPane.addTab("Krypto", investedCrypto);
        tabbedPane.addTab("Edelmetalle", investedMetals);

        content.add(tabbedPane, BorderLayout.CENTER);

        infoPanel.setLayout(new GridLayout(1, 2));

        JPanel links = new JPanel();
        JPanel rechts = new JPanel();

        links.setLayout(new GridLayout(2,1));
        // Platzhalter
        rechts.setLayout(new BorderLayout());

        sumTitle = new JLabel("Gesamtinvestition:");
        sumTitle.setFont(Constants.MIDDELPLAINFONT);

        totalSum.setText(Investment.getTotalInvestment());
        totalSum.setFont(Constants.MIDDLEFONT);

<<<<<<< HEAD
        // hinter der Zahl ein euro

        profitNonprofit = new JLabel("^ 50€ / 2%           ");
        profitNonprofit.setFont(Constants.LARGEFONT);

=======
>>>>>>> c2963b88df891d5aa9997a382d050f473fcd4ee2
        links.add(sumTitle, 0);
        links.add(totalSum, 1);

        infoPanel.add(links, 0);
        infoPanel.add(rechts, 1);

        content.add(infoPanel, BorderLayout.PAGE_START);

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
                    refresh(Models.Asset.getAssets());
                    revalidate();
                    repaint();
                }
            });
        });

        buttonPane.add(investmentButton, BorderLayout.EAST);
        buttonPane.setBackground(Color.LIGHT_GRAY);
        content.add(buttonPane, BorderLayout.SOUTH);
        container.add(content, 0);

        loggingOutput.setLineWrap(true);
        loggingOutput.setForeground(Color.WHITE);
        loggingOutput.setBackground(Color.BLACK);
        loggingOutput.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        loggingPane = new JScrollPane(loggingOutput);
        loggingPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if (DBConnection.getInstance().isAdmin()) {
            container.add(loggingPane, 1);
        }
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
        this.totalSum.setText("       " + Investment.getTotalInvestment() + " €");
        this.totalSum.repaint();
    }
}
