package penza.shandakov.baikal.server;

import penza.shandakov.baikal.AuthorizationController;
import penza.shandakov.baikal.POJO.ForCar;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.POJO.ForLogist;

import java.sql.*;


public class DatabaseHandler {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionString = "jdbc:sqlserver://DESKTOP-2JU1ID3:1433;databaseName=cargo_driving;user=sa;password=sa;encrypt=false;";
        dbConnection = DriverManager.getConnection(connectionString);
        return dbConnection;
    }

    public ResultSet autoUser(ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT * FROM client WHERE phone =? AND password = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getPhone());
            prSt.setString(2, forClient.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet checkInfoClient(ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT * FROM client WHERE fullname = (SELECT fullname FROM client WHERE id_client = ?) ";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getId());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getInfoClient(ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT client.fullname, city.name FROM client " +
                "INNER JOIN city ON client.city = city.id_city WHERE id_client = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getId());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet autoPerson(ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT * FROM personal WHERE phone =? AND password = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getPhone());
            prSt.setString(2, forClient.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void signUpUser(ForClient forClient) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO client (phone,password) VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getPhone());
            prSt.setString(2, forClient.getPassword());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClient(ForClient forClient) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE client SET " +
                "fullname = (? + ' ' + ?), " +
                "birthday = ?, " +
                "city = ? WHERE id_client = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getSurname());
            prSt.setString(2, forClient.getName());
            prSt.setString(3, forClient.getPatronymic());
            prSt.setString(4, forClient.getBirthday());
            prSt.setString(5, forClient.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClientFull(ForClient forClient) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE client SET " +
                "fullname = (? + ' ' + ? + ' ' + ?), " +
                "birthday = ?, " +
                "city = (SELECT id_city FROM city WHERE name = ?) WHERE id_client = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getSurname());
            prSt.setString(2, forClient.getName());
            prSt.setString(3, forClient.getPatronymic());
            prSt.setString(4, forClient.getBirthday());
            prSt.setString(5, forClient.getCity());
            prSt.setString(6, forClient.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


  /*  public void addCargo(ForCargo forCargo) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO cargo(number_cargo,id_client, description, length, width, height, size, weight, " +
                "price, package, city_from, city_to) VALUES(?,?,?,?,?,?,?,?,?,?,?,?); " +
                "INSERT INTO book_delivery(number_cargo, status) VALUES (?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forCargo.getNumber());
            prSt.setString(2, forCargo.getId_client());
            prSt.setString(3, forCargo.getDesc());
            prSt.setFloat(4, Float.parseFloat(forCargo.getLen()));
            prSt.setFloat(5, Float.parseFloat(forCargo.getWid()));
            prSt.setFloat(6, Float.parseFloat(forCargo.getHei()));
            prSt.setFloat(7, Float.parseFloat(forCargo.getSize()));
            prSt.setFloat(8, Float.parseFloat(forCargo.getWei()));
            prSt.setFloat(9, Float.parseFloat(forCargo.getPrice()));
            prSt.setString(10, forCargo.getPack());
            prSt.setString(11, forCargo.getCity_from());
            prSt.setString(12, forCargo.getCity_to());
            prSt.setString(13, forCargo.getNumberTwo());
            prSt.setString(14, forCargo.getStatus());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public ResultSet getCargo(ForCargo forCargo, ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT cargo.length, cargo.width, cargo.height, cargo.size, cargo.weight, cargo.package, cargo.price, " +
                            "book_delivery.status,  book_delivery.time_delivery, " +
                            "c1.name, " +
                            "book_delivery.sent, " +
                            "c2.name, " +
                            "book_delivery.delivered " +
                "FROM cargo " +
                    "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo " +
                    "INNER JOIN rate AS r ON cargo.rate = r.id " +
                    "INNER JOIN city AS c1 ON r.city_from = c1.id_city " +
                    "INNER JOIN city AS c2 On r.city_to = c2.id_city " +
                "WHERE cargo.id_client = ? AND cargo.number_cargo = ?" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getId());
            prSt.setString(2, forCargo.getNumber());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getDriver() {
        ResultSet resSet = null;
        String select = "SELECT * FROM personal WHERE role = 'Водитель'";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getTransport() {
        ResultSet resSet = null;
        String select = "SELECT * FROM transport";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getCity() {
        ResultSet resSet = null;
        String select = "SELECT name FROM city";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }


    public ResultSet getDistanceCity(ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT distance FROM " +
                "rate AS r " +
                "INNER JOIN city AS c1 ON r.city_from = c1.id_city " +
                "INNER JOIN city AS c2 ON r.city_to = c2.id_city " +
                "WHERE c1.name = ? AND c2.name = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getCityFrom());
            prSt.setString(2, forClient.getCityTo());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }





    public ResultSet getBook() {
        ResultSet resSet = null;
        String select = "SELECT client.fullname, cargo.number_cargo, cargo.weight, cargo.size, cargo.city_from, " +
                "cargo.city_to, cargo.price from client " +
                "INNER JOIN cargo ON client.id_client = cargo.id_client " +
                "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo " +
                "WHERE book_delivery.status = 'Создан'";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void changeCargo(ForLogist forLogist) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE book_delivery SET " +
                "state=?," +
                "id_personal=?," +
                "time_delivery=?, " +
                "status=?, " +
                "sent=? " +
                "WHERE number_cargo = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forLogist.getState());
            prSt.setString(2, forLogist.getId_personal());
            prSt.setString(3, forLogist.getTime());
            prSt.setString(4, forLogist.getStatus());
            prSt.setString(5, forLogist.getFrom());
            prSt.setString(6, forLogist.getNumber());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getDelivery() {
        ResultSet resSet = null;
        String select = "SELECT cargo.number_cargo, cargo.weight, cargo.size, cargo.city_from, " +
                "cargo.city_to from cargo " +
                "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo " +
                "WHERE book_delivery.status = 'Сформирован' AND  id_personal = " + AuthorizationController.idPerson;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getClient() {
        ResultSet resSet = null;
        String select = "SELECT id_client, fullname, city, phone FROM client";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getCargoTwo() {
        ResultSet resSet = null;
        String select = "SELECT client.id_client, cargo.number_cargo, cargo.city_from, cargo.city_to, book_delivery.sent FROM client " +
                "INNER JOIN cargo ON client.id_client = cargo.id_client " +
                "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getCar() {
        ResultSet resSet = null;
        String select = "SELECT * FROM transport";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getPerson() {
        ResultSet resSet = null;
        String select = "SELECT * FROM personal";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }


    public void addCar(ForCar forCar) throws ClassNotFoundException {
        String insert = "INSERT INTO transport VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forCar.getNumber());
            prSt.setString(2, forCar.getModel());
            prSt.setString(3, forCar.getStatus());
            prSt.setString(4, forCar.getVin());
            prSt.setString(5, forCar.getLoad());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPerson(ForClient forClient) throws ClassNotFoundException {
        String insert = "INSERT INTO personal VALUES(?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getId());
            prSt.setString(2, forClient.getFullName());
            prSt.setString(3, forClient.getBirthday());
            prSt.setString(4, forClient.getRole());
            prSt.setString(5, forClient.getPhone());
            prSt.setString(6, forClient.getPassword());
            prSt.setString(7, forClient.getDateDoc());
            prSt.setString(8, forClient.getNumberDoc());
            prSt.setString(9, forClient.getCity());
            prSt.setString(10, forClient.getPatronymic());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(ForCar forCar) throws ClassNotFoundException {
        String insert = "DELETE FROM transport WHERE state = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forCar.getNumber());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(ForClient forClient) throws ClassNotFoundException {
        String insert = "DELETE FROM  personal WHERE id_personal = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet checkPhoneClient(ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT * FROM client WHERE phone =  ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forClient.getPhone());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void changePassword(ForClient forClient) throws ClassNotFoundException {
        String insert = "UPDATE client SET password = ? WHERE phone = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getPassword());
            prSt.setString(2, forClient.getPhone());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePasswordTwo(ForClient forClient) throws ClassNotFoundException {
        String insert = "UPDATE client SET password = ? WHERE id_client = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forClient.getPassword());
            prSt.setString(2, forClient.getId());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}











