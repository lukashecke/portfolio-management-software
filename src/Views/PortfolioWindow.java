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
    private JLabel placeHolder;

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

      //  infoPanel.add(sumTitle, BorderLayout.CENTER);
        sumTitle = new JLabel("Gesamtinvestition:");
        sumTitle.setFont(Constants.MIDDELPLAINFONT);

        totalSum.setText(DBConnection.getInstance().getTotalInvestment());
        totalSum.setFont(Constants.MIDDLEFONT);
      //  infoPanel.add(totalSum, BorderLayout.CENTER);
        profitNonprofit = new JLabel("^ 50€ / 2%");
        profitNonprofit.setFont(Constants.LARGEFONT);
      //  infoPanel.add(new JLabel(), BorderLayout.NORTH);

 /*       GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.CENTER;
        c.weightx = 1.0;

        c.gridx = 1;
        c.gridy = 1;
        //infoPanel.add(sumTitle, c); // oben links

        c.gridx = 1;
        c.gridy = 2;
        c.ipadx=1;
        //infoPanel.add(profitNonprofit, c); //unten links

        c.gridx = 2;
        c.gridy = 3;
        //infoPanel.add(totalSum, c); //oben links column 2
*/
        links.add(sumTitle, 0);
        links.add(totalSum, 1);
        rechts.add(profitNonprofit, BorderLayout.EAST);

        infoPanel.add(links, 0);
        infoPanel.add(rechts, 1);

        container.add(infoPanel, BorderLayout.PAGE_START);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BorderLayout());
        JButton button = new JButton("Neues Investment");
        button.setFont(new Font("Courier", Font.BOLD, 20));
        button.addActionListener((e) -> {
            var window = new AddInvestmentWindow();
            window.showWindow("Neues Investment",750,550);
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    refresh(DBConnection.getInstance().GetInvestedAssets());
                    revalidate();
                    repaint();
                }
            });
        });

        JPanel placePanel = new JPanel();
        placePanel.setLayout(new BorderLayout());
        placeHolder = new JLabel(" ");
        placeHolder.setFont(new Font("Courier", Font.BOLD, 120));

        placePanel.add(placeHolder, BorderLayout.PAGE_END);
        buttonPane.add(button, BorderLayout.CENTER);
        buttonPane.setBackground(Color.white);
        container.add(buttonPane, BorderLayout.LINE_END);
        container.add(placeHolder, BorderLayout.PAGE_END);
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
