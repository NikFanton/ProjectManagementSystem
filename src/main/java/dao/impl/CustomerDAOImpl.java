package dao.impl;

import dao.CustomerDAO;
import entities.Customer;
import entities.Skill;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void add(Customer customer) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO customers (customer_name) VALUES (?);";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.executeUpdate();
            ResultSet rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT max(id) FROM customers;");
            long customerId = 0;
            if (rs.first()) {
                customerId = rs.getLong(1);
            }
            customer.setCustomerId(customerId);
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
    public Customer getById(Long id) {
        Statement statement = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers WHERE id = " + id);
            if (rs.first()) {
                long customerId = rs.getLong("id");
                String customerName = rs.getString("customer_name");
                return new Customer(customerId, customerName);
            } else {
                return null;
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
        return null;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> result = new ArrayList<Customer>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                long id = rs.getLong("id");
                String customerName = rs.getString("customer_name");
                Customer customer = new Customer(id, customerName);
                result.add(customer);
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
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void update(Customer customer) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE customers SET customer_name = ? WHERE id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setLong(2, customer.getCustomerId());
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
            statement.executeUpdate("DELETE FROM customers WHERE id = " + id);
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
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        customerDAO.getAll().forEach(cust-> System.out.println(cust));
    }
}
