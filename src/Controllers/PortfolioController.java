package Controllers;

import Models.Asset;
import Views.PortfolioWindow;

import java.util.ArrayList;

/**
 * Controller für das ProtfolioWindow.
 */
public class PortfolioController {
    // View
    private PortfolioWindow view;
    // Model
    private ArrayList<Asset> assetTypes;

    public PortfolioController(PortfolioWindow portfolioWindow) {
        this.view = portfolioWindow;
        assetTypes = Asset.getAssets();
        this.view.refresh(assetTypes);
    }

    /**
     * Zeigt Fenster an.
     * @param title Titel des Fensters
     * @param width Breite des Fensters
     * @param height Höhe des Fensters
     */
    public void showWindow(String title, int width, int height) {
        view.showWindow(title,width,height);
    }
}
