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
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
public class AddPatient extends JFrame
{
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3;
	JButton b1,b2;
	Font f1,f2;
	JPanel p1,p2;
	JComboBox c1;
public AddPatient(){
	setTitle("AddPatient");
	f1 = new Font("Courier New", 1, 18);
    p1 = new JPanel();
    l1 = new JLabel("Add Patient Screen");
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
    p1.setBackground(new Color(204, 110, 155));

    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(null);
    
	l2 = new JLabel("PATIENT NAME");
	l2.setFont(f2);
	l2.setBounds(10,30,100,30);
	p2.add(l2);

	tf1 = new JTextField();
	tf1.setFont(f2);
	tf1.setBounds(110,30,100,30);
	p2.add(tf1);

	l3 = new JLabel("ILLNESS");
	l3.setFont(f2);
	l3.setBounds(10,80,100,30);
	p2.add(l3);

	tf2 = new JTextField();
	tf2.setFont(f2);
	tf2.setBounds(110,80,100,30);
	p2.add(tf2);

	l4 = new JLabel("GENDER");
	l4.setFont(f2);
	l4.setBounds(10,130,100,30);
	p2.add(l4);

	c1 = new JComboBox();
	c1.setFont(f2);
	c1.setBounds(110,130,100,30);
	c1.addItem("Male");
	c1.addItem("Female");
	p2.add(c1);

	l5 = new JLabel("ADDRESS");
	l5.setFont(f2);
	l5.setBounds(10,180,100,30);
	p2.add(l5);

	tf3 = new JTextField();
	tf3.setFont(f2);
	tf3.setBounds(110,180,100,30);
	p2.add(tf3);

	b1 = new JButton("ADD PATIENT");
	b1.setFont(f2);
	b1.setBounds(50,230,120,30);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			add();
		}
	});
	b2 = new JButton("EXIT");
	b2.setFont(f2);
	b2.setBounds(180,230,80,30);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
		}
	});
    getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
}
public void add(){
	String pname = tf1.getText();
	String illness = tf2.getText();
	String gender = c1.getSelectedItem().toString();
	String address = tf3.getText().trim();
	if(pname.length() <= 0 || pname == null){
		JOptionPane.showMessageDialog(this,"Patient name should not be empty");
		tf1.requestFocus();
		return;
	}
	if(illness.length() <= 0 || illness == null){
		JOptionPane.showMessageDialog(this,"Illness should not be empty");
		tf2.requestFocus();
		return;
	}
	if(address.length() <= 0 || address == null){
		JOptionPane.showMessageDialog(this,"Address should not be empty");
		tf3.requestFocus();
		return;
	}
	try{
		String msg = DBCon.addPatient(pname,illness,gender,address);
		if(msg.equals("success")){
			JOptionPane.showMessageDialog(this,"Patient details added");
			clear();
		}else{
			JOptionPane.showMessageDialog(this,"Error in adding patient details");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void clear(){
	tf1.setText("");
	tf2.setText("");
	tf3.setText("");
}
}