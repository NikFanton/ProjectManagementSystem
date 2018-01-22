package com.example.connect;

import java.sql.*;

public class TestJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/connect?useSSL=false&useUnicode=true&" +
            "useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";

    public static void displayDevelopersResultSet(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                System.out.println("ID #" + resultSet.getInt("id")
                        + "\tName: " + resultSet.getString("name")
                        + "  \tAge: " + resultSet.getInt("age")
                        + "\tSex: " + resultSet.getString("sex"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("The driver is connected");
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            System.out.println("Connection established");

            Statement statement = connection.createStatement();
            String sqlQuery = "SELECT * FROM developers";
            ResultSet resultSet = statement.executeQuery("SELECT * FROM developers");
            displayDevelopersResultSet(resultSet);

            statement.executeUpdate("DELETE FROM developers WHERE name='Alex'");
            System.out.println("Record has been removed");
            ResultSet resultSetAfterUpdate = statement.executeQuery(sqlQuery);
            displayDevelopersResultSet(resultSetAfterUpdate);

            System.out.println("Prepared statement");
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT * FROM developers WHERE  id > ? AND sex = ?");
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "male");
            displayDevelopersResultSet(preparedStatement.executeQuery());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

