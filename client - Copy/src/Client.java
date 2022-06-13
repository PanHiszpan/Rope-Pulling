
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JProgressBar;


public class Client extends JFrame{
    JProgressBar postep;
    int p=0;
    public Client() {

        setTitle("Client Przeciagania Liny");
        setSize(800,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        postep = new JProgressBar(0,100);
        postep.setStringPainted(true);
        postep.setValue(0);
        postep.setBounds(50,50,700,50);
        add(postep);

    }

    public void startClient() throws IOException
    {
        try
        {
            Scanner scn = new Scanner(System.in);

            InetAddress ip = InetAddress.getByName("localhost");

            Socket s = new Socket(ip, 5056);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());


            while (true)
            {

                System.out.println(dis.readUTF()); //#1
                p=dis.read();   //#2
                System.out.println("p: " + p);
                postep.setValue(p);
                int tosend = scn.nextInt();
                dos.write(tosend); //#3

                if(tosend == 5)
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }
                if(tosend == 2)
                {
                    System.out.println(dis.read());
                    System.out.println("p: " + p);
                }

            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

