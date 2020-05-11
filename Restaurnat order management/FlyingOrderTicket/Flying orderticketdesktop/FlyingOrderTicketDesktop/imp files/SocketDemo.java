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
public class SocketDemo {
    int port=6015;
    //String server_ip="10.0.0.2";
    ServerSocket server;
  public static String[][] items;
    public SocketDemo()
    {
       try
       {
           server=new ServerSocket(6015);
       }
       catch(Exception e)
       {
           System.err.println(""+e);
       }

    }

    public static void main(String[] args)
    {
   
        SocketDemo ou=new SocketDemo();

        ou.handleNewConnection();


    }
    public void load_data()
    {
    try
    {
   // Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
       Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

        Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=st.executeQuery("select * from M_Item");
        ResultSetMetaData rsmd=rs.getMetaData();
int cols=rsmd.getColumnCount();
rs.last();
int rows=rs.getRow();
rs.beforeFirst();
System.out.println(rows);

            items=new String[rows+1][cols+1];
            System.out.println(items.length);
            System.out.println(items[0].length);
            int j=1;
        while(rs.next())
        {

            for(int i=1;i<=cols;i++)
            {
                items[j][i]=rs.getString(i);
       //         System.out.print(rs.getString(i)+"\t");
            }
            j++;
                        System.out.println();
            System.out.println();

       }
for(int i=1;i<items.length;i++)
{
    for(int j1=1;j1<items[1].length;j1++)
    {
        System.out.println(items[i][j1]+"\t");
    }
    System.out.println();
}
        }
    catch(Exception e)
    {

    }
}
    public void handleNewConnection()
     {
        load_data();
         System.out.println("Waiting for client to connect");
         while(true)
         {
             try
             {
                 Socket socket=server.accept();
                 new OrderHandlerSoc(socket);
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
     }


}
class OrderHandlerSoc implements Runnable
{
    String time;
    public static Object[][] Data;
private Socket socket;
private BufferedReader input;

public OrderHandlerSoc(Socket socket)
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
          String messageid=input.readLine();
          System.out.println("ID  :"+messageid);
               if(messageid.equals("clone items"))
          {
             //   OutputStreamWriter osw=new OutputStreamWriter(socket.getOutputStream());
        

      // BufferedWriter bw=new BufferedWriter(osw);
//String row_string=rows+"";
  //     bw.write(row_string);
       
    //   System.out.println(row_string);
       
     //  bw.flush();
      
  ObjectOutputStream os=new ObjectOutputStream(socket.getOutputStream());
String[][] tempitems=SocketDemo.items;
 os.writeObject(tempitems);
            os.flush();
            
             // OutputStreamWriter osw=new OutputStreamWriter(os);
       //os.flush();

     //  BufferedWriter bw=new BufferedWriter(osw);

      // bw.write(time);
          System.out.println("Message sent to server");
            //rs.getString(1);




         // System.out.println("Waiting for client message");
//osw.close();
 os.close();
// bw.close();

              input.close();
         socket.close();

                  }

       }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }



      }


}










