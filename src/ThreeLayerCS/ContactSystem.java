package ThreeLayerCS;

import java.sql.*;

public class ContactSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/info";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public String viewContacts() {
        StringBuilder contacts = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM contacts";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                contacts.append("姓名：").append(name).append("，电话：").append(phone)
                        .append("，地址：").append(address).append("\n");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts.toString();
    }

    public void addContact(String name, String phone, String address) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO contacts (name, phone, address) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(String name, String field, String value) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE contacts SET " + field + " = ? WHERE name = ?")) {
            statement.setString(1, value);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(String name) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts WHERE name = ?")) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
