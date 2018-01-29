package entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Developer {
    private long developerId;
    private String name;
    private int age;
    private String sex;
    private int salary;

    public Developer() {
    }

    public Developer(String name, int age, String sex, int salary) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
    }

    public Developer(long developerId, String name, int age, String sex, int salary) {
        this.developerId = developerId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "developerId=" + developerId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", salary=" + salary +
                '}';
    }
}
