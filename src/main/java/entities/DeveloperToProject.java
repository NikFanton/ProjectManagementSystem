package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperToProject {
    long developerId;
    long projectId;

    public DeveloperToProject() {
    }

    public DeveloperToProject(long developerId, long projectId) {
        this.developerId = developerId;
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "DeveloperToProject{" +
                "developerId=" + developerId +
                ", projectId=" + projectId +
                '}';
    }
}
