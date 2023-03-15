import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class GameBoard {
    final static double Width = 640;
    final static double Height = 540;
    static double speed = 10; //패들 움직임 속도

    private KeyReceiver keyReceiver;

    private Paddle player1_paddle;
    private Paddle player2_paddle;
    private Ball ball;
    public GameBoard(Vector<Socket> players){
            player1_paddle = new Paddle(0, Height/2-50);
            player2_paddle = new Paddle(Width-Paddle.Width,Height/2-50);
            ball = new Ball();

            Iterator<Socket> it = players.iterator();
            while(it.hasNext()) {
                Socket s = it.next();
                keyReceiver =  new KeyReceiver(s, players, player1_paddle, player2_paddle, ball);
                // keyReceiver.start();
            }

    }
}