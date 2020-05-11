/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flyingorderticketdesktop;

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
/**
 *
 * @author shailendra
 */
public class ConnectionHandler {
 int port=6016;
 public static String ComputerName="ASEEM-PC";
    //String server_ip="10.0.0.2";
    ServerSocket server;
    public ConnectionHandler()
    {
          try
       {
           server=new ServerSocket(port);
       }
       catch(Exception e)
       {
           System.err.println(""+e);
       }
    }
     public static void main(String[] args)
    {
        ConnectionHandler m=new ConnectionHandler();
        m.handleNewConnection();

    }
      public void handleNewConnection()
     {
//         System.out.println("Waiting for order");
         while(true)
         {
             try
             {
                 Socket socket=server.accept();
                 new OrderHandlerM(socket);
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
     }

  public static String dbString()
  {
      return "jdbc:ucanaccess://E:/HOTEL_II.mdb;password=MH2014";

  }
}
