import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class GameQueue extends Thread {
    private int port = 3333;
    private String ip = "localhost";

    private Vector<Socket> playerList = null;
    private ServerSocket queueSocket;

    // constructor
    public GameQueue() {
        try {
            InetSocketAddress addr = new InetSocketAddress(ip, port);
            queueSocket = new ServerSocket();
            queueSocket.bind(addr);
            // AAA playerList = new Vector<>();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        playerList = new Vector<>();
            while(true){
                Socket client;
                try {
                    client = queueSocket.accept();
                    playerList.add(client);
                    System.out.println(client.getLocalSocketAddress() + " 클라이언트 접속 / 현재 큐 접속자 : (" +playerList.size()+"/2)" );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(playerList == null){
                    playerList = new Vector<>();
                }

                // 큐에 2명이 접속하면 게임방 하나를 만들어서 진행(게임 시작)
                if(playerList.size() == 2) {
                    Boolean isAllConnected = true;  //모든 소켓들이 살아있는 지 체크하는 변수
                    PrintWriter pw;
                    BufferedReader br;
                    Iterator<Socket> it = playerList.iterator();
                    while(it.hasNext()){
                        if(!isAllConnected) break;
                        Socket s = it.next();
                        try {
                            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            pw = new PrintWriter(s.getOutputStream());
                            pw.println("ready?");
                            pw.flush();
                            if(br.readLine() != null){
                                // pw.println("gameStart");
                                // pw.flush();
                            }
                        } catch (IOException e) {
                            System.err.println("GameQueue.java 76 Exception");
                            // e.printStackTrace();
                            playerList.remove(s);
                            System.out.println(playerList);
                            isAllConnected = false;
                            break;
                        }
                    }
                    if(isAllConnected){
                        new GameRoom(playerList);
                        playerList = null;
                    }
                }
            }
    }
}
