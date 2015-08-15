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
public class TagAssignment extends JFrame
{
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3;
	JButton b1,b2;
	Font f1,f2;
	JPanel p1,p2;
	JComboBox c1;
public TagAssignment(){
	setTitle("Tag Assignment");
	f1 = new Font("Courier New", 1, 18);
    p1 = new JPanel();
    l1 = new JLabel("Tag Assignment Screen");
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
    p1.setBackground(new Color(204, 110, 155));

    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(null);
    
	l2 = new JLabel("TAG ID");
	l2.setFont(f2);
	l2.setBounds(10,30,100,30);
	p2.add(l2);

	tf1 = new JTextField();
	tf1.setFont(f2);
	tf1.setBounds(110,30,100,30);
	p2.add(tf1);

	l3 = new JLabel("TAG USER");
	l3.setFont(f2);
	l3.setBounds(10,80,100,30);
	p2.add(l3);

	tf2 = new JTextField();
	tf2.setFont(f2);
	tf2.setBounds(110,80,100,30);
	p2.add(tf2);

	l4 = new JLabel("TAG TYPE");
	l4.setFont(f2);
	l4.setBounds(10,130,100,30);
	p2.add(l4);

	c1 = new JComboBox();
	c1.setFont(f2);
	c1.setBounds(110,130,100,30);
	c1.addItem("Doctor");
	c1.addItem("Nurse");
	c1.addItem("Patient");
	c1.addItem("Equipment");
	p2.add(c1);

	l5 = new JLabel("TAG LOCATION");
	l5.setFont(f2);
	l5.setBounds(10,180,100,30);
	p2.add(l5);

	tf3 = new JTextField();
	tf3.setFont(f2);
	tf3.setBounds(110,180,100,30);
	p2.add(tf3);

	b1 = new JButton("Add");
	b1.setFont(f2);
	b1.setBounds(50,230,80,30);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			add();
		}
	});
	b2 = new JButton("Exit");
	b2.setFont(f2);
	b2.setBounds(140,230,80,30);
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
	String id = tf1.getText();
	String user = tf2.getText();
	String type = c1.getSelectedItem().toString();
	String location = tf3.getText().trim();
	if(id.length() <= 0 || id == null){
		JOptionPane.showMessageDialog(this,"Tag id should not be empty");
		tf1.requestFocus();
		return;
	}
	if(user.length() <= 0 || user == null){
		JOptionPane.showMessageDialog(this,"Tag user should not be empty");
		tf2.requestFocus();
		return;
	}
	if(location.length() <= 0 || location == null){
		JOptionPane.showMessageDialog(this,"Tag location should not be empty");
		tf3.requestFocus();
		return;
	}
	try{
		if(!type.equals("Patient")){
			String msg = DBCon.tagAssign(id,user,type,location);
			if(msg.equals("success")){
				JOptionPane.showMessageDialog(this,"Tag assignment process completed");
				clear();
			}else{
				JOptionPane.showMessageDialog(this,"Error in tag assignment");
			}
		}else{
			String check = DBCon.checkPatient(user);
			if(check.equals("exists")){
				String msg = DBCon.tagAssign(id,user,type,location);
				if(msg.equals("success")){
					JOptionPane.showMessageDialog(this,"Tag assignment process completed");
					clear();
				}else{
					JOptionPane.showMessageDialog(this,"Error in tag assignment");
				}
			}else{
				JOptionPane.showMessageDialog(this,"Given patient not exists");
			}
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