package Models;

import Business.DBConnection;

import java.util.ArrayList;
import java.util.Collection;

public class PortfolioModel {
    private ArrayList<Asset> investedAssetTypes;

    public PortfolioModel() {
        investedAssetTypes = DBConnection.getInstance().GetInvestedAssets();
    }

    public Collection<? extends Asset> getInvestedAssets() {
        return investedAssetTypes;
    }
}
