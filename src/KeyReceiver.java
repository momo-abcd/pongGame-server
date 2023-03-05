import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class KeyReceiver extends Thread {
    private BufferedReader br;
    private PrintWriter pw;
    private Vector<Socket> playerList;

    // create constructor
    public KeyReceiver(Socket socket, Vector<Socket> playerList) {
        try{
            this.playerList = playerList;
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // pw = new PrintWriter(socket.getOutputStream());
        }catch (IOException e){
            System.err.println(e);
        }
    }

    @Override
    public void run() {
        try {
            String data = "";
            while((data = br.readLine()) != null){
                if(data.equals("p1:w")){
                    send("p1:w");
                }
                else if(data.equals("p1:s")){
                    send("p1:s");
                }
                else if(data.equals("p2:w")){
                    send("p2:w");
                }
                else if(data.equals("p2:s")){
                    send("p1:s");
                }
                else {
                    send("nothing");
                    System.out.println(data);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void send(String data){
        Iterator it = playerList.iterator();
        while(it.hasNext()) {
            Socket s = (Socket) it.next();
            try {
                pw = new PrintWriter(s.getOutputStream());
                pw.println(data);
                pw.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
}
