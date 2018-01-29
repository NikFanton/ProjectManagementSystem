package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {
    private long id;
    private String projectName;
    private long idCustomer;
    private long idCompany;
    private int cost;
    private int countOfDevelopers;

    public Project() {
    }

    public Project(String projectName, long idCustomer, long idCompany, int cost) {
        this.projectName = projectName;
        this.idCustomer = idCustomer;
        this.idCompany = idCompany;
        this.cost = cost;
    }

    public Project(long id, String projectName, long idCustomer, long idCompany, int cost) {
        this.id = id;
        this.projectName = projectName;
        this.idCustomer = idCustomer;
        this.idCompany = idCompany;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", idCustomer=" + idCustomer +
                ", idCompany=" + idCompany +
                ", cost=" + cost +
                '}';
    }
}
