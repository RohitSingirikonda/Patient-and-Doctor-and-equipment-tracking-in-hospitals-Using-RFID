package tracking;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import javax.swing.UIManager; 
import javax.swing.JOptionPane;
import java.net.ServerSocket;
import java.net.Socket;
public class RFIDServer extends JFrame{	
	DeviceReader reader;
	JPanel p1,p2,p3;
	JLabel l1;
	JButton b1,b2,b3,b4;
	JScrollPane jsp;
	JTextArea area;
	Font f1,f2;
	TagAssignment ta;
	ServerSocket server;
public void start(){
	try{
		server = new ServerSocket(1111);
		area.append("RFID Server Started");
		while(true){
			Socket socket = server.accept();
			socket.setKeepAlive(true);
			reader = new DeviceReader(socket,area);
			reader.start();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public RFIDServer(){
	setTitle("RFID SERVER TRACKING");
	f1 = new Font("Courier New", 1, 18);
    p1 = new JPanel();
    l1 = new JLabel("RFID BASED ON PERSONNEL / EQUIPMENT TRACKING IN HOSPITALS ");
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
    p1.setBackground(new Color(204, 110, 155));

    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(new BorderLayout());
    area = new JTextArea();
    area.setFont(f2);
    jsp = new JScrollPane(area);
    area.setEditable(false);
    p2.add(jsp);

	p3 = new JPanel();
	b1 = new JButton("Tag Assignment");
	b1.setFont(f2);
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			TagAssignment ta = new TagAssignment();
			ta.setVisible(true);
			ta.setSize(400,400);
			ta.clear();
		}
	});

	b2 = new JButton("Add Patient");
	b2.setFont(f2);
	p3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			AddPatient ap = new AddPatient(); 
			ap.setVisible(true);
			ap.setSize(400,400);
			ap.clear();
		}
	});

	b3 = new JButton("Tag Permission");
	b3.setFont(f2);
	p3.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			AssignPermission ap = new AssignPermission(); 
			ap.setVisible(true);
			ap.setSize(400,250);
			ap.clear();
			ap.addTag();
		}
	});

	b4 = new JButton("View User Location");
	b4.setFont(f2);
	p3.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			ViewUserLocation vul = new ViewUserLocation(); 
			vul.setVisible(true);
			vul.setSize(800,600);
			vul.clear();
			vul.readTag();
		}
	});
	
    getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
	getContentPane().add(p3, "South");
 
}
public static void main(String a[])throws Exception	{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	RFIDServer server = new RFIDServer();
	server.setVisible(true);
	server.setExtendedState(JFrame.MAXIMIZED_BOTH);
	new ServerThread(server);
}

}