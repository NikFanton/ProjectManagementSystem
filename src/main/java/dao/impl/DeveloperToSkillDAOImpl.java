package dao.impl;

import dao.DAOBase;
import entities.DeveloperToSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDeveloperToSkillDao implements DAOBase<DeveloperToSkill, DeveloperToSkill> {

    private Connection connection;
    private Statement statement;
    private PreparedStatement createDevToSkillStatement;
    private PreparedStatement deleteDevToSkillStatement;
    private PreparedStatement updateDevToSkillStatement;
    private PreparedStatement selectDevToSkillStatement;

    public JDBCDeveloperToSkillDao(Connection connection) {
        this.connection = connection;
        try {
            statement = connection.createStatement();
            createDevToSkillStatement = connection.prepareStatement("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?);");
            deleteDevToSkillStatement = connection.prepareStatement("DELETE FROM developers_skills WHERE developer_id = ? AND skill_id = ?");
            updateDevToSkillStatement = connection.prepareStatement("UPDATE developers_skills SET developer_id = ?, skill_id = ? WHERE developer_id = ? AND skill_id = ?");
            selectDevToSkillStatement = connection.prepareStatement("SELECT * FROM developers_skills WHERE developer_id = ? AND skill_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(DeveloperToSkill developerToSkill) {
        try {
            createDevToSkillStatement.setLong(1, developerToSkill.getDeveloperId());
            createDevToSkillStatement.setLong(2, developerToSkill.getSkillId());
            createDevToSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DeveloperToSkill getById(DeveloperToSkill id) {
        try {
            selectDevToSkillStatement.setLong(1, id.getDeveloperId());
            selectDevToSkillStatement.setLong(2, id.getSkillId());
            ResultSet rs = selectDevToSkillStatement.executeQuery();
            if (rs.first()) {
                return new DeveloperToSkill(rs.getLong("developer_id"), rs.getLong("skill_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DeveloperToSkill> getAll() {
        List<DeveloperToSkill> result = new ArrayList<DeveloperToSkill>();
        ResultSet rs = null;
        try {
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
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void update(DeveloperToSkill developerToSkill) {
    }

    public void delete(DeveloperToSkill id) {
        try {
            deleteDevToSkillStatement.setLong(1, id.getDeveloperId());
            deleteDevToSkillStatement.setLong(2, id.getSkillId());
            deleteDevToSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
