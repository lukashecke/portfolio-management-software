package Models;

import java.util.ArrayList;

/**
 * Abbildung eines Assets.
 */
public class Asset {
    private int id;
    private String name;
    private String shortNme;
    private AssetType type;

    // TODO: Sql mit abfragen (gesonderte Abfragen)
    private ArrayList<Label> labels;
    private ArrayList<Investment> investments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortNme() {
        return shortNme;
    }

    public void setShortNme(String shortNme) {
        this.shortNme = shortNme;
    }

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(ArrayList<Investment> investments) {
        this.investments = investments;
    }
}
