package dao;

import entities.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDeveloperDao implements DAOBase<Developer, Long> {

    private Connection connection;
    private Statement statement;
    private PreparedStatement deleteDeveloperStatement;
    private PreparedStatement updateDeveloperStatement;
    private PreparedStatement selectDeveloperStatement;

    public JDBCDeveloperDao(Connection connection) {
        this.connection = connection;
        try {
            statement = connection.createStatement();
            deleteDeveloperStatement = connection.prepareStatement("DELETE FROM developers WHERE developer_id = ?");
            updateDeveloperStatement = connection.prepareStatement("UPDATE developers SET name = ?, age = ?, sex = ?, salary = ? WHERE developer_id = ?");
            selectDeveloperStatement = connection.prepareStatement("SELECT * FROM developers WHERE developer_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Developer developer) {
        try {
            statement.executeUpdate("INSERT INTO developers (name, age, sex, salary) VALUES (" +
                    " '" + developer.getName() + "', " +
                    " '" + developer.getAge() + "', " +
                    " '" + developer.getSex() + "', " +
                    " '" + developer.getSalary() + "');");
            ResultSet rs = statement.executeQuery("SELECT max(developer_id) FROM developers;");
            long developerId = 0;
            if (rs.first()) {
                developerId = rs.getLong("max(developer_id)");
            }
            developer.setDeveloperId(developerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Developer getById(Long id) {
        try {
            selectDeveloperStatement.setLong(1, id);
            ResultSet rs = selectDeveloperStatement.executeQuery();
            if (rs.first()) {
                long dev_id = rs.getLong("developer_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                int salary = rs.getInt("salary");
                return new Developer(dev_id, name, age, sex, salary);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Developer> getAll() {
        List<Developer> result = new ArrayList<Developer>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery("SELECT * FROM developers");
            while (resultSet.next()) {
                long id = resultSet.getLong("developer_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                int salary = resultSet.getInt("salary");
                Developer dev = new Developer(id, name, age, sex, salary);
                result.add(dev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void update(Developer developer) {
        try {
            updateDeveloperStatement.setString(1, developer.getName());
            updateDeveloperStatement.setInt(2, developer.getAge());
            updateDeveloperStatement.setString(3, developer.getSex());
            updateDeveloperStatement.setInt(4, developer.getSalary());
            updateDeveloperStatement.setLong(5,   developer.getDeveloperId());
            updateDeveloperStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            deleteDeveloperStatement.setLong(1, id);
            deleteDeveloperStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
