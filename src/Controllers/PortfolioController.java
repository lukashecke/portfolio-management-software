package Controllers;

import Models.AssetType;
import Models.PortfolioModel;
import Views.PortfolioWindow;

import javax.swing.*;

public class PortfolioController {
    private PortfolioModel model;
    private PortfolioWindow view;

    public PortfolioController(PortfolioModel portfolioModel, PortfolioWindow portfolioWindow) {
        this.model = portfolioModel;
        this.view = portfolioWindow;

        updateView();
    }

    public void updateView() {
        DefaultListModel<AssetType> listModel = new DefaultListModel<>();
        listModel.addAll(model.getInvestedAssetTypes());
        view.getInvestedAssetTypes().setModel(listModel);
        view.getInvestedAssetTypes().repaint();
    }

    public void showWindow(String title, int width, int height) {
        view.showWindow(title,width,height);
    }
}
