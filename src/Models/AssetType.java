package Models;

/**
Abbildung eines Asset Typs (z.B. Einzelaktien, Krypto, Schwermetalle).
 */
public class AssetType {
    private int id;
    private String name;
    private String shortName;
    private Boolean needsIncomeTax;
    private String info;
    private String description;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getNeedsIncomeTax() {
        return needsIncomeTax;
    }

    public void setNeedsIncomeTax(Boolean needsIncomeTax) {
        this.needsIncomeTax = needsIncomeTax;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}