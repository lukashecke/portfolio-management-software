package Controllers;

import Models.PortfolioModel;
import Views.PortfolioWindow;

public class PortfolioController {
    private PortfolioModel model;
    private PortfolioWindow view;

    public PortfolioController(PortfolioModel portfolioModel, PortfolioWindow portfolioWindow) {
        this.model = portfolioModel;
        this.view = portfolioWindow;

        this.view.refresh(model.getAssets());
    }

    public void showWindow(String title, int width, int height) {
        view.showWindow(title,width,height);
    }
}
