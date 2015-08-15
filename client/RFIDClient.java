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
public class RFIDClient extends JFrame{	
	JPanel p1,p2;
	JLabel l1;
	JScrollPane jsp;
	JTextArea area;
	Font f1,f2;
	String location;
public RFIDClient(String location){
	this.location = location;
	setTitle("RFID CLIENT TRACKING");
	f1 = new Font("Courier New", 1, 18);
    p1 = new JPanel();
    l1 = new JLabel("RFID BASED ON PERSONNEL / EQUIPMENT TRACKING IN HOSPITALS");
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

	getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
	
	Serial serial = new Serial(area,location);
	serial.setPort("COM1");
	serial.setRate(9600);
	serial.initialize();
}
public static void main(String a[])throws Exception	{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	String loc = JOptionPane.showInputDialog(null,"Enter Reader Location");
	if(loc != null){
		RFIDClient client = new RFIDClient(loc);
		client.setVisible(true);
		client.setSize(600,400);
	}
}

}