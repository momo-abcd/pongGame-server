import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class GameRoom {
    KeyReceiver keyReceiver;
    private BufferedReader br;
    private PrintWriter pw;

    private GameBoard gameBoard;

    public GameRoom(Vector<Socket> players){
        System.out.println("Game Room 생성 / 게임 시작");
        Iterator<Socket> it = players.iterator();
        int playerCount = 1;

        //  게임 시작을 위한 루프
        while(it.hasNext()) {
            Socket s = it.next();
            try {
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                pw = new PrintWriter(s.getOutputStream());
                pw.println("gameStart:p"+playerCount++);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        gameBoard = new GameBoard(players);
    }
}