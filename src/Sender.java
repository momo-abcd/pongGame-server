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

    public Sender(Socket socket, Paddle paddle1, Paddle paddle2, Ball ball){
        try {
            this.pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error occuured");
        }
        this.paddle1 = paddle1;
        this.paddle2 = paddle2;
        this.ball = ball;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            System.out.println("sent");
            send();
            }
        }, 0, 100);
    }

    public void send() {
        pw.println(paddle1.getY() + ":" + paddle2.getY() + ":" + ball.getX() + ":" + ball.getY());
        pw.flush();
    }
}
