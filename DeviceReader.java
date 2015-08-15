package tracking;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextArea;
public class DeviceReader extends Thread{
	Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
public DeviceReader(Socket soc,JTextArea area){
	socket = soc;
	this.area = area;
	try{
		out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}

@Override
public void run(){
	try{
		Object input[]=(Object[])in.readObject();
        String type=(String)input[0];
		if(type.equals("read")){
			String location = (String)input[1];
			String id = (String)input[2];
			String msg = DBCon.trackLocation(id,location);
			Object res[] = {msg};
			out.writeObject(res);
		}
	}catch(Exception e){
        e.printStackTrace();
    }
}
}
