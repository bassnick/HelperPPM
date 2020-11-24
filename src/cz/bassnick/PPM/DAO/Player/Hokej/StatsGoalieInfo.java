package cz.bassnick.PPM.DAO.Player.Hokej;

public class StatsGoalieInfo {
    private String state;
    private String firstname;
    private String lastname;
    private int daysToBeIll;
    private int GP;
    private int TOI;
    private int SA;
    private int GA;
    private float GAA;
    private float SvPercentage;
    private int SO;
    private int A;
    private int TP;
    private int Hv;
    private float HK;

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

    public int getGP() {
        return GP;
    }

    public void setGP(int GP) {
        this.GP = GP;
    }

    public int getTOI() {
        return TOI;
    }

    public void setTOI(int TOI) {
        this.TOI = TOI;
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

    public float getGAA() {
        return GAA;
    }

    public void setGAA(float GAA) {
        this.GAA = GAA;
    }

    public float getSvPercentage() {
        return SvPercentage;
    }

    public void setSvPercentage(float svPercentage) {
        SvPercentage = svPercentage;
    }

    public int getSO() {
        return SO;
    }

    public void setSO(int SO) {
        this.SO = SO;
    }

    public int getA() {
        return A;
    }

    public void setA(int a) {
        A = a;
    }

    public int getTP() {
        return TP;
    }

    public void setTP(int TP) {
        this.TP = TP;
    }

    public int getHv() {
        return Hv;
    }

    public void setHv(int hv) {
        Hv = hv;
    }

    public float getHK() {
        return HK;
    }

    public void setHK(float HK) {
        this.HK = HK;
    }
}