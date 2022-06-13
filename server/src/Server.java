import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
public class Server {

    public static int postep=50;

    public void startServer() throws IOException
    {

        ServerSocket ss = new ServerSocket(5056);

        while (true)
        {
            Socket s = null;

            try
            {
                s = ss.accept();

                System.out.println("Połączono klienta ");

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                Thread t = new ClientHandler(s, dis, dos, this);
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
    public void setPostep(int value){
        postep = value;
    }
    public int getPostep(){
        return postep;
    }
}

class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    Server server;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, Server server)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.server = server;
    }

    @Override
    public void run()
    {
        int received;
        while (true)
        {
            try {

                dos.writeUTF("1 aby dodac 0 aby odjac"); //#1
                dos.write(server.getPostep());  //#2

                received = dis.read(); // #3

                if(received == 5)
                {
                    System.out.println("Wylaczam polaczenie");
                    this.s.close();
                    break;
                }


                switch (received) {

                    case 0:
                        server.setPostep(server.getPostep()-1);
                        break;

                    case 1 :
                        server.setPostep(server.getPostep()+1);
                        break;
                    case 2 :
                        dos.write(server.getPostep());
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }




}
