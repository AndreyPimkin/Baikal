package penza.shandakov.baikal.POJO;

public class ForCargo {
    private String number;
    private String numberTwo;
    private String id_client;
    private String desc;
    private String len;
    private String wid;
    private String hei;
    private String size;
    private String wei;
    private String price;
    private String pack;
    private String city_from;
    private String city_to;
    private String status;

    public ForCargo(String number, String id_client, String city_from, String city_to, String status) {
        this.number = number;
        this.id_client = id_client;
        this.city_from = city_from;
        this.city_to = city_to;
        this.status = status;
    }

    public ForCargo() {

    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getHei() {
        return hei;
    }

    public void setHei(String hei) {
        this.hei = hei;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWei() {
        return wei;
    }

    public void setWei(String wei) {
        this.wei = wei;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getCity_from() {
        return city_from;
    }

    public void setCity_from(String city_from) {
        this.city_from = city_from;
    }

    public String getCity_to() {
        return city_to;
    }

    public void setCity_to(String city_to) {
        this.city_to = city_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
