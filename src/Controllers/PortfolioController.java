package Controllers;

import Models.DataTableModel;
import Models.PortfolioModel;
import Views.PortfolioWindow;

public class PortfolioController {
    private PortfolioModel model;
    private PortfolioWindow view;

    public PortfolioController(PortfolioModel portfolioModel, PortfolioWindow portfolioWindow) {
        this.model = portfolioModel;
        this.view = portfolioWindow;

        updateView();
    }

    public void updateView() {
        view.getAssets().setModel(new DataTableModel(model.getAssets()));
    }

    public void showWindow(String title, int width, int height) {
        view.showWindow(title,width,height);
    }
}
