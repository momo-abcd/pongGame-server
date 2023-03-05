import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Sender extends Thread {
    private Socket socket;
    BufferedReader br;
    PrintWriter pw;

    private String[] messageQueue;
    private Queue<String> queue = new LinkedList<>();
    public Sender(Socket s){
        socket = s;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            String s;
            while((s = br.readLine()) != null){
                System.out.print("보낼 문자 입력: ");
                send(s);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void send(String str) {
        pw.println(str);
        pw.flush();
    }
}
