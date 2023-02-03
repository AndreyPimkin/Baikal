package penza.shandakov.baikal.POJO;

public class ForTableClient {
    private String number;
    private String status;
    private String sent;
    private String deliv;

    public ForTableClient(String number, String status, String sent, String deliv) {
        this.number = number;
        this.status = status;
        this.sent = sent;
        this.deliv = deliv;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getDeliv() {
        return deliv;
    }

    public void setDeliv(String deliv) {
        this.deliv = deliv;
    }
}
