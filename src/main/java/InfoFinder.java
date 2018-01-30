import entities.Developer;
import entities.Project;
import utils.ConnectionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InfoFinder {
    public static int costOfProject(long projectId) {
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT sum(developers.salary) FROM developers\n" +
                "INNER JOIN developers_projects ON developers.developer_id = developers_projects.id_developer\n" +
                "INNER JOIN projects ON developers_projects.id_project = projects.id\n" +
                "WHERE projects.id = " + projectId + "\n" +
                "GROUP BY (id_project);";
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery(sql);
            if (rs.first()) {
                return rs.getInt(1);
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
        return 0;
    }

    public static List<Developer> listOfDevelopersOfProject(long projectId) {
        List<Developer> result = new ArrayList<Developer>();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM developers\n" +
                    "INNER JOIN developers_projects ON developers.developer_id = developers_projects.id_developer\n" +
                    "INNER JOIN projects ON developers_projects.id_project = projects.id\n" +
                    "WHERE projects.id = " + projectId + ";";
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery(sql);
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
        return null;
    }

    public static List<Developer> listOfJavaDevelopers() {
        List<Developer> result = new ArrayList<Developer>();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM developers\n" +
                "  JOIN developers_skills ON developers.developer_id = developers_skills.developer_id\n" +
                "  JOIN skills ON developers_skills.skill_id = skills.skill_id\n" +
                "WHERE skill_name LIKE 'java';";
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery(sql);
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
        } finally {
            try {
                if (statement != null) {
                    statement.getConnection().close();
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

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Developer> listOfMiddleDevelopers() {
        List<Developer> result = new ArrayList<Developer>();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM developers\n" +
                "  JOIN developers_skills ON developers.developer_id = developers_skills.developer_id\n" +
                "  JOIN skills ON developers_skills.skill_id = skills.skill_id\n" +
                "WHERE level LIKE 'middle'" +
                "GROUP BY (developers.developer_id);";
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery(sql);
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
        } finally {
            try {
                if (statement != null) {
                    statement.getConnection().close();
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

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Project> listOfProjectsInfo() {
        List<Project> result = new ArrayList<Project>();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "SELECT projects.project_name, count(developer_id) FROM projects\n" +
                "INNER JOIN developers_projects ON projects.id = developers_projects.id_project\n" +
                "INNER JOIN developers ON developers.developer_id = developers_projects.id_project\n" +
                "GROUP BY (projects.id);";
        try {
            statement = ConnectionUtil.getConnection().createStatement();
            rs = statement.executeQuery(sql);
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
        } finally {
            try {
                if (statement != null) {
                    statement.getConnection().close();
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

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(InfoFinder.costOfProject(7));
        System.out.println("---------");
        InfoFinder.listOfDevelopersOfProject(7L).forEach(System.out::println);
        System.out.println("---------");
        InfoFinder.listOfJavaDevelopers().forEach(System.out::println);
        System.out.println("---------");
        InfoFinder.listOfMiddleDevelopers().forEach(System.out::println);
        System.out.println("---------");
        for (Project project : InfoFinder.listOfProjectsInfo()) {
            System.out.println("Project{" +
                    "projectName='" + project.getProjectName() + '\'' +
                    ", countOfDevelopers=" + project.getCountOfDevelopers() +
                    '}');
        }
    }
}
