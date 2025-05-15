import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.sql.*;
public class RegistrationForm extends JFrame implements ActionListener {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;
    private JLabel messageLabel;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);  // Absolute positioning

        // Username
        JLabel lheading = new JLabel("<html><h2><b>Registration form</b></h2></html>");
        lheading.setBounds(115, 0, 300, 100);
        lheading.setForeground(java.awt.Color.BLACK);
        add(lheading);


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 80, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(170, 80, 160, 30);
        add(usernameField);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 120, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(170, 120, 160, 30);
        add(emailField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 160, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(170, 160, 160, 30);
        add(passwordField);

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(30, 200, 120, 25);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(170, 200, 160,30);
        add(confirmPasswordField);

        // Message Label
        messageLabel = new JLabel("");
        messageLabel.setBounds(30, 240, 300, 25);
        add(messageLabel);

        // Register Button
        registerButton = new JButton("Register");
    
        registerButton.setBounds(50, 270, 100, 30);
        registerButton.addActionListener(this);
        add(registerButton);

        JLabel labelask = new JLabel("Already having an account ?");
        labelask.setBounds(30, 300, 250, 40);
        add(labelask);

        JLabel labellink = new JLabel("<html><U>Login Here !</U></html>");
        labellink.setBounds(200, 300, 150, 40);
        labellink.setForeground(java.awt.Color.BLUE);
        labellink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labellink.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e ){ new Loginpage();
             dispose();
            }
        });
        add(labellink);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please fill all fields.");
        } else if (!password.equals(confirmPassword)) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Passwords do not match.");
        } else {
            //messageLabel.setForeground(new Color(0, 128, 0));
            //messageLabel.setText("Registration successful!");
            // You can store the data or trigger other actions here
            saveToDatabase(username ,email ,password);
        }
    }
void saveToDatabase(String name,String email,String password) {
       /* String name = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());*/

        try (
            //Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp", "root", "Jitesh@123");
 /*FileInputStream fis = new FileInputStream(registration)*/){
            String query = "INSERT INTO registration(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                //saveToDatabase();
                messageLabel.setForeground(new Color(0, 128, 0));
                messageLabel.setText("Registration successful!");
            }

            pst.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }

}

