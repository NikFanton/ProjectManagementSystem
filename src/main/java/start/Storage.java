package start;

import entities.Developer;
import entities.DeveloperToSkill;
import entities.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost/itsdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;
    private Statement statement;
    private PreparedStatement deleteDeveloperStatement;
    private PreparedStatement updateDeveloperStatement;
    private PreparedStatement selectDeveloperStatement;
    private PreparedStatement createSkillStatement;
    private PreparedStatement deleteSkillStatement;
    private PreparedStatement updateSkillStatement;
    private PreparedStatement selectSkillStatement;
    private PreparedStatement createDevToSkillStatement;
    private PreparedStatement deleteDevToSkillStatement;
    private PreparedStatement updateDevToSkillStatement;
    private PreparedStatement selectDevToSkillStatement;

    public Storage() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }

        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            statement = connection.createStatement();
            deleteDeveloperStatement = connection.prepareStatement("DELETE FROM developers WHERE developer_id = ?");
            updateDeveloperStatement = connection.prepareStatement("UPDATE developers SET name = ?, age = ?, sex = ?, salary = ? WHERE developer_id = ?");
            selectDeveloperStatement = connection.prepareStatement("SELECT * FROM developers WHERE developer_id = ?");

            createSkillStatement = connection.prepareStatement("INSERT INTO skills (skill_name, level) VALUES (?, ?);");
            deleteSkillStatement = connection.prepareStatement("DELETE FROM skills WHERE skill_id = ?");
            updateSkillStatement = connection.prepareStatement("UPDATE skills SET skill_name = ?, level = ? WHERE skill_id = ?");
            selectSkillStatement = connection.prepareStatement("SELECT * FROM skills WHERE skill_id = ?");

            createDevToSkillStatement = connection.prepareStatement("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?);");
            deleteDevToSkillStatement = connection.prepareStatement("DELETE FROM developers_skills WHERE developer_id = ? AND skill_id = ?");
            updateDevToSkillStatement = connection.prepareStatement("UPDATE developers_skills SET developer_id = ?, skill_id = ? WHERE developer_id = ? AND skill_id = ?");
            selectDevToSkillStatement = connection.prepareStatement("SELECT * FROM developers_skills WHERE developer_id = ? AND skill_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDeveloper(Developer developer) {
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

    public void updateDeveloper(Developer developer) {
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

    public void deleteDeveloper(long developerId) {
        try {
            deleteDeveloperStatement.setLong(1, developerId);
            deleteDeveloperStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Developer selectDeveloper(long developerId) {
        try {
            selectDeveloperStatement.setLong(1, developerId);
            ResultSet rs = selectDeveloperStatement.executeQuery();
            if (rs.first()) {
                long id = rs.getLong("developer_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                int salary = rs.getInt("salary");
                return new Developer(id, name, age, sex, salary);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Developer> listOfDevelopers() {
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
                Developer developer = new Developer(id, name, age, sex, salary);
                result.add(developer);
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

    public void createSkill(Skill skill) {
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

    public void updateSkill(Skill skill) {
        try {
            updateSkillStatement.setString(1, skill.getSkillName());
            updateSkillStatement.setString(2, skill.getLevel());
            updateSkillStatement.setLong(3,   skill.getSkillId());
            updateSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSkill(long skillId) {
        try {
            deleteSkillStatement.setLong(1, skillId);
            deleteSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Skill selectSkill (long skillId) {
        try {
            selectSkillStatement.setLong(1, skillId);
            ResultSet rs = selectDeveloperStatement.executeQuery();
            if (rs.first()) {
                long id = rs.getLong("skill_id");
                String skillName = rs.getString("skill_name");
                String level = rs.getString("level");
                return new Skill(id, skillName, level);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Skill> listOfSkills() {
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

//    ...

    public void createDeveloperToSkill(DeveloperToSkill devToSkill) {
        try {
            createDevToSkillStatement.setLong(1, devToSkill.getDeveloperId());
            createDevToSkillStatement.setLong(2, devToSkill.getSkillId());
            createDevToSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeveloperToSkill(DeveloperToSkill devToSkill) {
        try {
            deleteDevToSkillStatement.setLong(1, devToSkill.getDeveloperId());
            deleteDevToSkillStatement.setLong(2, devToSkill.getSkillId());
            deleteDevToSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDeveloperToSkill(DeveloperToSkill oldDevToSkill, DeveloperToSkill newDevToSkill) {
        try {
            updateDevToSkillStatement.setLong(1, newDevToSkill.getDeveloperId());
            updateDevToSkillStatement.setLong(2, newDevToSkill.getSkillId());
            updateDevToSkillStatement.setLong(3, oldDevToSkill.getDeveloperId());
            updateDevToSkillStatement.setLong(4, oldDevToSkill.getSkillId());
            updateDevToSkillStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DeveloperToSkill selectDeveloperToSkill (DeveloperToSkill devToSkill) {
        try {
            selectDevToSkillStatement.setLong(1, devToSkill.getDeveloperId());
            selectDevToSkillStatement.setLong(2, devToSkill.getSkillId());
            ResultSet rs = selectDeveloperStatement.executeQuery();
            return new DeveloperToSkill(rs.getLong("developer_id"), rs.getLong("skill_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DeveloperToSkill> listOfDevelopersToSkills() {
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

    public static void main(String[] args) {
        Storage storage = new Storage();
        Developer developer = new Developer("Linda", 23, "female", 2300);
//        for (Developer dev : storage.listOfDevelopers()) {
//            System.out.println(dev);
//        }

        storage.deleteDeveloperToSkill(new DeveloperToSkill(7, 5));

        for (DeveloperToSkill developerToSkill : storage.listOfDevelopersToSkills()) {
            System.out.println(developerToSkill);
        }


    }
}
