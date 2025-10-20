public class HighScores {

    private int easy;
    private int medium;
    private int hard;

    public HighScores(int easy, int medium, int hard) {
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
    }

    public HighScores() {
        this.easy = Integer.MAX_VALUE;
        this.medium = Integer.MAX_VALUE;
        this.hard = Integer.MAX_VALUE;
    }

    public int getEasy() {
        return easy;
    }
    public int getMedium() {
        return medium;
    }
    public int getHard() {
        return hard;
    }

    public int getScore(int level) {
        return switch (level) {
            case 1 -> easy;
            case 2 -> medium;
            case 3 -> hard;
            default -> Integer.MAX_VALUE;
        };
    }

    public void setScore(int level, int score) {
        switch (level) {
            case 1 -> easy = score;
            case 2 -> medium = score;
            case 3 -> hard = score;
        }
    }
}
