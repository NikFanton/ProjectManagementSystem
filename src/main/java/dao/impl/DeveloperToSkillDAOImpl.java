package dao.impl;

import dao.DeveloperToSkillDAO;
import entities.DeveloperToSkill;
import utils.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeveloperToSkillDAOImpl implements DeveloperToSkillDAO {
    @Override
    public void add(DeveloperToSkill developerToSkill) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?);";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, developerToSkill.getDeveloperId());
            preparedStatement.setLong(2, developerToSkill.getSkillId());
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
    public DeveloperToSkill getById(DeveloperToSkill id) {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM developers_skills WHERE developer_id = ? AND skill_id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id.getDeveloperId());
            preparedStatement.setLong(2, id.getSkillId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()) {
                return new DeveloperToSkill(rs.getLong("developer_id"), rs.getLong("skill_id"));
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
    public List<DeveloperToSkill> getAll() {
        List<DeveloperToSkill> result = new ArrayList<DeveloperToSkill>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery("SELECT * FROM developers_skills");
            while (rs.next()) {
                long developerId = rs.getLong("developer_id");
                long skillId = rs.getLong("skill_id");
                DeveloperToSkill developerToSkill = new DeveloperToSkill(developerId, skillId);
                result.add(developerToSkill);
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
                rs.close();
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
    public void update(DeveloperToSkill developerToSkill) {
    }

    @Override
    public void delete(DeveloperToSkill id) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM developers_skills WHERE developer_id = ? AND skill_id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id.getDeveloperId());
            preparedStatement.setLong(2, id.getSkillId());
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
        DeveloperToSkillDAOImpl developerToSkillDAO = new DeveloperToSkillDAOImpl();
        developerToSkillDAO.getAll().forEach(devToSkill -> System.out.println(devToSkill));
    }
}
