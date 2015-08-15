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
public class AssignPermission extends JFrame
{
	JLabel l1,l2,l3;
	JTextField tf1;
	JButton b1,b2;
	Font f1,f2;
	JPanel p1,p2;
	JComboBox c1;
public AssignPermission(){
	setTitle("Assign Permission");
	f1 = new Font("Courier New", 1, 18);
    p1 = new JPanel();
    l1 = new JLabel("Assign Permission Screen");
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

	c1 = new JComboBox();
	c1.setFont(f2);
	c1.setBounds(110,30,150,30);
	p2.add(c1);

	l3 = new JLabel("LOCATION");
	l3.setFont(f2);
	l3.setBounds(10,80,100,30);
	p2.add(l3);

	tf1 = new JTextField();
	tf1.setFont(f2);
	tf1.setBounds(110,80,150,30);
	p2.add(tf1);

	b1 = new JButton("ASSIGN");
	b1.setFont(f2);
	b1.setBounds(50,130,80,30);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			add();
		}
	});
	b2 = new JButton("EXIT");
	b2.setFont(f2);
	b2.setBounds(140,130,80,30);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
		}
	});
    getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
}
public void addTag(){
	try{
		c1.removeAllItems();
		String tag[] = DBCon.getTags();
		for(String str : tag){
			c1.addItem(str);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void add(){
	String tag = c1.getSelectedItem().toString();
	String location = tf1.getText().trim();
	if(location.length() <= 0 || location == null){
		JOptionPane.showMessageDialog(this,"Location should not be empty");
		tf1.requestFocus();
		return;
	}
	try{
		String msg = DBCon.assignPermission(tag,location);
		if(msg.equals("success")){
			JOptionPane.showMessageDialog(this,"Permission assignment process completed");
			clear();
		}else{
			JOptionPane.showMessageDialog(this,"Error in permission assignment");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void clear(){
	tf1.setText("");
}
}