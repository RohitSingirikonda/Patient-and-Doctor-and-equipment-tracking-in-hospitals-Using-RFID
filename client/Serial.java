package tracking;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JTextArea;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
public class Serial {
	private String PORT_NAME;
	private static final int TIME_OUT = 2000;
	private int DATA_RATE;
	private SerialPort serialPort;
	JTextArea area;
	String location;
public Serial(JTextArea area,String location){
	this.area= area;
	this.location = location;
}
public void setPort(String PORT_NAME){
	this.PORT_NAME = PORT_NAME;
}
public void setRate(int DATA_RATE){
	this.DATA_RATE = DATA_RATE;
}
public void initialize(){
	CommPortIdentifier portId = null;
	try{
		portId = CommPortIdentifier.getPortIdentifier(PORT_NAME);
	}catch (NoSuchPortException e){
		e.printStackTrace();
	}
	try{
		this.serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
		this.serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
		SerialPort.PARITY_NONE);
		this.serialPort.addEventListener(new MyListener(this.serialPort,area,location));
		this.serialPort.notifyOnDataAvailable(true);
	}catch (Exception e){
		e.printStackTrace();
	}
}

public synchronized void close(){
	
	if (this.serialPort != null) {
		this.serialPort.removeEventListener();
		this.serialPort.close();
	}
	
}
}
class MyListener implements SerialPortEventListener{
	private final SerialPort port;
	JTextArea area;
	String location;
	StringBuilder builder = new StringBuilder();
public MyListener(SerialPort port,JTextArea area,String location){
	super();
	this.port = port;
	this.area = area;
	this.location = location;
}
public void serialEvent(SerialPortEvent event){
	if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE){
		try{
			int available = this.port.getInputStream().available();
			byte chunk[] = new byte[available];
			this.port.getInputStream().read(chunk, 0, available);
			String input=new String(chunk);
			builder.append(input);
			if(builder.length() == 12)
				writeLog();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
public void writeLog(){
	try{
		area.append("Read tag : "+builder.toString()+"\n");
		Socket socket = new Socket("localhost",1111);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		Object req[] = {"read",location,builder.toString()};
		out.writeObject(req);
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Object res[] = (Object[])in.readObject();
		String re = (String)res[0];
		builder.delete(0,builder.length());
		area.append(re+"\n");
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
