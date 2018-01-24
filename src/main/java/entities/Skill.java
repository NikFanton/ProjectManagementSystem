package entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Skill {
    private long skillId;
    private String skillName;
    private String level;

    public Skill(long skillId, String skillName, String level) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.level = level;
    }

    public Skill(String skillName, String level) {
        this.skillName = skillName;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
