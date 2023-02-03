package penza.shandakov.baikal.POJO;

public class ForLogist{
    private String fullname;
    private String number;
    private String ves;
    private String ob;
    private String from;
    private String to;
    private String price;
    private String carDriver;

    private String state;
    private String id_personal;
    private String time;
    private String status;

    public ForLogist() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId_personal() {
        return id_personal;
    }

    public void setId_personal(String id_personal) {
        this.id_personal = id_personal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVes() {
        return ves;
    }

    public void setVes(String ves) {
        this.ves = ves;
    }

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCarDriver() {
        return carDriver;
    }

    public void setCarDriver(String carDriver) {
        this.carDriver = carDriver;
    }


    public ForLogist(String number, String ves, String ob, String from, String to) {
        this.number = number;
        this.ves = ves;
        this.ob = ob;
        this.from = from;
        this.to = to;
    }

    public ForLogist(String fullname, String number, String ves, String ob, String from, String to, String price) {
        this.fullname = fullname;
        this.number = number;
        this.ves = ves;
        this.ob = ob;
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public ForLogist(String carDriver) {
        this.carDriver = getCarDriver();
    }


}
