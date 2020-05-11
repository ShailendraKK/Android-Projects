/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flyingorderticketdesktop;

/**
 *
 * @author shailendra
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;
import java.sql.Time;

 class OrderHandlerM implements Runnable
{
    String time;
    public static Object[][] Data;
private Socket socket;
private BufferedReader input;

public OrderHandlerM(Socket socket)
{
     // System.out.println("Connected 1");
    this.socket=socket;
    Thread t=new Thread(this);
    t.start();
  //   try{ t.sleep(1000);}
//  catch(Exception e){}

}

    public void run() {
      try
      {

          // DataInputStream dis=new DataInputStream   (socket.getInputStream());
              System.out.println("Connected");
          this.input=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
          String command=input.readLine();
          if(command.equals("login"))
          {
           new SigningTask(input, socket);
          }
          else if(command.equals("order"))
          {
            new MyOrderHandler(input,socket);
          }
 else if(command.equals("cancel"))
          {
     new CancelOrderHandler(input, socket);
 }
 else if(command.equals("sink"))
          {
     new SinkHandler(socket);
 }
       //   socket.close();

       }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }



      }

}

