package Controllers;

import Models.Asset;
import Models.PortfolioModel;
import Views.PortfolioWindow;

import javax.swing.*;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class PortfolioController {
    private PortfolioModel model;
    private PortfolioWindow view;

    private Collection<? extends Asset> investedAssets;

    public PortfolioController(PortfolioModel portfolioModel, PortfolioWindow portfolioWindow) {
        this.model = portfolioModel;
        this.view = portfolioWindow;

        investedAssets = model.getInvestedAssets();

        updateView();
    }

    public void updateView() {
        DefaultListModel<Asset> etfListModel = new DefaultListModel<>();
        etfListModel.addAll(investedAssets.stream().filter(t -> t.getType().getId() == 1).collect(toList()));
        view.getInvestedETFs().setModel(etfListModel);
        view.getInvestedETFs().repaint();

        DefaultListModel<Asset> cryptoListModel = new DefaultListModel<>();
        cryptoListModel.addAll(investedAssets.stream().filter(t -> t.getType().getId() == 2).collect(toList()));
        view.getInvestedCrypto().setModel(cryptoListModel);
        view.getInvestedCrypto().repaint();

        DefaultListModel<Asset> metalLstModel = new DefaultListModel<>();
        metalLstModel.addAll(investedAssets.stream().filter(t -> t.getType().getId() == 3).collect(toList()));
        view.getInvestedMetals().setModel(metalLstModel);
        view.getInvestedMetals().repaint();
    }

    public void showWindow(String title, int width, int height) {
        view.showWindow(title,width,height);
    }
}
