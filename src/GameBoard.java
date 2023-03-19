import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameBoard {
    final static double Width = 640;
    final static double Height = 540;
    static double speed = 10; //패들 움직임 속도

    private KeyReceiver[] keyReceiver = new KeyReceiver[2];
    private Sender[] sender = new Sender[2];
    private BufferedReader br;
    private PrintWriter pw;

    private Paddle player1_paddle;
    private Paddle player2_paddle;
    private Ball ball;

    private PlayerScore player1_score = new PlayerScore();
    private PlayerScore player2_score = new PlayerScore();


    public BooleanPressed p1_wPressed = new BooleanPressed();
    public BooleanPressed p1_sPressed = new BooleanPressed();
    public BooleanPressed p2_wPressed = new BooleanPressed();
    public BooleanPressed p2_sPressed = new BooleanPressed();

    Socket[] playersSocket = new Socket[2];


    public GameBoard(Vector<Socket> players){
            player1_paddle = new Paddle(0, Height/2-50);
            player2_paddle = new Paddle(Width-Paddle.Width,Height/2-50);
            ball = new Ball();

            Iterator<Socket> it = players.iterator();
            int index = 0;
            while(it.hasNext()) {
                playersSocket[index] = it.next();
                keyReceiver[index] =  new KeyReceiver(playersSocket[index], players, player1_paddle, player2_paddle, ball, this);
                index++;
                // TMP
                // sender[index++] = new Sender(s, player1_paddle, player2_paddle, ball, player1_score, player2_score, this);

                // keyReceiver.start();
            }
            gameLogicThread();
    }

    public void senderStop() {
        // if(keyReceiver[0].isClosed()) {
        //     sender[0].isClosed();
        //     sender[1].isClosed();
        // }
        // if(keyReceiver[1].isClosed()) {
        //     sender[0].isClosed();
        //     sender[1].isClosed();
        // }

            // TMP
            // sender[0].isClosed();
            // sender[1].isClosed();
    }

    private void gameLogicThread() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameLogic();
            }
        }, 0, 10);
    }

    public void gameLogic() {
        if(p1_wPressed.getValue()){
            player1_paddle.update(-GameBoard.speed);
        }
        if(p1_sPressed.getValue()){
            player1_paddle.update(GameBoard.speed);
        }
        if(p2_wPressed.getValue()){
            player2_paddle.update(-GameBoard.speed);
        }
        if(p2_sPressed.getValue()){
            player2_paddle.update(GameBoard.speed);
        }

        // 패들과 공의 충돌 확인
        if(player1_paddle.detectBallCollision(ball)){
            double collidPoint = (ball.getY() - (player1_paddle.getY() + Paddle.Height/2));
            collidPoint = collidPoint / (Paddle.Height/2);

            double angleRad = (Math.PI/4) * collidPoint;
            double direction = ball.getX() + ball.getRadius() < Width/2 ? 1 : -1;
            ball.setXVelocity(angleRad, direction);
            ball.setYVelocity(angleRad, direction);
        }
        if(player2_paddle.detectBallCollision(ball)){
            double collidPoint = (ball.getY() - (player2_paddle.getY() + Paddle.Height/2));
            collidPoint = collidPoint / (Paddle.Height/2);

            double angleRad = (Math.PI/4) * collidPoint;
            double direction = ball.getX() + ball.getRadius() < Width/2 ? 1 : -1;
            ball.setXVelocity(angleRad, direction);
            ball.setYVelocity(angleRad, direction);
        }
        // 공이 나갔을 경우 다시 리셋
        if((int)ball.getX() <= -1){
            ball.resetBall();
            player1_score.increment();
            // if(score.addP1Score()){
            //     // 게임 끝내기
            //     endGame("You Win!");
            // }
        }

        if((int)ball.getX()+ball.getRadius() >= (int)Width+1){
            ball.resetBall();
            player2_score.increment();
            // if(score.addP2Score()) {
            //     // 게임 끝내기
            //     endGame("You Lose!");
            // }
        }

        ball.update();
        (new Sender(playersSocket[0], player1_paddle, player2_paddle, ball, player1_score, player2_score, this)).send();
        (new Sender(playersSocket[1], player1_paddle, player2_paddle, ball, player1_score, player2_score, this)).send();
    }

    class BooleanPressed{
        boolean value;
        public BooleanPressed() {
            value = false;
        }
        synchronized public boolean getValue(){
            return value;
        }
        synchronized public void setValue(boolean thing){
            value =  thing;
        }
    }
}