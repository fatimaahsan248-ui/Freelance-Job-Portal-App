import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FreelanceJobPortalGUI extends JFrame {
    private ArrayList<User> users;
    private User currentUser;

    public FreelanceJobPortalGUI(ArrayList<User> users) {
        this.users = users;

        setTitle("Freelance Job Portal GUI");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // closes only the window
        setLocationRelativeTo(null);

        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JButton exitBtn = new JButton("Exit");

        loginBtn.addActionListener(e -> showLoginScreen());
        registerBtn.addActionListener(e -> showRegisterScreen());
        exitBtn.addActionListener(e -> System.exit(0)); // Exits the full app

        panel.add(new JLabel("Welcome to Freelance Job Portal (GUI)", SwingConstants.CENTER));
        panel.add(loginBtn);
        panel.add(registerBtn);
        panel.add(exitBtn);

        add(panel);
        revalidate();
        repaint();
    }

    private void showLoginScreen() {
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        int result = JOptionPane.showConfirmDialog(this, new Object[]{
                "Username:", userField,
                "Password:", passField
        }, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            for (User user : users) {
                if (user.username.equals(username) && user.login(password)) {
                    currentUser = user;
                    if (user instanceof Client client) {
                        showClientDashboard(client);
                    } else if (user instanceof Freelancer freelancer) {
                        showFreelancerDashboard(freelancer);
                    }
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private void showRegisterScreen() {
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        String[] roles = {"client", "freelancer"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        int result = JOptionPane.showConfirmDialog(this, new Object[]{
                "Username:", userField,
                "Password:", passField,
                "Role:", roleBox
        }, "Register", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            for (User u : users) {
                if (u.username.equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                    return;
                }
            }

            if ("client".equals(role)) {
                users.add(new Client(username, password));
            } else {
                users.add(new Freelancer(username, password));
            }

            JOptionPane.showMessageDialog(this, "User registered successfully!");
        }
    }

    private void showClientDashboard(Client client) {
        getContentPane().removeAll();
        setTitle("Client Dashboard");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton postJobBtn = new JButton("Post Job");
        JButton viewJobsBtn = new JButton("View My Jobs");
        JButton logoutBtn = new JButton("Logout");

        postJobBtn.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextField descField = new JTextField();
            int res = JOptionPane.showConfirmDialog(this, new Object[]{
                    "Job Title:", titleField,
                    "Description:", descField
            }, "Post Job", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                client.postJob(titleField.getText(), descField.getText());
                JOptionPane.showMessageDialog(this, "Job posted!");
            }
        });

        viewJobsBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("My Posted Jobs:\n");
            for (Job job : client.getJobs()) {
                sb.append("Title: ").append(job.getTitle()).append("\n");
                sb.append("Description: ").append(job.getDescription()).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            showWelcomeScreen();
        });

        panel.add(postJobBtn);
        panel.add(viewJobsBtn);
        panel.add(logoutBtn);
        add(panel);
        revalidate();
        repaint();
    }

    private void showFreelancerDashboard(Freelancer freelancer) {
        getContentPane().removeAll();
        setTitle("Freelancer Dashboard");

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton viewJobsBtn = new JButton("View All Jobs");
        JButton logoutBtn = new JButton("Logout");

        viewJobsBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("All Available Jobs:\n");
            for (User u : users) {
                if (u instanceof Client client) {
                    for (Job job : client.getJobs()) {
                        sb.append("Title: ").append(job.getTitle()).append("\n");
                        sb.append("Description: ").append(job.getDescription()).append("\n\n");
                    }
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            showWelcomeScreen();
        });

        panel.add(viewJobsBtn);
        panel.add(logoutBtn);
        add(panel);
        revalidate();
        repaint();
    }

    public static void launchGUI(ArrayList<User> users) {
        SwingUtilities.invokeLater(() -> new FreelanceJobPortalGUI(users).setVisible(true));
    }
}
