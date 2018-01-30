package dao.impl;

import dao.CompanyDAO;
import entities.Company;
import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {
    @Override
    public void add(Company company) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO companies (company_name, year_of_foundation) VALUES (?, ?);";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setInt(2, company.getYearOfFoundation());
            preparedStatement.executeUpdate();
            ResultSet rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT max(id) FROM companies;");
            long companyId = 0;
            if (rs.first()) {
                companyId = rs.getLong(1);
            }
            company.setCompanyId(companyId);
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
    public Company getById(Long id) {
        Statement statement = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM companies WHERE id = " + id);
            if (rs.first()) {
                long companyId = rs.getLong("id");
                String companyName = rs.getString("company_name");
                Integer yearOfFoundation = rs.getInt("year_of_foundation");
                return new Company(companyId, companyName, yearOfFoundation);
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
    public List<Company> getAll() {
        List<Company> result = new ArrayList<Company>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM companies");
            while (rs.next()) {
                long companyId = rs.getLong("id");
                String companyName = rs.getString("company_name");
                Integer yearOfFoundation = rs.getInt("year_of_foundation");
                result.add(new Company(companyId, companyName, yearOfFoundation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void update(Company company) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE companies SET company_name = ?, year_of_foundation = ? WHERE id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setInt(2, company.getYearOfFoundation());
            preparedStatement.setLong(3, company.getCompanyId());
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
            statement.executeUpdate("UPDATE projects SET id_company = NULL WHERE id_company = " + id);
            statement.executeUpdate("DELETE FROM companies WHERE id = " + id);
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
        CompanyDAOImpl companyDAO = new CompanyDAOImpl();
        companyDAO.getAll().forEach(company -> System.out.println(company));
    }
}
