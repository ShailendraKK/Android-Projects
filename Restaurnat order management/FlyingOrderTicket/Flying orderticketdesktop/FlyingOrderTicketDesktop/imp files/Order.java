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
import java.util.Currency;
import java.util.Date;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author shailendra
 */
public class Order{
    int port=6016;
    //String server_ip="10.0.0.2";
    ServerSocket server;
    public Order()
    {
       try
       {
           server=new ServerSocket(6016);
       }
       catch(Exception e)
       {
           System.err.println(""+e);
       }

    }

    public static void main(String[] args)
    {
        Order ou=new Order();
        ou.handleNewConnection();

    }
    public void handleNewConnection()
     {
         System.out.println("Waiting for order");
         while(true)
         {
             try
             {
                 Socket socket=server.accept();
                 new OrderHandlerO(socket);
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
     }


}
class OrderHandlerO implements Runnable
{
    String time;
    public static Object[][] Data;
private Socket socket;
private BufferedReader input;

public OrderHandlerO(Socket socket)
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
          String no=input.readLine();
           for(int i=0;i<Integer.parseInt(no);i++)
           {
            String id=input.readLine();
            String name=input.readLine();
            String quantity=input.readLine();
            String Tableno=input.readLine();
            String username=input.readLine();
            insertintodb(id,name,quantity,Tableno,username);
           }
          OutputStream os1=socket.getOutputStream();
       OutputStreamWriter osw=new OutputStreamWriter(os1);
          //String ItemName=input.readLine();
          //String qualtity=input.readLine();
          //String table_no=input.readLine();
BufferedWriter bw=new BufferedWriter(osw);

       bw.write("success");
          bw.flush();
          
          input.close();
           socket.close();
   //       System.out.println("Number  :"+no);
     //     System.out.println("Message sent to server");
System.out.println("Waiting for client message");
       }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }



      }

  
public void insertintodb(String id,String name,String quantity,String table_no,String username)
    {
         try
    {
   //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
        Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

             Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          String rate=getRate(Integer.parseInt(id));
        double r=Double.parseDouble(rate);
        double amt=r*(Integer.parseInt(quantity));
       String amout=amt+"";
       String srn=getserialno();
      String sec=getSec(table_no);
   
Date d=new Date();

   
      String query="insert into Daily_SALE_Item(RecordNo,ItemCode,Rate,Qty,Amount,SrNo,Sec_ID) values(1,"+id+","+rate+","+quantity+","+amout+","+srn+","+sec+")";

      st.execute(query);
      c.commit();
        System.out.println("inserted");
     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
    }
public String getRate(int id)
    {
         try
    {
  //   Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
     Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

     Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String query="select * from M_Item where Item_Code="+id;
        ResultSet rs=st.executeQuery(query);
        while(rs.next())
        {
            c.close();
            return rs.getString(32);
        }

     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
         return null;

}
public String getserialno()
    {   int greater=0;
          try
    {
//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
     Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

              Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      String query="select max(SrNo) from Daily_SALE_Item";
        ResultSet rs=st.executeQuery(query);
  
        while(rs.next())
        {
            String srn=rs.getString(1);
            Integer s=Integer.parseInt(srn);
               s++;
               c.close();
               return s+"";

            
        }

     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
         return null;
}
public String getSec(String table){
try
    {

//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

    Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      
      String query="select * from M_Table where T_ID ="+table;
        ResultSet rs=st.executeQuery(query);
        if(rs.next())
        {
            String rate=rs.getString(3);
            c.close();
     
            return rate;
        }

     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
         return null;
    
}
}










