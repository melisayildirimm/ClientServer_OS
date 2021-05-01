import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        new Client().start();
    }

    public void start(){
        try {
            Socket socket = new Socket("localhost", 5000);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("Enter a number: ");
                dos.writeInt(scanner.nextInt());

                String cvp = dis.readUTF();
                if(cvp.equals("all chances are finished")){
                    System.out.println("Wrong with the true number: " + dis.readInt());
                    break;
                }else if(cvp.equals("True")){
                    System.out.println(cvp);
                    break;
                }else{
                    System.out.println(cvp);
                }
            }

            scanner.close();
            dis.close();
            dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
