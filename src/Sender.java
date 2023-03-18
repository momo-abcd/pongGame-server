import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Sender{
    private Socket socket;
    PrintWriter pw;
    Thread thread;
    Timer timer;

    Paddle paddle1;
    Paddle paddle2;
    Ball ball;

    PlayerScore player1_Score;
    PlayerScore player2_Score;

    public Sender(Socket socket, Paddle paddle1, Paddle paddle2, Ball ball,
    PlayerScore player1_score, PlayerScore player2_score
    ){
        try {
            this.pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error occuured");
        }
        this.socket = socket;
        this.paddle1 = paddle1;
        this.paddle2 = paddle2;
        this.ball = ball;
        this.player1_Score = player1_score;
        this.player2_Score = player2_score;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            // System.out.println("sent");
            send();
            }
        }, 0, 10);
    }

    public void send() {
        System.out.println(player1_Score.getScore() + "/" + player2_Score.getScore());
        pw.println(paddle1.getY() + ":" + paddle2.getY() + ":" + ball.getX() + ":" + ball.getY() + ":" + player1_Score.getScore() + ":" + player2_Score.getScore());
        pw.flush();
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }
    public void isClosed() {
        System.out.println(socket.isClosed());
        if(socket.isClosed()){
            stop();
        }
    }
}
