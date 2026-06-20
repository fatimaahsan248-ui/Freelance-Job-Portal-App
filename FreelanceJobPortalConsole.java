import java.util.ArrayList;
import java.util.Scanner;

// Abstract User class
abstract class User {
    protected String username;
    protected String password;
    protected String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String pass) {
        return this.password.equals(pass);
    }

    public String getRole() {
        return role;
    }

    public abstract void dashboard(FreelanceJobPortalConsole app, Scanner scanner);
}

// Client class
class Client extends User {
    private ArrayList<Job> jobs = new ArrayList<>();

    public Client(String username, String password) {
        super(username, password, "client");
    }

    public void postJob(String title, String desc) {
        jobs.add(new Job(title, desc));
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    @Override
    public void dashboard(FreelanceJobPortalConsole app, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Client Dashboard ---");
            System.out.println("1. Post Job");
            System.out.println("2. View My Jobs");
            System.out.println("3. Logout");
            System.out.print("Select option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter Job Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Job Description: ");
                String desc = scanner.nextLine();
                postJob(title, desc);
                System.out.println("Job posted successfully!");
            } else if (choice == 2) {
                System.out.println("My Posted Jobs:");
                for (Job job : jobs) {
                    System.out.println("Title: " + job.getTitle());
                    System.out.println("Description: " + job.getDescription());
                    System.out.println("----------------------");
                }
            } else {
                break;
            }
        }
    }
}

// Freelancer class
class Freelancer extends User {
    public Freelancer(String username, String password) {
        super(username, password, "freelancer");
    }

    @Override
    public void dashboard(FreelanceJobPortalConsole app, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Freelancer Dashboard ---");
            System.out.println("1. View All Jobs");
            System.out.println("2. Logout");
            System.out.print("Select option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Available Jobs:");
                for (User u : app.users) {
                    if (u instanceof Client client) {
                        for (Job job : client.getJobs()) {
                            System.out.println("Title: " + job.getTitle());
                            System.out.println("Description: " + job.getDescription());
                            System.out.println("----------------------");
                        }
                    }
                }
            } else {
                break;
            }
        }
    }
}

// Job class
class Job {
    private String title;
    private String description;

    public Job(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}

// Main console application
public class FreelanceJobPortalConsole {
    ArrayList<User> users = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void start() {
        FreelanceJobPortalGUI.launchGUI(users);
        while (true) {
            System.out.println("\n--- Freelance Job Portal ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("4. Exit");
            System.out.print("Select option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (option == 1) {
                loginUser();
            } else if (option == 2) {
                registerUser();
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    private void loginUser() {
        System.out.print("Enter Username: ");
        String user = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        User matched = null;
        for (User u : users) {
            if (u.username.equals(user) && u.login(pass)) {
                matched = u;
                break;
            }
        }

        if (matched != null) {
            matched.dashboard(this, scanner);
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private void registerUser() {
        System.out.print("Enter Username: ");
        String user = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();
        System.out.print("Enter Role (client/freelancer): ");
        String role = scanner.nextLine();

        if (role.equalsIgnoreCase("client")) {
            users.add(new Client(user, pass));
            System.out.println("Client registered successfully!");
        } else if (role.equalsIgnoreCase("freelancer")) {
            users.add(new Freelancer(user, pass));
            System.out.println("Freelancer registered successfully!");
        } else {
            System.out.println("Invalid role. Try again.");
        }
    }

    public static void main(String[] args) {
        new FreelanceJobPortalConsole().start();
    }
}
