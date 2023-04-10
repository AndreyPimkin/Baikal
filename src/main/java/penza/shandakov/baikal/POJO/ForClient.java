package penza.shandakov.baikal.POJO;

public class ForClient {
    private String phone;
    private String password;
    private String surname;
    private String name;
    private String patronymic;
    private String city;
    private String numberDoc;
    private String dateDoc;
    private String birthday;
    private String id;
    private String fullName;

    private String cityFrom;
    private String cityTo;



    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    private String role;

    public ForClient(String id, String fullName, String city, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.city = city;
        this.phone = phone;
    }

    public ForClient(String id, String fullName, String birthday, String role, String phone, String password, String dateDoc, String numberDoc, String city, String patronymic) {
        this.phone = phone;
        this.password = password;
        this.patronymic = patronymic;
        this.city = city;
        this.numberDoc = numberDoc;
        this.dateDoc = dateDoc;
        this.birthday = birthday;
        this.id = id;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public ForClient(String id, String fullName, String birthday, String phone, String password, String numberDoc, String dateDoc, String city) {
        this.phone = phone;
        this.password = password;
        this.city = city;
        this.numberDoc = numberDoc;
        this.dateDoc = dateDoc;
        this.birthday = birthday;
        this.id = id;
        this.fullName = fullName;
    }
}

