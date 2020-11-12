package cz.bassnick.PPM.DAO.Player.Hokej;

public class Player {
    private String name; // key (may be bug if it is not unique)
    private String state;
    private int age;
    private int dayToBeIll;
    private OverviewPlayerInfo overview;
    private AgreementPlayerInfo agreement;
    private BasicPlayerInfo basic;
    private TrainingPlayerInfo training;
    private StatsFieldInfo statField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDayToBeIll() {
        return dayToBeIll;
    }

    public void setDayToBeIll(int dayToBeIll) {
        this.dayToBeIll = dayToBeIll;
    }

    public OverviewPlayerInfo getOverview() { return overview; }

    public void setOverview(OverviewPlayerInfo overview) { this.overview = overview; }

    public AgreementPlayerInfo getAgreement() {
        return agreement;
    }

    public void setAgreement(AgreementPlayerInfo agreement) {
        this.agreement = agreement;
    }

    public BasicPlayerInfo getBasic() {
        return basic;
    }

    public void setBasic(BasicPlayerInfo basic) {
        this.basic = basic;
    }

    public TrainingPlayerInfo getTraining() {
        return training;
    }

    public void setTraining(TrainingPlayerInfo training) {
        this.training = training;
    }

    public StatsFieldInfo getStatField() { return statField; }

    public void setStatField(StatsFieldInfo statField) { this.statField = statField; }

}
