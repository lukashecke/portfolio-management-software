package Controllers;

import Models.Asset;
import Views.AssetWindow;

public class AssetController {
    private Asset model;
    private AssetWindow view;

    public AssetController(Asset asset, AssetWindow assetWindow) {
        this.model = asset;
        this.view = assetWindow;

        updateView();
    }

    public void updateView() {
    }

    public void showWindow(String title, int width, int height) {
        view.showWindow(title,width,height);
    }
}
