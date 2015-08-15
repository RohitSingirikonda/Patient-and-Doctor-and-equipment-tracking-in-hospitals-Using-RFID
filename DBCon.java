package tracking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBCon{
	private static Connection con;
public static Connection getCon()throws Exception{
	Class.forName("com.mysql.jdbc.Driver");
	con = DriverManager.getConnection("jdbc:mysql://localhost/rfid_hospital_tracking","root","root");
	return con;
}
public static String trackLocation(String id,String location)throws Exception{
	String msg = "no";
	java.util.Date date = new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(date.getTime()); 
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select tag_id from tag_permission where tag_id='"+id+"' and tag_location='"+location+"'");
	if(rs.next()){
        stmt = con.createStatement();
		rs=stmt.executeQuery("select tag_user,tag_type from addusers where tag_id='"+id+"'");
		while(rs.next()){
			String user = rs.getString(1);
			String type = rs.getString(2);
			PreparedStatement stat=con.prepareStatement("insert into tracklocation values(?,?,?,?,?,?)");
			stat.setString(1,id);
			stat.setString(2,user);
			stat.setString(3,type);
			stat.setString(4,location);
			stat.setTimestamp(5,time);
			stat.setString(6,"Valid Location");
			int i = stat.executeUpdate();
			if(i > 0)
				msg = "User track at valid location";
		}
	}else{
		stmt = con.createStatement();
		rs=stmt.executeQuery("select tag_user,tag_type from addusers where tag_id='"+id+"'");
		while(rs.next()){
			String user = rs.getString(1);
			String type = rs.getString(2);
			PreparedStatement stat=con.prepareStatement("insert into tracklocation values(?,?,?,?,?,?)");
			stat.setString(1,id);
			stat.setString(2,user);
			stat.setString(3,type);
			stat.setString(4,location);
			stat.setTimestamp(5,time);
			stat.setString(6,"Invalid Location");
			int i = stat.executeUpdate();
			if(i > 0)
				msg = "User track at invalid location";
		}
	}
	return msg;
}
public static String[] getTracks(String id)throws Exception{
	StringBuilder sb = new StringBuilder();
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select * from tracklocation where tag_id='"+id+"'");
	while(rs.next()){
		String tid = rs.getString(1);
		String user = rs.getString(2);
		String type = rs.getString(3);
		String loc = rs.getString(4);
		String time = rs.getString(5);
		String status = rs.getString(6);
		sb.append(tid+"#"+user+"#"+type+"#"+loc+"#"+time+"#"+status+"\n");
	}
	if(sb.length() > 0)
		sb.deleteCharAt(sb.length()-1);
	return sb.toString().split("\n");
}
public static String tagAssign(String id,String user,String type,String location)throws Exception{
	String msg="fail";
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select tag_id from addusers where tag_id='"+id+"'");
    if(rs.next()){
        msg = "Given tag id already exist";
    }else{
		PreparedStatement stat=con.prepareStatement("insert into addusers values(?,?,?,?)");
		stat.setString(1,id);
		stat.setString(2,user);
		stat.setString(3,type);
		stat.setString(4,location);
		int i=stat.executeUpdate();
		if(i > 0)
			msg = "success";
	}
    return msg;
}
public static String[] getTags()throws Exception{
	StringBuilder sb = new StringBuilder();
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select tag_id from addusers");
	while(rs.next()){
		sb.append(rs.getString(1)+",");
	}
	if(sb.length() > 0)
		sb.deleteCharAt(sb.length()-1);
	return sb.toString().split(",");
}
public static String checkPatient(String user)throws Exception{
	String msg = "no";
	con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select pname from addpatient where pname='"+user+"'");
	if(rs.next()){
		msg = "exists";
	}
	return msg;
}
public static String addPatient(String pname,String illness,String gender,String address)throws Exception{
	String msg="fail";
    con = getCon();
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("select pname from addpatient where pname='"+pname+"'");
    if(rs.next()){
        msg = "Given tag id already exist";
    }else{
		PreparedStatement stat=con.prepareStatement("insert into addpatient values(?,?,?,?)");
		stat.setString(1,pname);
		stat.setString(2,illness);
		stat.setString(3,gender);
		stat.setString(4,address);
		int i=stat.executeUpdate();
		if(i > 0)
			msg = "success";
	}
    return msg;
}
public static String assignPermission(String tag,String location)throws Exception{
	String msg="fail";
    con = getCon();
    PreparedStatement stat=con.prepareStatement("insert into tag_permission values(?,?)");
	stat.setString(1,tag);
	stat.setString(2,location);
	int i=stat.executeUpdate();
	if(i > 0)
		msg = "success";
	return msg;
}
}
