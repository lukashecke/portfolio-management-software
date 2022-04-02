package Models;

import Business.DBConnection;

import java.util.ArrayList;
import java.util.Collection;

public class PortfolioModel {
    private ArrayList<Asset> assetTypes;

    public PortfolioModel() {
        assetTypes = DBConnection.getInstance().GetAssets();
    }

    public Collection<? extends Asset> getAssets() {
        return assetTypes;
    }
}
