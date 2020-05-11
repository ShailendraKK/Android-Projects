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


class CancelOrderHandler implements Runnable{
    BufferedReader input;
    Socket socket;
    public CancelOrderHandler(BufferedReader input,Socket socket)
    {
        this.socket=socket;
        this.input=input;
         Thread t=new Thread(this);
    t.start();
    }
        public void run() {
            try
      {

          // DataInputStream dis=new DataInputStream   (socket.getInputStream());
             System.out.println("Connected to Cancel Order Handler");
  String recordtype=input.readLine();
          String no=input.readLine();
  java.sql.Date dt=new java.sql.Date(Calendar.getInstance().getTime().getTime());
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  String date=sdf.format(dt).trim();




             String id=input.readLine();
            String name=input.readLine();
            String quantity=input.readLine();
            String Tableno=input.readLine();
           String tableno=Tableno;
            String username=input.readLine();
            String ItemType=getItemType(id);
            String OrderName="";
if(ItemType.equals("B") || ItemType.equals("O"))

        {
OrderName = "BOT";
        }
 else if(ItemType.equals("F"))
  {
       OrderName = "KOT";
        }
 else if(ItemType.equals("S"))
  {

  OrderName = "SOT";
  }

int recordno=Integer.parseInt(no);
   int  orderno=getorderno("A",OrderName,date);
 
            insertintoDailyOrderTicket(id, name, quantity, Tableno, username,recordno,orderno,date);
                   insertintodb(id,name,quantity,Tableno,username,recordno,date);
getTotalAmount(tableno, id, recordno);
System.out.println("Order Cancellation successfull");

   
  input.close();
           socket.close();
          }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }


            // throw new UnsupportedOperationException("Not supported yet.");
        }
        
  public void insertintodb(String id,String name,String quantity,String table_no,String username,int recordno,String date)
    {
         try
    {
   //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
//        Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
             Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          String rate=getRate(Integer.parseInt(id));
        double r=Double.parseDouble(rate);
        double amt=r*(Integer.parseInt(quantity));
       String amout=amt+"";
       amt=amt*(-1);
 //      String srn=getserialno();
   //   String sec=getSec(table_no);


    //  String query="insert into Daily_SALE_Item(RecordNo,ItemCode,Rate,Qty,Amount,SrNo,Sec_ID) values(1,"+id+","+rate+","+quantity+","+amout+","+srn+","+sec+")";
   String query="insert into Daily_SALE_Item(RecordNo,ItemCode,Rate,Qty,Amount,SrNo,Date,Sec_ID) values(?,?,?,?,?,?,?,?)";
  PreparedStatement ps=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
 ps.setInt(1, recordno);
 ps.setInt(2, Integer.parseInt(id));
 ps.setDouble(3, Double.parseDouble(rate));
 ps.setInt(4, Integer.parseInt(quantity));
 ps.setDouble(5, amt);
 ps.setInt(6,Integer.parseInt(getserialnosaleitem()));
 ps.setDate(7,java.sql.Date.valueOf(date));
 ps.setInt(8, Integer.parseInt(getSec(table_no)));
  ps.executeUpdate();
 c.commit();
    //    System.out.println("inserted in dsi");

/*      st.execute(query);
      c.commit();
    //    System.out.println("inserted");*/
     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
    }
          public void insertintoDailyOrderTicket(String id,String name,String quantity,String table_no,String username,int recordno,int orderno,String date)
    {
         try
    {
   //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
      //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
           //  Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    //      String rate=getRate(Integer.parseInt(id));
     //   double r=Double.parseDouble(rate);
      //  double amt=r*(Integer.parseInt(quantity));
       //String amout=amt+"";
      // String srn=getserialno();
      //String sec=getSec(table_no);
  java.sql.Date dt=new java.sql.Date(Calendar.getInstance().getTime().getTime());

 // SimpleDateFormat
  SimpleDateFormat stf=new SimpleDateFormat("hh:mm:ss");
String query="insert into Daily_OrderTicket(Date,Time,RecordNo,OrderNo,OrderType,OrderName,ItemCode,Rate,Qty,Amount,ReasonCode,SrNo,UserID,Machine,Sec_ID,Order_No_Ref,KPG) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//,"+quantity+","+amout+","+srn+","+sec+")";
// String date=sdf.format(dt).trim();
String ItemType=getItemType(id);
String OrderName=null;

       String rate=getRate(Integer.parseInt(id));
        double r=Double.parseDouble(rate);
        double amt=r*(Integer.parseInt(quantity));
       String amout=amt+"";
amt=amt*(-1);
if(ItemType.equals("B") || ItemType.equals("O"))

        {
OrderName = "BOT";
        }
 else if(ItemType.equals("F"))
  {
       OrderName = "KOT";
        }
 else if(ItemType.equals("S"))
  {

  OrderName = "SOT";
  }



Time t=new Time(Calendar.getInstance().getTime().getTime());
 String time=stf.format(t).trim();
//System.out.println(date);
//System.out.println(time);
//System.out.println("Time format" + Time.valueOf(time));

//String ordertype=getItemType(id);
  PreparedStatement ps=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
  ps.setDate(1,java.sql.Date.valueOf(date));
 ps.setTime(2,Time.valueOf(time));
  ps.setInt(3, recordno);
  ps.setInt(4, orderno);
  ps.setString(5, "C");
  ps.setString(6,OrderName);
  ps.setInt(7, Integer.parseInt(id));
  ps.setDouble(8, r);
  ps.setInt(9,Integer.parseInt(quantity));
  ps.setDouble(10, amt);
  ps.setInt(11,0);
  ps.setInt(12, Integer.parseInt(getserialnodot()));
  ps.setInt(13,1);
  ps.setString(14, ConnectionHandler.ComputerName);
  ps.setInt(15,Integer.parseInt(getSec(table_no)));
  ps.setInt(16, orderno);
  ps.setInt(17, 0);
  // String query="insert into Daily_SALE_Item(RecordNo,ItemCode,Rate,Qty,Amount,SrNo,Sec_ID) values(1,"+id+","+rate+","+quantity+","+amout+","+srn+","+sec+")";
// String query="insert into Daily_OrderTicket(Date,Time) values('"+date+"','"+time+")";//,"+quantity+","+amout+","+srn+","+sec+")";
//String query="insert into Daily_OrderTicket(Date) values(?)";//,"+quantity+","+amout+","+srn+","+sec+")";

 //System.out.println(query);
  //    st.execute(query);
 ps.executeUpdate();
 c.commit();
      //  System.out.println("inserted in dot");
     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
    }
          /*
            public void insertintoDailySaleV(String table_no,int recordno,String date)
    {
         try
    {
   //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
        Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");

           //  Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    //      String rate=getRate(Integer.parseInt(id));
     //   double r=Double.parseDouble(rate);
      //  double amt=r*(Integer.parseInt(quantity));
       //String amout=amt+"";
      // String srn=getserialno();
      //String sec=getSec(table_no);
  java.sql.Date dt=new java.sql.Date(Calendar.getInstance().getTime().getTime());

 // SimpleDateFormat
  SimpleDateFormat stf=new SimpleDateFormat("hh:mm:ss");
String query="insert into Daily_SALEV(RecordNo,BillNo,Date,Time,Sec_ID,Tab) values(?,?,?,?,?,?)"; //,"+quantity+","+amout+","+srn+","+sec+")";
// String date=sdf.format(dt).trim();



Time t=new Time(Calendar.getInstance().getTime().getTime());
 String time=stf.format(t).trim();
//System.out.println(date);
//System.out.println(time);
//System.out.println("Time format" + Time.valueOf(time));

//String ordertype=getItemType(id);
  PreparedStatement ps=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
  ps.setDate(3,java.sql.Date.valueOf(date));
 ps.setTime(4,Time.valueOf(time));
  ps.setInt(1, recordno);
  ps.setInt(2,recordno);
  ps.setInt(5,Integer.parseInt(getSec(table_no)));
  ps.setString(6,getTN(table_no));

  // String query="insert into Daily_SALE_Item(RecordNo,ItemCode,Rate,Qty,Amount,SrNo,Sec_ID) values(1,"+id+","+rate+","+quantity+","+amout+","+srn+","+sec+")";
// String query="insert into Daily_OrderTicket(Date,Time) values('"+date+"','"+time+")";//,"+quantity+","+amout+","+srn+","+sec+")";
//String query="insert into Daily_OrderTicket(Date) values(?)";//,"+quantity+","+amout+","+srn+","+sec+")";

 //System.out.println(query);
  //    st.execute(query);
 ps.executeUpdate();
 c.commit();
 getTotalAmount(table_no, getSec(table_no), recordno);
      //  System.out.println("inserted in dot");
     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
    }




            */
public String getRate(int id)
    {
         try
    {
  //   Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    // Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
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
public String getserialnosaleitem()
    {   int greater=0;
          try
    {
//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    // Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
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
public String getserialnodot()
    {   int greater=0;
          try
    {
//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    // Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
              Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      String query="select max(SrNo) from Daily_OrderTicket";
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
public int getorderno(String Ordertype,String OrderName,String Date)
    {
          try
    {
//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    // Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
    Connection c = DriverManager.getConnection(ConnectionHandler.dbString());

      String query="select max(OrderNo) as maxoforderno from Daily_OrderTicket group by OrderType,OrderName,Machine,Date having ((OrderType=? and OrderName=?) and (Machine=? and Date=?))";
      PreparedStatement st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
     st.setString(1, Ordertype);
     st.setString(2, OrderName);
     st.setString(3, ConnectionHandler.ComputerName);
     st.setDate(4,java.sql.Date.valueOf(Date));
      ResultSet rs=st.executeQuery();

        while(rs.next())
        {
            int recordno=rs.getInt(1);
            recordno++;
               c.close();
         //      return s+"";
                return recordno;

        }

     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
         return 1;
}

public int getTotalAmount(String table_no,String sec_id,int record_no)
    {
          try
    {
              int secrate_id=Integer.parseInt(sec_id);
              String tab_n="";


//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    // Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
        Connection c = DriverManager.getConnection(ConnectionHandler.dbString());

      String query="SELECT M_Table.SaleRate FROM Daily_SALEV INNER JOIN M_Table ON Daily_SALEV.Tab = M_Table.TN WHERE Daily_SALEV.RecordNo=?";
      PreparedStatement st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
     st.setInt(1, record_no);

      ResultSet rs=st.executeQuery();

        while(rs.next())
        {
            secrate_id=rs.getInt(1);
         //      c.close();
         //      return s+"";


        }
   // System.out.println("Secrate id ="+secrate_id);
      query="SELECT M_Table.SaleRate,M_Table.TN FROM Daily_SALEV INNER JOIN M_Table ON Daily_SALEV.Tab = M_Table.TN WHERE Daily_SALEV.RecordNo=?";
      st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      st.setInt(1, record_no);

      rs=st.executeQuery();

        while(rs.next())
        {
            secrate_id=rs.getInt(1);
           // tab_n=rs.getString(2);
         //      c.close();
         //      return s+"";


        }
    //     System.out.println("Secrate id ="+secrate_id);
            query="SELECT M_Table.TN FROM Daily_SALEV INNER JOIN M_Table ON Daily_SALEV.Tab = M_Table.TN WHERE Daily_SALEV.RecordNo=?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            st.setInt(1, record_no);

      rs=st.executeQuery();

        while(rs.next())
        {
         //   secrate_id=rs.getInt(1);
            tab_n=rs.getString(1);
         //      c.close();
         //      return s+"";


        }
    //     System.out.println("tabn ="+tab_n);
query="SELECT Sum(Daily_SALE_Item.Amount) AS SumOfAmount From Daily_SALE_Item WHERE Daily_SALE_Item.RecordNo=?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

st.setInt(1, record_no);
int tba=0;
      rs=st.executeQuery();

        while(rs.next())
        {
        tba=rs.getInt(1);
            //      c.close();
         //      return s+"";


        }
query="SELECT * From  M_Section where S_ID=?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

st.setInt(1, secrate_id);
int servicecharge_per=0;
int service_tax_per=0;
int vattax_per=0;
      rs=st.executeQuery();

        while(rs.next())
        {
        servicecharge_per=rs.getInt("ServiceCharge%");
        service_tax_per=rs.getInt("ServiceTax%");
        vattax_per=rs.getInt("VAT%");
        //      c.close();
         //      return s+"";


        }
      double SrCh=(tba*service_tax_per)/100;
      int srch=(int)SrCh;
      if(((SrCh -srch) * 100) >= 50)
              {
                   SrCh = srch + 1;
        }
      else
    {
   SrCh = srch;
    }

      double STax = (tba * service_tax_per) / 100;
    int stax=(int)STax;
      if(((STax - stax) * 100) >= 50)
      {
   STax = stax+ 1;
        }
 else
      {
   STax = stax;
        }
if(tab_n.trim().length()>=6)
{
    if(tab_n.trim().toLowerCase().substring(0,5).equals("parcel"))
    {

        STax=0;
    }
}


double VAT = (tba * vattax_per) / 100;
int vat=(int)VAT;
if (((VAT - vat) * 100) >= 50)
        {

   VAT = vat + 1;
        }
 else
  VAT = vat;

    double BillRoundOff = tba + SrCh + VAT + STax;
    int billround=(int)BillRoundOff;
     if (((BillRoundOff - billround) * 100 )>= 50)
     {
       BillRoundOff = (int)(tba+ SrCh + VAT + STax) + 1;
        }
          else
    {
    BillRoundOff = (int)(tba + SrCh + VAT + STax);
    }



double SalesBillAmount = BillRoundOff;


query="UPDATE Daily_SaleV SET Daily_SaleV.Net_Amt = ? WHERE Daily_SaleV.RecordNo = ?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

st.setDouble(1,SalesBillAmount);
      st.setInt(2,record_no);
  st.executeUpdate();

  query="UPDATE Daily_SaleV SET Daily_SaleV.ServiceCharge = ? WHERE Daily_SaleV.RecordNo=?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      st.setDouble(1,SrCh);
      st.setInt(2,record_no);
  st.executeUpdate();


  query="UPDATE Daily_SaleV SET Daily_SaleV.VAT = ? WHERE Daily_SaleV.RecordNo = ?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      st.setDouble(1,VAT);
      st.setInt(2,record_no);
  st.executeUpdate();

query="UPDATE Daily_SaleV SET Daily_SaleV.Total_Amt = ? WHERE Daily_SaleV.RecordNo = ?";
 st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

st.setDouble(1,SalesBillAmount);
      st.setInt(2,record_no);
  st.executeUpdate();
c.close();
          }
    catch(Exception e)
    {
        e.printStackTrace();

    }
         return 0;
}

public String getSec(String table){
try
    {

//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
    //Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
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
    public String getTN(String table){
try
    {

//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
  //  Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
    Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      String query="select * from M_Table where T_ID ="+table;
        ResultSet rs=st.executeQuery(query);
        if(rs.next())
        {
            String rate=rs.getString(4);
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
        private String getItemType(String id) {
          try
    {

//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
 //   Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");
 Connection c = DriverManager.getConnection(ConnectionHandler.dbString());
    Statement st=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

      String query="select ItemType from M_Item where Item_Code ="+id;
        ResultSet rs=st.executeQuery(query);
        if(rs.next())
        {
            String rate=rs.getString(1);
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
/*
    private int getrecordno(String Ordertype, String Date,String name) {
        try
    {
//     Connection c=DriverManager.getConnection("jdbc:ucanaccess://F:/flying order ticket/FlyingOrderTicketDesktop/HOTEL_II.mdb;password=MH2014");
     Connection c=DriverManager.getConnection("jdbc:ucanaccess://D:/Softmakeii/Data/HOTEL_II.mdb;password=MH2014");


      String query="select max(RecordNo) as maxoforderno from Daily_OrderTicket group by OrderType,OrderName,Machine,Date having Daily_OrderTicket.OrderType=? and Daily_OrderTicket.Machine=? and Daily_OrderTicket.Date=?";
      PreparedStatement st=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
     st.setString(1, Ordertype);
     st.setString(2,name);
    // st.setString(3, ConnectionHandler.ComputerName);
     st.setDate(3,java.sql.Date.valueOf(Date));
      ResultSet rs=st.executeQuery();

        while(rs.next())
        {
            int recordno=rs.getInt(1);
               c.close();
         //      return s+"";
                return recordno;

        }

     }
    catch(Exception e)
    {
        e.printStackTrace();

    }
         return 0;
    }
*/
}
