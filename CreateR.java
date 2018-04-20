package ust;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateR extends JFrame {

	private JPanel contentPane;
	private static JTextPane textPane;
	private static JDateChooser dateChooser;
	private static JSpinner spinner;
	private static JSpinner spinner_1;
	private static JTextField textField;
	/**
	 * Launch the application.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	
	//function called to edit the details of reminder
	public void edit(String id, Date date, Time time, String name) throws ClassNotFoundException, SQLException {
	    dateChooser.setDate(date);
	    DateFormat df = new SimpleDateFormat("HH:mm");
	    String times = df.format(time);
	    
	    //type casting time into integer by splitting to display them in spinners
	    String[] part=times.split(":", 2);
	    String hh= part[0];
	    int hour = Integer.parseInt(hh);
	    String mm=part[1];
	    int minute = Integer.parseInt(mm);
	    
	    //display the existing details to edit
	    
		textPane.setText(name);
		spinner.setValue(hour);
		spinner_1.setValue(minute);
		textField.setText(id);
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateR frame = new CreateR();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateR() {
		setTitle("Create Reminder");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 455, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		String title = "Time";
		Border border = BorderFactory.createTitledBorder(title);
		panel_1.setBorder(border);
		panel_1.setBounds(72, 68, 292, 111);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Hours(HH)");
		lblNewLabel_1.setBounds(23, 29, 58, 14);
		panel_1.add(lblNewLabel_1);
				
		 spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 23, 1));
		spinner.setBounds(140, 20, 45, 27);
		panel_1.add(spinner);
		
		JLabel lblNewLabel_2 = new JLabel("Minutes(MM)");
		lblNewLabel_2.setBounds(23, 73, 72, 14);
		panel_1.add(lblNewLabel_2);
		
		 spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		spinner_1.setBounds(140, 67, 45, 27);
		panel_1.add(spinner_1);
		
		JLabel lblNewLabel = new JLabel("Date");
		lblNewLabel.setBounds(72, 43, 46, 14);
		panel.add(lblNewLabel);
		
		 dateChooser = new JDateChooser();
		dateChooser.setBounds(183, 37, 91, 20);
		panel.add(dateChooser);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		String title1 = "Reminder";
		Border borders = BorderFactory.createTitledBorder(title1);
		panel_2.setBorder(borders);
		panel_2.setBounds(72, 190, 292, 83);
		panel.add(panel_2);
		
		textPane = new JTextPane();
		textPane.setBounds(10, 23, 272, 49);
		panel_2.add(textPane);
		
		JButton btnSaveReminder = new JButton("Save Reminder");
		btnSaveReminder.addActionListener(new ActionListener() {
			
			// create the reminder and save in data base
			
			public void actionPerformed(ActionEvent e) {
				String id=textField.getText();
				Date date =   dateChooser.getDate();			
				int hh = (Integer) spinner.getValue();
				int mm =(Integer) spinner_1.getValue();
				String str = String.format("%02d:%02d",hh, mm);		
				String name= textPane.getText();
				//if any of the fields is  null return from this function
				if(date==null ||str==null||name==null){
					JOptionPane.showMessageDialog(null,"All the fields are mandatory");
					return;
				}
				SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dmy = dmyFormat.format(date);
				Date currdate = new Date() ;
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
				   String presenttime=dateFormat.format(currdate);
				   String[] parts=presenttime.split(":",2);
				   spinner.setValue(Integer.parseInt(parts[0]));
				   spinner_1.setValue(Integer.parseInt(parts[1]));
				   String presentdate=dmyFormat.format(currdate);
				   
				 //check if date entered is greater than or equal to todays
				   try {
					if ((dmyFormat.parse(presentdate).after(dmyFormat.parse(dmy)))) {
						   JOptionPane.showMessageDialog(null,"Please enter date greater than or equal\t"+presentdate);
						   dateChooser.setDate(null);
						   return;
						}
					   else{
						   if((dmyFormat.parse(presentdate).equals(dmyFormat.parse(dmy))))
						   { 
							   if(!(dateFormat.parse(presenttime).before(dateFormat.parse(str))))
							{ 
							   JOptionPane.showMessageDialog(null,"Please enter time grater than"+presenttime);
							   return;
								
							}
							} 
					try {
						Statement stmt = null;
						Class.forName("com.mysql.jdbc.Driver");
						String host="jdbc:mysql://localhost/ustintern";
						String user="root";
						String pass="";
						Connection con = DriverManager.getConnection( host,user,pass);
						stmt = con.createStatement();
						
						//inserting into database
						
						String sql ="INSERT INTO `reminder` (`remin_id`,`remin_date`, `remin_time`, `remin_name`)"
								+ " VALUES ('"+id+"','"+dmy+"','"+str+"', '"+name+"');";
						try {
							int status=stmt.executeUpdate(sql);
							if(status==1)
							{
								JOptionPane.showMessageDialog(null,"Saved Successfully");
							}
						} catch (SQLIntegrityConstraintViolationException e1) {
							 if (e1 instanceof SQLIntegrityConstraintViolationException) {
								 JOptionPane.showMessageDialog(null,"Reminder "+id+" already exits");
							    }
							 else{
								 JOptionPane.showMessageDialog(null,"Failed to save");
							 }
							}
						
					} catch (ClassNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}}
				} catch (HeadlessException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnSaveReminder.setBounds(118, 307, 133, 23);
		panel.add(btnSaveReminder);
		
		JButton btnNewButton = new JButton("update");
		
		//update the selected reminder 
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=textField.getText();
				Date date1 =   dateChooser.getDate();   
						int hh1 = (Integer) spinner.getValue();
						int mm1 =(Integer) spinner_1.getValue();
						String str = String.format("%02d:%02d",hh1, mm1);		
						String name1= textPane.getText();
						if(date1==null ||str==null||name1==null){
							JOptionPane.showMessageDialog(null,"All the fields are mandatory");
							return;
						}
						SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
						String dmy = dmyFormat.format(date1); 
						
						Date currdate = new Date() ;
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
						   String presenttime=dateFormat.format(currdate);
						   String[] parts=presenttime.split(":",2);
						   spinner.setValue(Integer.parseInt(parts[0]));
						   spinner_1.setValue(Integer.parseInt(parts[1]));
						   String presentdate=dmyFormat.format(currdate);
						   try {
							if ((dmyFormat.parse(presentdate).after(dmyFormat.parse(dmy)))) {
								   JOptionPane.showMessageDialog(null,"Please enter date greater than or equal\t"+presentdate);
								   dateChooser.setDate(null);
								   return;
								}
							   else{
								   
								   //if date entered is today's date ,then compare the time entered with present time.
								   if((dmyFormat.parse(presentdate).equals(dmyFormat.parse(dmy))))
								   { 
									   if(!(dateFormat.parse(presenttime).before(dateFormat.parse(str))))
									{ 
									   JOptionPane.showMessageDialog(null,"Please enter time greater than"+presenttime);
									   return;
										
									}
									   }				
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String host="jdbc:mysql://localhost/ustintern";
					String user="root";
					String pass="";
					Connection con = DriverManager.getConnection( host,user,pass);
					Statement stmt = con.createStatement();
				//update into database
				String sql="UPDATE `reminder` SET `remin_id`='"+id+"',`remin_date`='"+dmy+"',"
				+ "`remin_time`='"+str+"',`remin_name`='"+name1+"' WHERE remin_id='"+id+"'";
					int status=stmt.executeUpdate(sql);
					if(status==0)
					{
						JOptionPane.showMessageDialog(null,"There is no reminder with that id");
						return;
					}
					else{JOptionPane.showMessageDialog(null,"Updated succsfully");}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 }
				 }catch (HeadlessException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(274, 307, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblReminderid = new JLabel("Reminder_id");
		lblReminderid.setBounds(72, 18, 73, 14);
		panel.add(lblReminderid);
		
		textField = new JTextField();
		textField.setBounds(183, 15, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
	}
		

	
}
