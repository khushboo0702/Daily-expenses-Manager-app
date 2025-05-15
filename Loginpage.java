import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Loginpage extends JFrame implements ActionListener {
   // private JFrame frame;
    private JLabel messageLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;
    
   public Loginpage() {
        JFrame f = new JFrame("LOGIN PAGE");
        f.setLayout(new FlowLayout(FlowLayout.CENTER));
         
        JLabel l1 = new JLabel("<html><h2><b>Login Here!</b></h2></html>");
        l1.setBounds(135, 0, 300, 100);
        l1.setForeground(java.awt.Color.BLACK);

        JLabel l2 = new JLabel("Enter Username:");
        l2.setBounds(50, 70, 150, 40);
        usernameField = new JTextField();
        usernameField.setBounds(50, 100, 250, 30);

        JLabel l3 = new JLabel("Enter Password:");
        l3.setBounds(50, 140, 120, 40);
        passwordField = new JPasswordField();
        passwordField.setBounds(50, 170, 250, 30);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 220, 300, 25);
        

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 250,80, 30);
        loginButton.addActionListener(this);
        
        JLabel l4 = new JLabel("New Here ?");
        l4.setBounds(50, 280, 110, 40);

        JLabel l5 = new JLabel("<html><U>Click here to register !</U></html>");
        l5.setBounds(130, 280, 210, 40);
        l5.setForeground(java.awt.Color.BLUE);
        l5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        l5.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e ){ new RegistrationForm();
             dispose();
            }
        });


        f.add(l1);
        f.add(l2);
        f.add(usernameField);
        f.add(l3);
        f.add(passwordField);
        f.add(messageLabel);
        f.add(loginButton);
        f.add(l4);
        f.add(l5);

        f.setLayout(null);
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null); // Center the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);

        
        f.setVisible(true);
    }

 @Override
public void actionPerformed(ActionEvent e) {
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword());

    if (username.isEmpty() || password.isEmpty()) {
        messageLabel.setForeground(Color.RED);
        messageLabel.setText("Please fill all fields.");
    } else if (checkLogin(username, password)) {
        messageLabel.setForeground(new Color(0, 128, 0));
        messageLabel.setText("Login successful!");
        new MenuFrame(); // Load your next page
        dispose();
    } else {
        messageLabel.setForeground(Color.RED);
        messageLabel.setText("Wrong Username or Password.");
    }
}


private boolean checkLogin(String username, String password) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp",
            "root", "Jitesh@123"
        );

        String sql = "SELECT * FROM registration WHERE name = ? AND password = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();
        boolean result = rs.next(); // returns true if user exists

        rs.close();
        pst.close();
        con.close();

        return result;

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        return false;
    }
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(Loginpage::new);
}
}