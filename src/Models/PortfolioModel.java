package Models;

import Business.DBConnection;

import java.util.ArrayList;
import java.util.Collection;

public class PortfolioModel {
    private ArrayList<AssetType> investedAssetTypes;

    public PortfolioModel() {
        investedAssetTypes = DBConnection.getInstance().GetInvestedAssetTypes();
    }

    public Collection<? extends AssetType> getInvestedAssetTypes() {
        return investedAssetTypes;
    }
}
