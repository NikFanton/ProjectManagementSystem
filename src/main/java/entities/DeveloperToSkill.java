package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperToSkill {
    private long developerId;
    private long skillId;

    public DeveloperToSkill(long developerId, long skillId) {
        this.developerId = developerId;
        this.skillId = skillId;
    }

    @Override
    public String toString() {
        return "DeveloperToSkill{" +
                "developerId=" + developerId +
                ", skillId=" + skillId +
                '}';
    }
}
