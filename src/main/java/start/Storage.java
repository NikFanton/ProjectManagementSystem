package start;

import entities.Developer;

import java.sql.*;

public class Storage {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost/itsdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;
    private Statement statement;

    public Storage() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }

        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDeveloper(Developer developer) {
        try {
            statement.executeUpdate("INSERT INTO developers (name, age, sex, salary) VALUES (" +
                    " '" + developer.getName() + "', " +
                    " '" + developer.getAge() + "', " +
                    " '" + developer.getSex() + "', " +
                    " '" + developer.getSalary() + "');");
            ResultSet rs = statement.executeQuery("SELECT max(id) FROM developers;");
            long developerId = 0;
            if (rs.first()) {
                developerId = rs.getLong(1);
            }
            developer.setDeveloperId(developerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Storage storage = new Storage();

        storage.createDeveloper(new Developer("Nadia", 23, "female", 2300));
    }
}
