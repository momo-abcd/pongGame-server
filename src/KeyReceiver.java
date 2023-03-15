import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class KeyReceiver{
    private BufferedReader br;
    private PrintWriter pw;
    private Vector<Socket> playerList;
    private Ball ball;
    private Paddle player1;
    private Paddle player2;
    private Socket socket;
    private boolean p1_wPressed = false;
    private boolean p1_sPressed = false;
    private boolean p2_wPressed = false;
    private boolean p2_sPressed = false;

    private Thread keyReceiver;

    // create constructor
    public KeyReceiver(Socket socket, Vector<Socket> playerList, Paddle player1, Paddle player2, Ball ball) {
        this.playerList = playerList;
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
        this.socket = socket;
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // pw = new PrintWriter(socket.getOutputStream());
        }catch (IOException e){
            System.err.println(e);
        }

        keyReceiver = initKeyReceiver();
        keyReceiver.start();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                    gameLogic();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0, 500);
    
    }
    public Thread initKeyReceiver() {
        return new Thread() {
            @Override
            public void run() {
                /** 
                 * data 형식 :playerCode + ":"+keyCode.toLowerCase()+":"+instruction;
                 * ex : p1:w:KEY_PRESSED
                 *      p2:s:KEY_RELEASED
                 */
                System.out.println("running");
                try {
                    String data = "";
                    while((data = br.readLine()) != null){
                        System.out.println(data + "// KeyReceiver.java 55");
                        if(data.equals("p1:w:KEY_PRESSED")){
                            p1_wPressed = true;
                        }
                        else if(data.equals("p1:w:KEY_RELEASED")){
                            p1_wPressed = false;
                        }
                        else if(data.equals("p1:s:KEY_PRESSED")){
                            p1_sPressed = true;
                        }
                        else if(data.equals("p1:s:KEY_RELEASED")){
                            p1_sPressed = false;
                        }

                        else if(data.equals("p2:w:KEY_PRESSED")){
                            p2_wPressed = true;
                        }
                        else if(data.equals("p2:w:KEY_RELEASED")){
                            p2_wPressed = false;
                        }
                        else if(data.equals("p2:s:KEY_PRESSED")){
                            p2_sPressed = true;
                        }
                        else if(data.equals("p2:s:KEY_RELEASED")){
                            p2_sPressed = false;
                        }else{}
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    private void send(String data){
        Iterator<Socket> it = playerList.iterator();
        while(it.hasNext()) {
            Socket s = (Socket) it.next();
            try {
                pw = new PrintWriter(s.getOutputStream());
                pw.println(data);
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void gameLogic() {
        if(p1_wPressed){
            player1.update(-GameBoard.speed);
        }
        if(p1_sPressed){
            player1.update(GameBoard.speed);
        }
        if(p2_wPressed){
            player2.update(-GameBoard.speed);
        }
        if(p2_sPressed){
            player2.update(GameBoard.speed);
        }

        ball.update();
        System.out.println("player1 X : " + player1.getX());
        System.out.println("player1 Y : " + player1.getY());
    }
}
