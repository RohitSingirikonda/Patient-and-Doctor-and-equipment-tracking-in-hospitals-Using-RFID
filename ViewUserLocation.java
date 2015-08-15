package tracking;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
public class ViewUserLocation extends JFrame
{
	JLabel l1;
	JComboBox c1;
	JButton b1;
	JPanel p1,p2;
	Font f1;
	DefaultTableModel dtm;
	JTable table;
	JScrollPane jsp;
	
public ViewUserLocation(){
	super("View User Location");
	
	p1 = new JPanel();
	p1.setBackground(Color.white);
	f1 = new Font("Courier New",Font.PLAIN,14);
	
	l1 = new JLabel("Tag ID");
	l1.setFont(f1);
	p1.add(l1);
	l1.setFont(f1);

	c1 = new JComboBox();
	c1.setFont(f1);
	c1.setFont(f1);
	p1.add(c1);

	b1 = new JButton("View");
	b1.setFont(f1);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try{
				String id = c1.getSelectedItem().toString().trim();
				String str[] = DBCon.getTracks(id);
				clear();
				for(String s1 : str){
					String row[] = s1.split("#");
					dtm.addRow(row);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	});
	

	p2 = new JPanel();
	p2.setBackground(Color.white);
	p2.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	table.setFont(f1);
	table.getTableHeader().setFont(new Font("Courier New",Font.BOLD,13));
	dtm.addColumn("Tag ID");
	dtm.addColumn("Tag User");
	dtm.addColumn("Tag Type");
	dtm.addColumn("Location");
	dtm.addColumn("Time");
	dtm.addColumn("Status");

	jsp = new JScrollPane(table);
	jsp.getViewport().setBackground(Color.white);
	p2.add(jsp,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);
}
public void clear(){
	for(int i=dtm.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
}
public void readTag(){
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
}