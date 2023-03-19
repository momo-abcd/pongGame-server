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

    private GameBoard gameBoard;

    // create constructor
    public KeyReceiver(
        Socket socket,
         Vector<Socket> playerList,
          Paddle player1, Paddle player2,
          Ball ball,
          GameBoard gameBoard
        ) {
        this.playerList = playerList;
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
        this.socket = socket;
        this.gameBoard = gameBoard;
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // pw = new PrintWriter(socket.getOutputStream());
        }catch (IOException e){
            System.err.println(e);
        }

        keyReceiver = initKeyReceiver();
        keyReceiver.setDaemon(true);
        keyReceiver.start();

        // TimerTask timerTask = new TimerTask() {
        //     @Override
        //     public void run() {
        //             gameLogic();
        //     }
        // };
        // Timer timer = new Timer();
        // timer.schedule(timerTask,0, 500);
    
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
                System.out.println("keyReceiver is started");
                try {
                    String data = "";
                    while((data = br.readLine()) != null){
                        // System.out.println(data + "// KeyReceiver.java 55");
                        if(data.equals("p1:w:KEY_PRESSED")){
                            gameBoard.p1_wPressed.setValue(true);
                        }
                        else if(data.equals("p1:w:KEY_RELEASED")){
                            gameBoard.p1_wPressed.setValue(false);
                        }
                        else if(data.equals("p1:s:KEY_PRESSED")){
                            gameBoard.p1_sPressed.setValue(true);
                        }
                        else if(data.equals("p1:s:KEY_RELEASED")){
                            gameBoard.p1_sPressed.setValue(false);
                        }

                        else if(data.equals("p2:up:KEY_PRESSED")){
                            gameBoard.p2_wPressed.setValue(true);
                        }
                        else if(data.equals("p2:up:KEY_RELEASED")){
                            p2_wPressed = false;
                            gameBoard.p2_wPressed.setValue(false);
                        }
                        else if(data.equals("p2:down:KEY_PRESSED")){
                            gameBoard.p2_sPressed.setValue(true);
                        }
                        else if(data.equals("p2:down:KEY_RELEASED")){
                            gameBoard.p2_sPressed.setValue(false);
                        }else{}
                        // System.out.println("data : "+ data);
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    gameBoard.senderStop();
                }
            }
        };
    }
    public boolean isClosed() {
        try {
            if(br.readLine() == null) return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
