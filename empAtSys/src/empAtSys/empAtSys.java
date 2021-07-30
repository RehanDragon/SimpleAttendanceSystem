package empAtSys;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;





public class empAtSys {

	private JFrame frame;
	private JTextField textID;
	private JTextField textFieldEmpName;
	private JTextField textFieldAttendance;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					empAtSys window = new empAtSys();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public empAtSys() {
		initialize();
	}

	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 827, 582);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textID = new JTextField();
		textID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				
				
				if(evt.getKeyCode()==KeyEvent.VK_ENTER)
				{
//					this will get the  id from database
					String code = textID.getText();
					try {
						Class.forName("com.mysql.jdbc.Driver");
//						this is our local host URL through which we are getting data
//						seas was the database name that will come ,
//						con=DriverManager.getConnection("jdbc:mysql://localhost//seas","root","");
						con=DriverManager.getConnection("jdbc:mysql://localhost/seas", "root", "");
						
						
//						Prepared Statement
//						it is used to get the data from database through querries
//						records is my table name so I selected table name 
						pst= con.prepareStatement("select * from records where id = ?");
						
//						this line will check the code if it is present in database or not
						pst.setString(1,code);
						rs=pst.executeQuery();
						
//						if you entered wrong product number so it will say thagt the product code is not foud
						if(rs.next()==false) 
						
						{
//							frame is my JFrame in which whole content is in
							JOptionPane.showMessageDialog(frame, "Product code not fount");
						}
						else {
//							the data you want to fetch from the specified from the text field 
//							name is my column name whose data I want to fetch
							String nameJoDatabaseSaFetchKrnaHa=rs.getString("name");
							/*
							 * 
							 aishe treka sa sara bnta rehain ga jis jis column sa data fetch kr wana ho ga
							 * */
							
//							trim white spaces remove kr deta ha
							textFieldEmpName.setText(nameJoDatabaseSaFetchKrnaHa.trim());
							
						}
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		textID.setBounds(36, 127, 86, 20);
		frame.getContentPane().add(textID);
		textID.setColumns(10);
		
		textFieldEmpName = new JTextField();
		textFieldEmpName.setEditable(false);
		textFieldEmpName.setBounds(150, 127, 219, 20);
		frame.getContentPane().add(textFieldEmpName);
		textFieldEmpName.setColumns(10);
		
		textFieldAttendance = new JTextField();
		textFieldAttendance.setEditable(false);
		textFieldAttendance.setText("");
		textFieldAttendance.setBounds(394, 127, 86, 20);
		frame.getContentPane().add(textFieldAttendance);
		textFieldAttendance.setColumns(10);
		
		JLabel lblEmployeeId = new JLabel("Employee ID");
		lblEmployeeId.setBounds(36, 84, 86, 14);
		frame.getContentPane().add(lblEmployeeId);
		
		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setBounds(150, 84, 219, 14);
		frame.getContentPane().add(lblEmployeeName);
		
		JLabel lblAttandance = new JLabel("Attandance");
		lblAttandance.setBounds(394, 84, 86, 14);
		frame.getContentPane().add(lblAttandance);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(36, 289, 452, 179);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 452, 179);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Attendance"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(500, 46, 301, 427);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 48, 281, 287);
		panel.add(scrollPane_1);
		
		JTextArea textAreaAttandanceDisplay = new JTextArea();
		textAreaAttandanceDisplay.setEditable(false);
		scrollPane_1.setViewportView(textAreaAttandanceDisplay);
		
		JButton btnSaveRecordIn = new JButton("Save Record In File System");
		btnSaveRecordIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				 here we had done simple printing The records with builtin print function which works with the help of Exception HAndeling Try catch
				try {
					textAreaAttandanceDisplay.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSaveRecordIn.setBounds(10, 368, 281, 23);
		panel.add(btnSaveRecordIn);
		
		JButton btnAddRecord = new JButton("Add Record");
		btnAddRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
				
				
				DefaultTableModel model= new DefaultTableModel();
//				here we had done casting (DefaultTableModel)
				model=(DefaultTableModel)table.getModel();
				model.addRow(new Object[] {
						
						textID.getText(),
						textFieldEmpName.getText(),
						textFieldAttendance.getText(),
				});
				
				
				
		
				
				
				
				
				
				textID.setText("");
				textFieldEmpName.setText("");
				textFieldAttendance.setText("");
				textID.requestFocus();
			}
			
			
			
			
		});
		btnAddRecord.setBounds(36, 170, 186, 23);
		frame.getContentPane().add(btnAddRecord);
		
		JButton btnDisplayRecords = new JButton("Display Record");
		btnDisplayRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				Displaying records date and time wise
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
				   LocalDateTime now = LocalDateTime.now();
				   LocalDateTime now1 = LocalDateTime.now();
				String todays_date_and_time=dtf.format(now);
				String todays_date_and_time_1=dtf1.format(now1);
				

				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+"\n");
				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+"Date : "+todays_date_and_time);
				
				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+"\n");
				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+"Time "+todays_date_and_time_1);
				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+"\n");
				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+"\n");
				
				DefaultTableModel model= new DefaultTableModel();
//				here we had done casting (DefaultTableModel)
				model=(DefaultTableModel)table.getModel();
				textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+" Employ ID  "+"\t"+" Employ Name "+"\t"+" Attendance "+"\n");
				for(int i=0;i <= model.getColumnCount()-1;i++)
				{
//					remember Array and Loops Counting Starts From Zero so first column index will be zero
//					we had casted model values into String
					String ID = (String) model.getValueAt(/*row*/ i, /*column*/0);
					String NAME = (String) model.getValueAt(/*row*/ i, /*column*/1);
					String Attendance = (String) model.getValueAt(/*row*/ i, /*column*/2);
					
					
//					We will display our records in big TextBox
					
					
					System.out.println();
					System.out.println();
					textAreaAttandanceDisplay.setText(textAreaAttandanceDisplay.getText()+""+ID+"\t"+NAME+"\t"+Attendance+"\n");
				}
				
				
				
				

				
			}
		});
		btnDisplayRecords.setBounds(36, 204, 188, 23);
		frame.getContentPane().add(btnDisplayRecords);
		
		JButton btnNewButtonPresent = new JButton("Present");
		btnNewButtonPresent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				textFieldAttendance.setText("present");
			}
		});
		btnNewButtonPresent.setBounds(391, 170, 89, 23);
		frame.getContentPane().add(btnNewButtonPresent);
		
		JButton btnAbsent = new JButton("Absent");
		btnAbsent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textFieldAttendance.setText("absent");
			}
		});
		btnAbsent.setBounds(391, 206, 89, 23);
		frame.getContentPane().add(btnAbsent);
		
		JLabel lblSimpleEmployeeAttandance = new JLabel("ATTENDANCE  SYSTEM");
		lblSimpleEmployeeAttandance.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSimpleEmployeeAttandance.setBounds(29, 35, 501, 14);
		frame.getContentPane().add(lblSimpleEmployeeAttandance);
		
		JButton btnClearAllFields = new JButton("Clear All Fields");
		btnClearAllFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				table.setModel(new DefaultTableModel(null,new String[]{"ID", "Name", "Attendance"}));
				textAreaAttandanceDisplay.setText("");
			}
		});
		btnClearAllFields.setBounds(36, 248, 444, 23);
		frame.getContentPane().add(btnClearAllFields);
	}
}
