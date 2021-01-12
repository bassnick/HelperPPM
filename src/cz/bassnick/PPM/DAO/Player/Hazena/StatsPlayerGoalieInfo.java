package cz.bassnick.PPM.DAO.Player.Hazena;

public class StatsPlayerGoalieInfo {
    private String state;
    private String firstname;
    private String lastname;
    private int daysToBeIll;
    private int MP;
    private int SV;
    private int SA;
    private int GA;
    private float SvPercentage;
    private int SV7m;
    private int SA7m;
    private int GA7m;
    private float M7Percentage;

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

    public int getDaysToBeIll() {
        return daysToBeIll;
    }

    public void setDaysToBeIll(int daysToBeIll) {
        this.daysToBeIll = daysToBeIll;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getSV() {
        return SV;
    }

    public void setSV(int SV) {
        this.SV = SV;
    }

    public int getSA() {
        return SA;
    }

    public void setSA(int SA) {
        this.SA = SA;
    }

    public int getGA() {
        return GA;
    }

    public void setGA(int GA) {
        this.GA = GA;
    }

    public float getSvPercentage() {
        return SvPercentage;
    }

    public void setSvPercentage(float svPercentage) {
        SvPercentage = svPercentage;
    }

    public int getSV7m() {
        return SV7m;
    }

    public void setSV7m(int SV7m) {
        this.SV7m = SV7m;
    }

    public int getSA7m() {
        return SA7m;
    }

    public void setSA7m(int SA7m) {
        this.SA7m = SA7m;
    }

    public int getGA7m() {
        return GA7m;
    }

    public void setGA7m(int GA7m) {
        this.GA7m = GA7m;
    }

    public float getM7Percentage() {
        return M7Percentage;
    }

    public void setM7Percentage(float m7Percentage) {
        M7Percentage = m7Percentage;
    }
}