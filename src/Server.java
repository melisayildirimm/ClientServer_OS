import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        new Server().start();
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true){
                Socket s = serverSocket.accept();
                new Thread(new Client(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Client implements Runnable{
        private Socket socket = null;
        private DataOutputStream dos;
        private DataInputStream dis;

        private int attempt = 3;
        private int rand = (int)(Math.random()*9 + 1);


        public Client(Socket s){
            try {
                this.socket = s;
                this.dos = new DataOutputStream(s.getOutputStream());
                this.dis = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("Random number: " + rand);
            while (true){
                try {

                    int guess = dis.readInt();
                    System.out.println(guess);

                    if(guess == rand){
                        dos.writeUTF("True");
                        System.out.println("True");
                        System.out.println("Client " + this.socket + " sends exit...");
                        System.out.println("Closing this connection.");
                        dos.writeUTF("exit");
                        this.socket.close();
                        System.out.println("Connection closed");
                        break;
                    }

                    attempt--;

                    if(attempt == 0){
                        dos.writeUTF("all chances are finished");
                        dos.writeInt(rand);
                        System.out.println("Wrong with the true number:"+rand);
                        System.out.println("Client " + this.socket + " sends exit...");
                        System.out.println("Closing this connection.");
                        dos.writeUTF("exit");
                        this.socket.close();
                        System.out.println("Connection closed");
                        break;
                    }

                    if(guess > rand){
                        dos.writeUTF("Lower");
                        System.out.println("Lower");
                    }else{
                        dos.writeUTF("Higher");
                        System.out.println("Higher");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try{
                this.dis.close();
                this.dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



