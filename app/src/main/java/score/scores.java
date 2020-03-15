package score;

public class scores {
    private static final scores ourInstance = new scores();

    public static scores getInstance() {
        return ourInstance;
    }

    private scores() {
    }
    private int math,phy,bio,eng;

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getPhy() {
        return phy;
    }

    public void setPhy(int phy) {
        this.phy = phy;
    }

    public int getBio() {
        return bio;
    }

    public void setBio(int bio) {
        this.bio = bio;
    }

    public int getEng() {
        return eng;
    }

    public void setEng(int eng) {
        this.eng = eng;
    }
}
