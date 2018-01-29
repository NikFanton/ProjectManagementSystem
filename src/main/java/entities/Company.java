package entities;

public class Company {
    private long companyId;
    private String companyName;
    private int yearOfFoundation;

    public Company() {
    }

    public Company(String companyName, int yearOfFoundation) {
        this.companyName = companyName;
        this.yearOfFoundation = yearOfFoundation;
    }

    public Company(long companyId, String companyName, int yearOfFoundation) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.yearOfFoundation = yearOfFoundation;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", yearOfFoundation=" + yearOfFoundation +
                '}';
    }
}
