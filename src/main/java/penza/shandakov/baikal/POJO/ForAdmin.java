package penza.shandakov.baikal.POJO;

public class ForAdmin {
    private String IDPacking;
    private String namePacking;
    private String pricePacking;

    private String idRate;
    private String fromCityRate;
    private String toCityRate;
    private String distanceRate;

    public ForAdmin(String IDPacking, String namePacking, String pricePacking) {
        this.IDPacking = IDPacking;
        this.namePacking = namePacking;
        this.pricePacking = pricePacking;
    }

    public ForAdmin(String idRate, String fromCityRate, String toCityRate, String distanceRate) {
        this.idRate = idRate;
        this.fromCityRate = fromCityRate;
        this.toCityRate = toCityRate;
        this.distanceRate = distanceRate;
    }

    public ForAdmin() {

    }

    public String getIDPacking() {
        return IDPacking;
    }

    public void setIDPacking(String IDPacking) {
        this.IDPacking = IDPacking;
    }

    public String getNamePacking() {
        return namePacking;
    }

    public void setNamePacking(String namePacking) {
        this.namePacking = namePacking;
    }

    public String getPricePacking() {
        return pricePacking;
    }

    public void setPricePacking(String pricePacking) {
        this.pricePacking = pricePacking;
    }

    public String getIdRate() {
        return idRate;
    }

    public void setIdRate(String idRate) {
        this.idRate = idRate;
    }

    public String getFromCityRate() {
        return fromCityRate;
    }

    public void setFromCityRate(String fromCityRate) {
        this.fromCityRate = fromCityRate;
    }

    public String getToCityRate() {
        return toCityRate;
    }

    public void setToCityRate(String toCityRate) {
        this.toCityRate = toCityRate;
    }

    public String getDistanceRate() {
        return distanceRate;
    }

    public void setDistanceRate(String distanceRate) {
        this.distanceRate = distanceRate;
    }
}
