package penza.shandakov.baikal.POJO;

public class ForCar {

    private String number;
    private String model;
    private String status;
    private String vin;
    private String load;

    public ForCar(String number, String model, String status, String vin, String load) {
        this.number = number;
        this.model = model;
        this.status = status;
        this.vin = vin;
        this.load = load;
    }

    public ForCar() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }
}
