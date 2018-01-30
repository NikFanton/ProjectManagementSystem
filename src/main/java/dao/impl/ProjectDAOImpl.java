package dao.impl;

import dao.ProjectDAO;
import entities.Company;
import entities.Project;
import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {
    @Override
    public void add(Project project) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO projects (project_name, id_customer, id_company, cost) VALUES (?, ?, ?, ?);";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setLong(2, project.getIdCustomer());
            preparedStatement.setLong(3, project.getIdCompany());
            preparedStatement.setInt(4, project.getCost());
            preparedStatement.executeUpdate();
            ResultSet rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT max(id) FROM projects;");
            if (rs.first()) {
                project.setId(rs.getLong(1));
            }
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
    public Project getById(Long id) {
        Statement statement = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM projects WHERE id = " + id);
            if (rs.first()) {
                Long projectId = rs.getLong("id");
                String projectName = rs.getString("project_name");
                Long customerId = rs.getLong("id_customer");
                Long companyId = rs.getLong("id_company");
                Integer cost = rs.getInt("cost");
                return new Project(projectId, projectName, customerId, companyId, cost);
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
    public List<Project> getAll() {
        List<Project> result = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM projects");
            while (rs.next()) {
                Long projectId = rs.getLong("id");
                String projectName = rs.getString("project_name");
                Long customerId = rs.getLong("id_customer");
                Long companyId = rs.getLong("id_company");
                Integer cost = rs.getInt("cost");
                result.add(new Project(projectId, projectName, customerId, companyId, cost));
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
    public void update(Project project) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE projects SET project_name = ?, id_customer = ?, id_company = ?, cost = ? WHERE id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setLong(2, project.getIdCustomer());
            preparedStatement.setLong(3, project.getIdCompany());
            preparedStatement.setInt(4, project.getCost());
            preparedStatement.setLong(5, project.getId());
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
            statement.executeUpdate("DELETE FROM developers_projects WHERE id_project = " + id);
            statement.executeUpdate("DELETE FROM projects WHERE id = " + id);
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
        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        projectDAO.getAll().forEach(System.out::println);
    }
}
