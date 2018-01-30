package dao.impl;

import dao.DeveloperDAO;
import entities.Developer;
import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDAOImpl implements DeveloperDAO {
    @Override
    public void add(Developer developer) {
        try {
            ConnectionUtil.getConnection().createStatement().executeUpdate("INSERT INTO developers (name, age, sex, salary) VALUES (" +
                    " '" + developer.getName() + "', " +
                    " '" + developer.getAge() + "', " +
                    " '" + developer.getSex() + "', " +
                    " '" + developer.getSalary() + "');");
            ResultSet rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT max(developer_id) FROM developers;");
            long developerId = 0;
            if (rs.first()) {
                developerId = rs.getLong("max(developer_id)");
            }
            developer.setDeveloperId(developerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Developer getById(Long id) {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM developers WHERE developer_id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
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

    @Override
    public List<Developer> getAll() {
        List<Developer> result = new ArrayList<Developer>();
        Statement statement = null;
        String sql = "SELECT * FROM developers";
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setDeveloperId(resultSet.getLong("developer_id"));
                developer.setName(resultSet.getString("name"));
                developer.setAge(resultSet.getInt("age"));
                developer.setSex(resultSet.getString("sex"));
                developer.setSalary(resultSet.getInt("salary"));
                result.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void update(Developer developer) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE developers SET name = ?, age = ?, sex = ?, salary = ? WHERE developer_id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setInt(2, developer.getAge());
            preparedStatement.setString(3, developer.getSex());
            preparedStatement.setInt(4, developer.getSalary());
            preparedStatement.setLong(5,   developer.getDeveloperId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement.getConnection() != null) {
                    preparedStatement.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long id) {
        Statement statement = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM developers_skills WHERE developer_id = " + id);
            statement.executeUpdate("DELETE FROM developers_projects WHERE id_developer = " + id);
            statement.executeUpdate("DELETE FROM developers WHERE developer_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        DeveloperDAOImpl developerDAO = new DeveloperDAOImpl();
        developerDAO.getAll().forEach(i-> System.out.println(i));
        developerDAO.delete(33L);
        System.out.println("----------------");
        developerDAO.getAll().forEach(i-> System.out.println(i));
    }
}
