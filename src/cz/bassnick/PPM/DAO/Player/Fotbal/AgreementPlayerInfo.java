package cz.bassnick.PPM.DAO.Player.Fotbal;

public class AgreementPlayerInfo {
    private String state;
    private String firstname;
    private String lastname;
    private int age;
    private int daysToBeIll;
    private int remainsDayOfAgreements;
    private int daySalary;
    private int daysInTeam;
    private String automaticProlongedAgreement;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDaysToBeIll() {
        return daysToBeIll;
    }

    public void setDaysToBeIll(int daysToBeIll) {
        this.daysToBeIll = daysToBeIll;
    }

    public int getRemainsDayOfAgreements() {
        return remainsDayOfAgreements;
    }

    public void setRemainsDayOfAgreements(int remainsDayOfAgreements) {
        this.remainsDayOfAgreements = remainsDayOfAgreements;
    }

    public int getDaySalary() {
        return daySalary;
    }

    public void setDaySalary(int daySalary) {
        this.daySalary = daySalary;
    }

    public int getDaysInTeam() {
        return daysInTeam;
    }

    public void setDaysInTeam(int daysInTeam) {
        this.daysInTeam = daysInTeam;
    }

    public String getAutomaticProlongedAgreement() {
        return automaticProlongedAgreement;
    }

    public void setAutomaticProlongedAgreement(String automaticProlongedAgreement) {
        this.automaticProlongedAgreement = automaticProlongedAgreement;
    }
}
