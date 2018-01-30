package starter;

import dao.impl.DeveloperDAOImpl;
import dao.impl.DeveloperToSkillDAOImpl;
import dao.impl.SkillDAOImpl;
import entities.Developer;
import entities.DeveloperToSkill;
import entities.Project;
import entities.Skill;
import lombok.Getter;
import utils.ConnectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppRunner {


    public int costOfProject(long projectId) {
        try {
            ResultSet rs = ConnectionUtil.getConnection().createStatement().executeQuery("select sum(developers.salary) from developers\n" +
                    "inner join developers_projects on developers.developer_id = developers_projects.id_developer\n" +
                    "inner join projects on developers_projects.id_project = projects.id\n" +
                    "where projects.id = '" + projectId + "'\n" +
                    "group by (id_project);");
            if (rs.first()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Developer> listOfDevelopersOfProject(long projectId) {
        List<Developer> result = new ArrayList<Developer>();
        ResultSet rs = null;
        try {
            rs = ConnectionUtil.getConnection().createStatement().executeQuery("select * from developers\n" +
                    "        inner join developers_projects on developers.developer_id = developers_projects.id_developer\n" +
                    "        inner join projects on developers_projects.id_project = projects.id\n" +
                    "        where projects.id = '" + projectId + "';");
            while (rs.next()) {
                long id = rs.getLong("developer_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                int salary = rs.getInt("salary");
                Developer developer = new Developer(id, name, age, sex, salary);
                result.add(developer);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Developer> listOfJavaDevelopers() {
        List<Developer> result = new ArrayList<Developer>();
        ResultSet rs = null;
        try {
            rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT * FROM developers\n" +
                    "  JOIN developers_skills ON developers.developer_id = developers_skills.developer_id\n" +
                    "  JOIN skills ON developers_skills.skill_id = skills.skill_id\n" +
                    "WHERE skill_name LIKE 'java';");
            while (rs.next()) {
                long id = rs.getLong("developer_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                int salary = rs.getInt("salary");
                Developer developer = new Developer(id, name, age, sex, salary);
                result.add(developer);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Developer> listOfMiddleDevelopers() {
        List<Developer> result = new ArrayList<Developer>();
        ResultSet rs = null;
        try {
            rs = ConnectionUtil.getConnection().createStatement().executeQuery("SELECT * FROM developers\n" +
                    "  JOIN developers_skills ON developers.developer_id = developers_skills.developer_id\n" +
                    "  JOIN skills ON developers_skills.skill_id = skills.skill_id\n" +
                    "WHERE level LIKE 'middle'" +
                    "GROUP BY (developers.developer_id);");
            while (rs.next()) {
                long id = rs.getLong("developer_id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String sex = rs.getString("sex");
                int salary = rs.getInt("salary");
                Developer developer = new Developer(id, name, age, sex, salary);
                result.add(developer);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Project> listOfProjectsInfo() {
        List<Project> result = new ArrayList<Project>();
        ResultSet rs = null;
        try {
            rs = ConnectionUtil.getConnection().createStatement().executeQuery("select projects.project_name, count(developer_id) from projects\n" +
                    "inner join developers_projects on projects.id = developers_projects.id_project\n" +
                    "inner join developers on developers.developer_id = developers_projects.id_project\n" +
                    "group by(projects.id);");
            while (rs.next()) {
                String projectName = rs.getString("project_name");
                int countOfDev = rs.getInt("count(developer_id)");
                Project project = new Project();
                project.setProjectName(projectName);
                project.setCountOfDevelopers(countOfDev);
                result.add(project);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AppRunner appRunner = new AppRunner();
    }
}
