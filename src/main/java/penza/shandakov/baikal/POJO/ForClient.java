package penza.shandakov.baikal.POJO;

public class ForClient {
    private String phone;
    private String password;

    private String surname;
    private String name;
    private String pat;
    private String city;
    private String numberDoc;
    private String dateDoc;
    private String day;
    private String id;
    private String fullname;

    private String role;

    public ForClient(String id, String fullname, String city, String phone) {
        this.id = id;
        this.fullname = fullname;
        this.city = city;
        this.phone = phone;
    }

    public ForClient(String id, String fullname, String day, String role, String phone, String password, String dateDoc, String numberDoc, String city, String pat) {
        this.phone = phone;
        this.password = password;
        this.pat = pat;
        this.city = city;
        this.numberDoc = numberDoc;
        this.dateDoc = dateDoc;
        this.day = day;
        this.id = id;
        this.fullname = fullname;
        this.role = role;
    }

    public ForClient() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPat() {
        return pat;
    }

    public void setPat(String pat) {
        this.pat = pat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumberDoc() {
        return numberDoc;
    }

    public void setNumberDoc(String numberDoc) {
        this.numberDoc = numberDoc;
    }

    public String getDateDoc() {
        return dateDoc;
    }

    public void setDateDoc(String dateDoc) {
        this.dateDoc = dateDoc;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

