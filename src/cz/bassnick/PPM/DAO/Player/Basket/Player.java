package cz.bassnick.PPM.DAO.Player.Basket;


public class Player {
    private String name; // key (may be bug if it is not unique)
    private String state;
    private int age;
    private int dayToBeIll;
    private AgreementPlayerInfo agreement;
    private BasicPlayerInfo basic;
    private TrainingPlayerInfo training;
    private OverviewPlayerInfo overview;
    private Stats40PlayerInfo stats40;
    private StatsGamePlayerInfo statsGame;
 //   private PlayerBindedInfoStats bindedAll;

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

    public AgreementPlayerInfo getAgreement() { return agreement; }

    public void setAgreement(AgreementPlayerInfo agreement) { this.agreement = agreement; }

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

    public OverviewPlayerInfo getOverview() { return overview; }

    public void setOverview(OverviewPlayerInfo overview) { this.overview = overview; }

    public Stats40PlayerInfo getStats40() { return stats40; }

    public void setStats40(Stats40PlayerInfo stats40) { this.stats40 = stats40; }

    public StatsGamePlayerInfo getStatsGame() { return statsGame; }

    public void setStatsGame(StatsGamePlayerInfo statsGame) { this.statsGame = statsGame; }
}
