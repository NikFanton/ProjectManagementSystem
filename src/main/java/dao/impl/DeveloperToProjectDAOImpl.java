package dao.impl;

import dao.DeveloperToProjectDAO;
import entities.Developer;
import entities.DeveloperToProject;
import entities.DeveloperToSkill;
import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeveloperToProjectDAOImpl implements DeveloperToProjectDAO {
    @Override
    public void add(DeveloperToProject developerToProject) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO developers_projects (id_developer, id_project) VALUES (?, ?);";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, developerToProject.getDeveloperId());
            preparedStatement.setLong(2, developerToProject.getProjectId());
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
    public DeveloperToProject getById(DeveloperToProject id) {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM developers_projects WHERE id_developer = ? AND id_project = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id.getDeveloperId());
            preparedStatement.setLong(2, id.getProjectId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()) {
                DeveloperToProject developerToProject = new DeveloperToProject();
                developerToProject.setDeveloperId(rs.getLong("id_developer"));
                developerToProject.setProjectId(rs.getLong("id_project"));
                return developerToProject;
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
        return null;
    }

    @Override
    public List<DeveloperToProject> getAll() {
        List<DeveloperToProject> result = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM developers_projects");
            while (rs.next()) {
                DeveloperToProject developerToProject = new DeveloperToProject();
                developerToProject.setDeveloperId(rs.getLong("id_developer"));
                developerToProject.setProjectId(rs.getLong("id_project"));
                result.add(developerToProject);
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
                if (rs != null) {
                    rs.close();
                }
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
    public void update(DeveloperToProject developerToProject) {

    }

    @Override
    public void delete(DeveloperToProject id) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM developers_projects WHERE id_developer = ? AND id_project = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id.getDeveloperId());
            preparedStatement.setLong(2, id.getProjectId());
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

    public static void main(String[] args) {
        DeveloperToProjectDAOImpl devToProjectDAO = new DeveloperToProjectDAOImpl();
        devToProjectDAO.delete(new DeveloperToProject(3, 8));
        devToProjectDAO.getAll().forEach(System.out::println);
    }
}
