package ust;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import net.proteanit.sql.*;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSplitPane;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dialog.ModalExclusionType;
import com.toedter.calendar.JDateChooser;
public class ViewR extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	public JTextField textField;
	public static void function()
	{ //to display all the records
        try {
			/* Connecting to the database*/
			Statement stmt = null;
			Class.forName("com.mysql.jdbc.Driver");
			String host="jdbc:mysql://localhost/ustintern";
			String user="root";
			String pass="";
			Connection con = DriverManager.getConnection( host,user,pass);
			stmt = con.createStatement();
			String sql="SELECT * FROM `reminder` WHERE 1";			//Query gets all the records of reminders stored in database.
			ResultSet rs = stmt.executeQuery(sql);
			table.setModel(DbUtils.resultSetToTableModel(rs));		//display the records in table
        } catch (Exception e) {
			e.printStackTrace();}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ViewR frame = new ViewR();
					frame.setVisible(true);
					function();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewR() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 681, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setForeground(Color.BLUE);
		splitPane.setContinuousLayout(true);
		splitPane.setBounds(5, 5, 655, 410);
		  splitPane.setDividerLocation(330);
		  //splitPane.setLayout(mgr);
		contentPane.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBounds(10, 0, 643, 97);
		splitPane_1.setDividerLocation(360);
		panel.add(splitPane_1);
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setLeftComponent(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(263, 11, 86, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterTheReminder = new JLabel("Enter the reminder id you want to edit /delete");
		lblEnterTheReminder.setBounds(10, 7, 243, 28);
		panel_1.add(lblEnterTheReminder);
		
		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.setBounds(220, 37, 89, 23);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Edit");
		btnNewButton.setBounds(95, 37, 76, 22);
		panel_1.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblSearchBy = new JLabel("Search by date");
		lblSearchBy.setBounds(39, 17, 124, 14);
		panel_2.add(lblSearchBy);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(146, 11, 118, 20);
		panel_2.add(dateChooser);
		
		JButton btnNewButton_2 = new JButton("View Details");
		btnNewButton_2.addActionListener(new ActionListener() {
			//to display particular records searched by date
			public void actionPerformed(ActionEvent e) {
				Date date =   dateChooser.getDate();
				SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dmy = dmyFormat.format(date);
				try {
					//connecting to database
					Class.forName("com.mysql.jdbc.Driver");
					String host="jdbc:mysql://localhost/ustintern";
					String user="root";
					String pass="";
					Connection con = DriverManager.getConnection( host,user,pass);
					Statement stmt = con.createStatement();
				String sql="SELECT * FROM `reminder` WHERE `remin_date`='"+dmy+"'";
				ResultSet rs = stmt.executeQuery(sql);
				//displaying the records
				table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (ClassNotFoundException e1) {
					
					e1.printStackTrace();
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_2.setBounds(97, 42, 118, 23);
		panel_2.add(btnNewButton_2);
		
		JButton btnViewAllDetails = new JButton("Refresh");
		btnViewAllDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				function();
			}
		});
		btnViewAllDetails.setBounds(263, 420, 169, 23);
		contentPane.add(btnViewAllDetails);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* get the details of reminder_id searched to edit the record*/
				Statement stmt=null;
				try {
					String rid=textField.getText();
					Class.forName("com.mysql.jdbc.Driver");
					String host="jdbc:mysql://localhost/ustintern";
					String user="root";
					String pass="";
					Connection con = DriverManager.getConnection( host,user,pass);
					 stmt = con.createStatement();
					 String sql="SELECT * FROM `reminder` WHERE remin_id='"+rid+"'";
						ResultSet rs =stmt.executeQuery(sql);
						//if record exists send the data of reminder to create class to edit the componenets
						if(rs.next())
						{
						String id = rs.getString(1);
				        Date date = rs.getDate(2);
				        Time time = rs.getTime(3);
				        String name = rs.getString(4);
				        System.out.println(id);
				        
				        CreateR cc=new CreateR();
				        //call the function in another class to edit the details
						cc.edit(id,date,time,name);
						cc.setVisible(true);}
						
						else{
							JOptionPane.showMessageDialog(null,"There is no record with that reminder id");
							textField.setText(null);
						return;}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//select the record to delete
			     
				try {
					String id=textField.getText();
					Class.forName("com.mysql.jdbc.Driver");
					String host="jdbc:mysql://localhost/ustintern";
					String user="root";
					String pass="";
					Connection con = DriverManager.getConnection( host,user,pass);
					Statement stmt = con.createStatement();
				String sql="DELETE FROM `reminder` WHERE `remin_id`='"+id+"'";
				int status=stmt.executeUpdate(sql);
				System.out.println(status);
				if(status==0)
				{
					JOptionPane.showMessageDialog(null,"There is no record with that reminder id");
					textField.setText(null);
					return;
				}
				else{
					//create a dialogue box to confirm the deletion.
					
					Object[] options = { "OK", "CANCEL" };
					JOptionPane optionPane = new JOptionPane();
					int n=JOptionPane.showOptionDialog(null,"Are you sure you want to delete the reminder","Warning",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if (n == JOptionPane.NO_OPTION) {
						 return;
					}
					Object[] options1 = { "OK"};
					int n1=JOptionPane.showOptionDialog(null,"Reminder deleted successfully","Message",
							JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null,options1,options1[0]);
					if(n1== JOptionPane.OK_OPTION)
					{
						String sql1="SELECT * FROM `reminder` WHERE 1";
						ResultSet rs = stmt.executeQuery(sql1);
						table.setModel(DbUtils.resultSetToTableModel(rs));
						textField.setText(null);
					}
				
				} }catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JPanel glass = new JPanel();
        glass.setSize(450, 750);
        glass.setBackground(Color.BLUE);
        glass.setVisible(true);
		

	}
}
