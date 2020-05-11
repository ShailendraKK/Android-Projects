/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flyingorderticketdesktop;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author shailendra
 */
public class Signing{
    int port=6017;
    //String server_ip="10.0.0.2";
    ServerSocket server;
    public Signing()
    {
       try
       {
           server=new ServerSocket(6017);
       }
       catch(Exception e)
       {
           System.err.println(""+e);
       }

    }

    public static void main(String[] args)
    {
        Signing ou=new Signing();
        ou.handleNewConnection();

    }
    public void handleNewConnection()
     {
         System.out.println("Waiting for client to connect");
         while(true)
         {
             try
             {
                 Socket socket=server.accept();
                 new OrderHandlerS(socket);
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
     }


}
class OrderHandlerS implements Runnable
{
    String time;
    public static Object[][] Data;
private Socket socket;
private BufferedReader input;

public OrderHandlerS(Socket socket)
{
      System.out.println("Connected 1");
    this.socket=socket;
    Thread t=new Thread(this);
    t.start();
//     try{ t.sleep(1000);}
 //  catch(Exception e){}

}

    public void run() {
      try
      {

          // DataInputStream dis=new DataInputStream   (socket.getInputStream());
               System.out.println("Connected");
          this.input=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
          String username=input.readLine();
          String password=input.readLine();
         OutputStream os1=socket.getOutputStream();
       OutputStreamWriter osw=new OutputStreamWriter(os1);
          //String ItemName=input.readLine();
          //String qualtity=input.readLine();
          //String table_no=input.readLine();
BufferedWriter bw=new BufferedWriter(osw);
int status =authenticateuser(username,password);
if(status==1)

       bw.write("success");
else
     bw.write("failure");
bw.flush();

          input.close();
          
           socket.close();
          System.out.println("Username ="+username+" Password ="+password);
          System.out.println("Message sent to server");
System.out.println("Waiting for client message");
       }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }



      }

    private int authenticateuser(String username, String password) {
        try
    {
    // Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
         Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

            Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      String query="select * from M_UserID";
        ResultSet rs=st.executeQuery(query);
        System.out.println("After query");
        if(rs.next())
            {
            if(rs.getString(3).equals(username)&&rs.getString(4).equals(password))
                c.close();
                return 1;
        }
       
     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
        return -1;
    }
}










