package dao.impl;

import dao.DAOBase;
import entities.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSkillDao implements DAOBase<Skill, Long> {

    private Connection connection;
    private Statement statement;
    private PreparedStatement createSkillStatement;
    private PreparedStatement deleteSkillStatement;
    private PreparedStatement updateSkillStatement;
    private PreparedStatement selectSkillStatement;

    public JDBCSkillDao(Connection connection) {
        this.connection = connection;
        try {
            statement = connection.createStatement();
            createSkillStatement = connection.prepareStatement("INSERT INTO skills (skill_name, level) VALUES (?, ?);");
            deleteSkillStatement = connection.prepareStatement("DELETE FROM skills WHERE skill_id = ?");
            updateSkillStatement = connection.prepareStatement("UPDATE skills SET skill_name = ?, level = ? WHERE skill_id = ?");
            selectSkillStatement = connection.prepareStatement("SELECT * FROM skills WHERE skill_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Skill skill) {
        try {
            createSkillStatement.setString(1, skill.getSkillName());
            createSkillStatement.setString(2, skill.getLevel());
            ResultSet rs = statement.executeQuery("SELECT max(skill_id) FROM skills;");
            long skillId = 0;
            if (rs.first()) {
                skillId = rs.getLong(1);
            }
            skill.setSkillId(skillId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Skill getById(Long id) {
        try {
            selectSkillStatement.setLong(1, id);
            ResultSet rs = selectSkillStatement.executeQuery();
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
        }
        return null;
    }

    public List<Skill> getAll() {
        List<Skill> result = new ArrayList<Skill>();
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM skills");
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

    public void update(Skill skill) {
        try {
            updateSkillStatement.setString(1, skill.getSkillName());
            updateSkillStatement.setString(2, skill.getLevel());
            updateSkillStatement.setLong(3,   skill.getSkillId());
            updateSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            deleteSkillStatement.setLong(1, id);
            deleteSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
