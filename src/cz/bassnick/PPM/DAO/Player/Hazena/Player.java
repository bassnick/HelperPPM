package cz.bassnick.PPM.DAO.Player.Hazena;


import cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.StatsPlayerGoalieInfo;
import cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo;


public class Player {
    private String name; // key (may be bug if it is not unique)
    private String state;
    private int age;
    private int dayToBeIll;
    private cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo agreement;
    private cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo basic;
    private cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo training;
    private cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo overview;
    private cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo fieldStats;
    private cz.bassnick.PPM.DAO.Player.Hazena.StatsPlayerGoalieInfo goalieStats;
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

    public cz.bassnick.PPM.DAO.Player.Hazena.AgreementPlayerInfo getAgreement() { return agreement; }

    public void setAgreement(AgreementPlayerInfo agreement) { this.agreement = agreement; }

    public cz.bassnick.PPM.DAO.Player.Hazena.BasicPlayerInfo getBasic() {
        return basic;
    }

    public void setBasic(BasicPlayerInfo basic) {
        this.basic = basic;
    }

    public cz.bassnick.PPM.DAO.Player.Hazena.TrainingPlayerInfo getTraining() {
        return training;
    }

    public void setTraining(TrainingPlayerInfo training) {
        this.training = training;
    }

    public cz.bassnick.PPM.DAO.Player.Hazena.OverviewPlayerInfo getOverview() { return overview; }

    public void setOverview(OverviewPlayerInfo overview) { this.overview = overview; }

    public cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo getFieldStats() { return fieldStats; }

    public void setFieldStats(cz.bassnick.PPM.DAO.Player.Hazena.StatsFieldInfo fieldStats) { this.fieldStats = fieldStats; }

    public cz.bassnick.PPM.DAO.Player.Hazena.StatsPlayerGoalieInfo getGoalieStats() {
        return goalieStats;
    }

    public void setGoalieStats(StatsPlayerGoalieInfo goalieStats) {
        this.goalieStats = goalieStats;
    }

    /*  public PlayerBindedInfoStats getBindedAll() {
        return bindedAll;
    }

    public void setBindedAll(PlayerBindedInfoStats bindedAll) {
        this.bindedAll = bindedAll;
    }
    */

}
