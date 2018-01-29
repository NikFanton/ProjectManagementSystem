package dao.impl;

import dao.SkillDAO;
import entities.DeveloperToSkill;
import entities.Skill;
import utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDAOImpl implements SkillDAO {
    @Override
    public void add(Skill skill) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO skills (skill_name, level) VALUES (?, ?);";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, skill.getSkillName());
            preparedStatement.setString(2, skill.getLevel());
            ResultSet rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT max(skill_id) FROM skills;");
            long skillId = 0;
            if (rs.first()) {
                skillId = rs.getLong(1);
            }
            skill.setSkillId(skillId);
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
    public Skill getById(Long id) {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT * FROM skills WHERE skill_id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()) {
                long skillId = rs.getLong("skill_id");
                String skillName = rs.getString("skill_name");
                String level = rs.getString("level");
                return new Skill(skillId, skillName, level);
            } else {
                return null;
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
    public List<Skill> getAll() {
        List<Skill> result = new ArrayList<Skill>();
        ResultSet rs = null;
        try {
            rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT * FROM skills");
            while (rs.next()) {
                long id = rs.getLong("skill_id");
                String skillName = rs.getString("skill_name");
                String level = rs.getString("level");
                Skill skill = new Skill(id, skillName, level);
                result.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void update(Skill skill) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE skills SET skill_name = ?, level = ? WHERE skill_id = ?";
        try {
            preparedStatement = ConnectionUtil.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, skill.getSkillName());
            preparedStatement.setString(2, skill.getLevel());
            preparedStatement.setLong(3,   skill.getSkillId());
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
            statement.executeUpdate("DELETE FROM developers_skills WHERE skill_id = " + id);
            statement.executeUpdate("DELETE FROM skills WHERE skill_id = " + id);
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
        SkillDAOImpl skillDAO = new SkillDAOImpl();
        skillDAO.getAll().forEach((skill -> System.out.println(skill)));
        skillDAO.delete(9L);
        System.out.println("----------------");
        skillDAO.getAll().forEach((skill -> System.out.println(skill)));
    }
}
