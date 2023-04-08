package penza.shandakov.baikal.server;

import penza.shandakov.baikal.AuthorizationController;
import penza.shandakov.baikal.POJO.ForCar;
import penza.shandakov.baikal.POJO.ForCargo;
import penza.shandakov.baikal.POJO.ForClient;
import penza.shandakov.baikal.POJO.ForLogistician;

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


    public void addCargo(ForCargo forCargo) throws ClassNotFoundException {
        String insert = "INSERT INTO cargo(number_cargo, id_client,description, length, width, height, size, weight, price, id_package, rate) " +
                "VALUES(?,?,?,?,?,?,?,?,?," +
                "(SELECT id_package FROM package WHERE name = ?)," +
                "(SELECT id FROM rate " +
                "INNER JOIN city AS c1 ON rate.city_from = c1.id_city " +
                "INNER JOIN city AS c2 On rate.city_to = c2.id_city " +
                "WHERE c1.name = ? AND c2.name = ?)); " +
                "INSERT INTO book_delivery(number_cargo, status) VALUES (?, 'В заявку')";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forCargo.getNumber());
            prSt.setString(2, forCargo.getIDClient());
            prSt.setString(3, forCargo.getDescription());
            prSt.setFloat(4, Float.parseFloat(forCargo.getLength()));
            prSt.setFloat(5, Float.parseFloat( forCargo.getWidth()));
            prSt.setFloat(6, Float.parseFloat( forCargo.getHeight()));
            prSt.setFloat(7, Float.parseFloat(forCargo.getSize()));
            prSt.setFloat(8, Float.parseFloat(forCargo.getWeight()));
            prSt.setFloat(9, Float.parseFloat(forCargo.getPrice()));
            prSt.setString(10, forCargo.getPacking());
            prSt.setString(11, forCargo.getCityFrom());
            prSt.setString(12, forCargo.getCityTo());
            prSt.setString(13, forCargo.getNumberTwo());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCargo(ForCargo forCargo, ForClient forClient) {
        ResultSet resSet = null;
        String select = "SELECT cargo.length, cargo.width, cargo.height, cargo.size, cargo.weight, package.name, cargo.price, " +
                            "book_delivery.status,  book_delivery.time_delivery, " +
                            "c1.name, " +
                            "book_delivery.sent, " +
                            "c2.name, " +
                            "book_delivery.delivered " +
                "FROM cargo " +
                    "INNER JOIN package ON cargo.id_package = package.id_package " +
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

    public ResultSet getCargoForLogistician() {
        ResultSet resSet = null;
        String select = "SELECT cargo.id_client, cargo.number_cargo, cargo.length ,cargo.width, cargo.height, cargo.size, cargo.weight, " +
                "c1.name, c2.name, cargo.description " +
                "FROM cargo " +
                "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo " +
                "INNER JOIN rate AS r ON cargo.rate = r.id " +
                "INNER JOIN city AS c1 ON r.city_from = c1.id_city " +
                "INNER JOIN city AS c2 On r.city_to = c2.id_city " +
                "WHERE book_delivery.status = 'В заявку' OR book_delivery.status = 'Отклонено водителем'" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getClientForLogistician() {
        ResultSet resSet = null;
        String select = "SELECT client.id_client, client.fullname, city.name, client.phone  from client " +
                        "INNER JOIN city ON client.city = city.id_city" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getCargoForDriver() {
        ResultSet resSet = null;
        String select = "SELECT cargo.number_cargo, book_delivery.sent, transport.model, cargo.length ,cargo.width, cargo.height, cargo.size, cargo.weight, " +
                "c1.name, c2.name, cargo.description " +
                "FROM cargo " +
                "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo " +
                "INNER JOIN transport ON book_delivery.transport = transport.state " +
                "INNER JOIN rate AS r ON cargo.rate = r.id " +
                "INNER JOIN city AS c1 ON r.city_from = c1.id_city " +
                "INNER JOIN city AS c2 On r.city_to = c2.id_city " +
                "WHERE book_delivery.status = 'Подтвержден'" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);

            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getCargoAcceptedForDriver() {
        ResultSet resSet = null;
        String select = "SELECT cargo.number_cargo, transport.model,  " +
                "c1.name, c2.name, book_delivery.status, book_delivery.sent " +
                "FROM cargo " +
                "INNER JOIN book_delivery ON cargo.number_cargo = book_delivery.number_cargo " +
                "INNER JOIN transport ON book_delivery.transport = transport.state " +
                "INNER JOIN rate AS r ON cargo.rate = r.id " +
                "INNER JOIN city AS c1 ON r.city_from = c1.id_city " +
                "INNER JOIN city AS c2 On r.city_to = c2.id_city " +
                "WHERE book_delivery.status = 'Принято в доставку'" ;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);

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

    public ResultSet getPackage() {
        ResultSet resSet = null;
        String select = "SELECT name FROM package";
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

    public ResultSet getPricePacking(ForCargo forCargo) {
        ResultSet resSet = null;
        String select = "SELECT price FROM package WHERE name = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, forCargo.getPacking());

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

    public void changeCargo(ForLogistician forLogistician) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE book_delivery SET " +
                "transport=?," +
                "id_personal=?," +
                "time_delivery=?, " +
                "status=?, " +
                "sent=? " +
                "WHERE number_cargo = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forLogistician.getCar());
            prSt.setInt(2, forLogistician.getIdPersonal());
            prSt.setString(3, forLogistician.getTimeDelivery());
            prSt.setString(4, forLogistician.getStatus());
            prSt.setString(5, forLogistician.getSent());
            prSt.setString(6, forLogistician.getNumberCargo());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeCargoForDriver(ForLogistician forLogistician) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE book_delivery SET " +
                "status=? WHERE number_cargo = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, forLogistician.getStatus());
            prSt.setString(2, forLogistician.getNumberCargo());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void changeCargoTwo(ForLogistician forLogistician) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE book_delivery SET " +
                "id_personal=?," +
                "status=? " +
                "WHERE number_cargo = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, forLogistician.getIdPersonal());
            prSt.setString(2, forLogistician.getStatus());
            prSt.setString(3, forLogistician.getNumberCargo());
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

    public ResultSet getCar(ForCar forCar) {
        ResultSet resSet = null;
        String select = "SELECT * FROM transport WHERE load_capacity > ? AND size > ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setFloat(1, forCar.getLoad_capacity());
            prSt.setFloat(2, forCar.getSize());
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


/*
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
*/

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

/*
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
*/

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











