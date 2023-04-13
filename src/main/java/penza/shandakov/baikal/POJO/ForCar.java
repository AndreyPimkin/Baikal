package penza.shandakov.baikal.POJO;

public class ForCar {

    private String state;
    private String model;
    private String status;
    private Float load_capacity;
    private Float size;

    public ForCar() {

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public Float getLoad_capacity() {
        return load_capacity;
    }

    public void setLoad_capacity(Float load_capacity) {
        this.load_capacity = load_capacity;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public ForCar(String state, String model, String status, Float load_capacity, Float size) {
        this.state = state;
        this.model = model;
        this.status = status;
        this.load_capacity = load_capacity;
        this.size = size;
    }
}
