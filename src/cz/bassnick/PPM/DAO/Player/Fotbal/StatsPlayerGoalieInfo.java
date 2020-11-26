package cz.bassnick.PPM.DAO.Player.Fotbal;

public class StatsPlayerGoalieInfo {
    private String state;
    private String firstname;
    private String lastname;
    private int daysToBeIll;
    private int MP;
    private int GA;
    private int SV;
    private float SvPercentage;
    private int CS;
    private String Str;
    private String LStr;

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

    public int getGA() {
        return GA;
    }

    public void setGA(int GA) {
        this.GA = GA;
    }

    public int getSV() {
        return SV;
    }

    public void setSV(int SV) {
        this.SV = SV;
    }

    public float getSvPercentage() {
        return SvPercentage;
    }

    public void setSvPercentage(float svPercentage) {
        SvPercentage = svPercentage;
    }

    public int getCS() {
        return CS;
    }

    public void setCS(int CS) {
        this.CS = CS;
    }

    public String getStr() {
        return Str;
    }

    public void setStr(String str) {
        Str = str;
    }

    public String getLStr() {
        return LStr;
    }

    public void setLStr(String LStr) {
        this.LStr = LStr;
    }
}

