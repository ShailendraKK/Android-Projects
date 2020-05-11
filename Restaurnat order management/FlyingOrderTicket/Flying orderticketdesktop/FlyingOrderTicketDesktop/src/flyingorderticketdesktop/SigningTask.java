/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flyingorderticketdesktop;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author shailendra
 */
class SigningTask implements Runnable{
  BufferedReader input1;
  Socket socket;
  public SigningTask(BufferedReader input1,Socket socket)
    {
      this.input1=input1;
      this.socket=socket;
      Thread t=new Thread(this);
      t.start();
  }
  public void run() {
     try
      {

          // DataInputStream dis=new DataInputStream   (socket.getInputStream());
               System.out.println("Connected to signing handler");
          String username=input1.readLine();
          String password=input1.readLine();
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

          input1.close();

       socket.close();
       System.out.println("Authentication successfull");
        //  System.out.println("Username ="+username+" Password ="+password);
       //   System.out.println("Message sent to server");
//System.out.println("Waiting for client message");
       }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }

    }

  private int authenticateuser(String username, String password) {
        try
    {
       //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 // Connection c=DriverManager.getConnection("jdbc:ucanaccess://E:/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
            Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      String query="select * from M_UserID";
        ResultSet rs=st.executeQuery(query);
       // System.out.println("After query");
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

