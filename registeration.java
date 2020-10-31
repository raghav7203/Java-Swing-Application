import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import javax.swing.table.DefaultTableModel;


public class registeration{
	static DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Course"}, 0);
	public static void main(String args[]) {
			
		JFrame frame=new JFrame();
		JLabel label=new JLabel("Student Registeration");
		
		JTable table=new JTable();
		
		
		label.setBounds(350, 50, 500, 50);
		
		label.setFont(new Font(label.getFont().getName(), 
		label.getFont().getStyle(), 40));
		
		JLabel name=new JLabel("Name");
		name.setBounds(50, 200, 100, 50);
		name.setFont(new Font(name.getFont().getName(),
		name.getFont().getStyle(), 25));
		
		JTextField nameTF=new JTextField();
		nameTF.setBounds(175, 210, 270, 35);
		
		JLabel phone=new JLabel("Phone");
		phone.setBounds(50, 290, 100, 50);
		phone.setFont(new Font(phone.getFont().getName(),
		phone.getFont().getStyle(), 25));
		
		JTextField phoneTF=new JTextField();
		phoneTF.setBounds(175, 300, 270, 35);
		
		
		JLabel course=new JLabel("Course");
		course.setBounds(50, 380, 100, 50);
		course.setFont(new Font(phone.getFont().getName(),
		course.getFont().getStyle(), 25));
		
		JTextField courseTF=new JTextField();
		courseTF.setBounds(175, 390, 270, 35);
		
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	int selectedIndex = table.getSelectedRow();
		    	
		    	nameTF.setText(model.getValueAt(selectedIndex, 1).toString());
		    	phoneTF.setText(model.getValueAt(selectedIndex, 2).toString());
		    	courseTF.setText(model.getValueAt(selectedIndex, 3).toString());
		    }
		});
		
		
		JButton add=new JButton("Add");
		add.setBounds(50, 470, 190, 40);
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String valueName=nameTF.getText();
				String valuePhone=phoneTF.getText();
				String valueCourse=courseTF.getText();
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentRegistration","root","");
					
					String strQuery = "insert into student(name,phone,course) VALUES (?,?,?)";
			        PreparedStatement statement = con.prepareStatement(strQuery);
			        statement.setString(1,valueName);
			        statement.setString(2,valuePhone);
			        statement.setString(3,valueCourse);
			        statement.executeUpdate();
			        
		            nameTF.setText("");
		            phoneTF.setText("");
		            courseTF.setText("");
		            table_update();
				}
				
				catch (ClassNotFoundException ex) {
					ex.printStackTrace();
					  
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		
		JButton update=new JButton("Update");
		update.setBounds(255, 470, 190, 40);
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String valueName=nameTF.getText();
				String valuePhone=phoneTF.getText();
				String valueCourse=courseTF.getText();
				int selectedIndex = table.getSelectedRow();
				
				try {
					int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentRegistration","root","");
					
					String strQuery = "update student set name= ?,phone= ?,course= ? where id= ?";
			        PreparedStatement statement = con.prepareStatement(strQuery);
			        statement.setString(1,valueName);
			        statement.setString(2,valuePhone);
			        statement.setString(3,valueCourse);
			        statement.setInt(4,id);
			        statement.executeUpdate();
			        
		            nameTF.setText("");
		            phoneTF.setText("");
		            courseTF.setText("");
		            table_update();
				}
				
				catch (ClassNotFoundException ex) {
					ex.printStackTrace();
					  
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JButton delete=new JButton("Delete");
		delete.setBounds(50, 530, 398, 40);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				try {
					int id = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentRegistration","root","");
					
					String strQuery = "delete from student where id = ?";
			        PreparedStatement statement = con.prepareStatement(strQuery);
			        statement.setInt(1,id);
			        statement.executeUpdate();
			        nameTF.setText("");
		            phoneTF.setText("");
		            courseTF.setText("");
		            table_update();
				}
				catch (ClassNotFoundException ex) {
					ex.printStackTrace();
					  
				}
				catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		table_update();
		
		table.setModel(model);
		
		
		JScrollPane sp=new JScrollPane(table); 
		sp.setBounds(500, 200, 690, 500);

		frame.add(sp);
		frame.add(delete);
		frame.add(update);
		frame.add(add);
		frame.add(courseTF);
		frame.add(course);
		frame.add(phoneTF);
		frame.add(phone);
		frame.add(nameTF);
		frame.add(name);
		frame.add(label);
		frame.setSize(1200, 750);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	public static void table_update() {
		model.getDataVector().removeAllElements();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentRegistration","root","");
			Statement st=con.createStatement();
			String sql="select * from student";
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{
			    String d = rs.getString("id");
			    String e = rs.getString("name");
			    String f = rs.getString("phone");
			    String g = rs.getString("course");
			    model.addRow(new Object[]{d, e, f, g});
			}
			con.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		  
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}