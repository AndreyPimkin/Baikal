package penza.shandakov.baikal.POJO;

public class ForLogistician {
    private String idClient;
    private String numberCargo;
    private String proportions;
    private Float size;
    private Float weight;
    private String fromCity;
    private String toCity;
    private String description;
    private String car;
    private String driver;
    private int idPersonal;
    private String timeDelivery;

    private String sent;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(String timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public ForLogistician() {

    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNumberCargo() {
        return numberCargo;
    }

    public void setNumberCargo(String numberCargo) {
        this.numberCargo = numberCargo;
    }

    public String getProportions() {
        return proportions;
    }

    public void setProportions(String proportions) {
        this.proportions = proportions;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ForLogistician(String idClient, String numberCargo, String proportions, Float size, Float weight, String fromCity, String toCity, String description) {
        this.idClient = idClient;
        this.numberCargo = numberCargo;
        this.proportions = proportions;
        this.size = size;
        this.weight = weight;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.description = description;
    }


    public ForLogistician(String numberCargo, String sent, String car, String proportions, Float size, Float weight, String fromCity, String toCity, String description) {
        this.numberCargo = numberCargo;
        this.proportions = proportions;
        this.size = size;
        this.weight = weight;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.description = description;
        this.car = car;
        this.sent = sent;
    }

    public ForLogistician(String numberCargo, String car, String fromCity, String toCity, String status, String sent) {
        this.numberCargo = numberCargo;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.car = car;
        this.status = status;
        this.sent = sent;

    }
}
