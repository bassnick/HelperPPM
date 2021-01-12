package cz.bassnick.PPM.DAO.Player.Hazena;

public class OverviewPlayerInfo {
    private String firstname;
    private String lastname;
    private int energy;
    private int maxEnergy;

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

    public int getEnergy() { return energy; }

    public void setEnergy(int energy) { this.energy = energy; }

    public int getMaxEnergy() { return maxEnergy; }

    public void setMaxEnergy(int maxEnergy) { this.maxEnergy = maxEnergy; }
}
