import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class GameRoom {
    KeyReceiver keyReceiver;
    public GameRoom(Vector<Socket> players){
        System.out.println("Game Room 생성 / 게임 시작");
        Iterator it = players.iterator();
        while(it.hasNext()) {
            Socket s = (Socket) it.next();
            new KeyReceiver(s, players);
        }
    }
}