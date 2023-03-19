public class PlayerScore {
    int score;
    public PlayerScore() {
        score = 0;
    }
    public int getScore() {
        return score;
    }
    public void increment() {
        score += 1;
    }
}
