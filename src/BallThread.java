import java.util.Timer;
import java.util.TimerTask;

public class BallThread{
    private Ball ball;
    public BallThread(Ball ball){
        this.ball = ball;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                logic();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 500);

    }

    public void logic() {
        ball.update();
    }
}