import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentGradingGUI {
    private StudentGradingSystem system;
    private JFrame frame;
    private JPanel dashboardPanel;
    private JPanel studentsPanel;
    private JPanel gradesPanel;
    private JPanel reportsPanel;

    // Color scheme
    private final Color PRIMARY_COLOR = new Color(52, 152, 219); // Blue
    private final Color SECONDARY_COLOR = new Color(241, 196, 15); // Yellow
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light gray
    private final Color light_blueeeeeeColor = new Color(173, 216, 230); // Light blue
    private final Color PANEL_BACKGROUND = Color.WHITE;
    private final Color TABLE_HEADER_COLOR = new Color(44, 62, 80); // Dark blue
    private final Color TABLE_ROW_COLOR = new Color(250, 250, 250);
    private final Color TABLE_ALTERNATE_ROW_COLOR = new Color(245, 245, 245);

    public StudentGradingGUI() {
        system = new StudentGradingSystem();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Student Grading System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setLayout(new BorderLayout());

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(Color.DARK_GRAY);

        // Create panels for each tab
        dashboardPanel = createDashboardPanel();
        studentsPanel = createStudentsPanel();
        gradesPanel = createGradesPanel();
        reportsPanel = createReportsPanel();

        // Add tabs
        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Students", studentsPanel);
        tabbedPane.addTab("Grades", gradesPanel);
        tabbedPane.addTab("Reports", reportsPanel);

        // Add menu bar
        frame.setJMenuBar(createMenuBar());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR);
        menuBar.setForeground(Color.WHITE);

        // File menu
        JMenu fileMenu = new JMenu("File");
        styleMenu(fileMenu);
        JMenuItem exitItem = new JMenuItem("Exit");
        styleMenuItem(exitItem);
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        styleMenu(editMenu);
        JMenuItem addStudentItem = new JMenuItem("Add Student");
        styleMenuItem(addStudentItem);
        addStudentItem.addActionListener(e -> addStudentDialog());
        JMenuItem addCourseItem = new JMenuItem("Add Course");
        styleMenuItem(addCourseItem);
        addCourseItem.addActionListener(e -> addCourseDialog());
        editMenu.add(addStudentItem);
        editMenu.add(addCourseItem);

        // Actions menu
        JMenu actionMenu = new JMenu("Actions");
        styleMenu(actionMenu);
        JMenuItem enrollItem = new JMenuItem("Enroll Student");
        styleMenuItem(enrollItem);
        enrollItem.addActionListener(e -> enrollStudentDialog());
        JMenuItem assignGradeItem = new JMenuItem("Assign Grade");
        styleMenuItem(assignGradeItem);
        assignGradeItem.addActionListener(e -> assignGradeDialog());
        actionMenu.add(enrollItem);
        actionMenu.add(assignGradeItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(actionMenu);

        return menuBar;
    }

    private void styleMenu(JMenu menu) {
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void styleMenuItem(JMenuItem item) {
        item.setBackground(Color.WHITE);
        item.setForeground(Color.DARK_GRAY);
        item.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(light_blueeeeeeColor);

        // Dashboard title
        JLabel titleLabel = new JLabel("DASHBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        statsPanel.setBackground(light_blueeeeeeColor);

        JPanel totalStudentsPanel = createStatPanel("Total Students", String.valueOf(system.getstudents().size()),
                PRIMARY_COLOR);
        JPanel totalCoursesPanel = createStatPanel("Total Courses", String.valueOf(system.getcourses().size()),
                PRIMARY_COLOR);

        // Find top performer
        String topPerformer = "None";
        double maxGPA = 0;
        for (Student student : system.getstudents()) {
            double gpa = student.calculateGPA();
            if (gpa > maxGPA) {
                maxGPA = gpa;
                topPerformer = student.getName();
            }
        }
        JPanel topPerformerPanel = createStatPanel("Top Performer", topPerformer, PRIMARY_COLOR);

        statsPanel.add(totalStudentsPanel);
        statsPanel.add(totalCoursesPanel);
        statsPanel.add(topPerformerPanel);

        panel.add(statsPanel, BorderLayout.CENTER);

        // Recent activity
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        activityPanel.setBackground(light_blueeeeeeColor);

        JLabel activityLabel = new JLabel("Recent Activity");
        activityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        activityLabel.setForeground(PRIMARY_COLOR);
        activityPanel.add(activityLabel, BorderLayout.NORTH);

        DefaultTableModel activityModel = new DefaultTableModel(
                new Object[] { "Student Name", "Course", "Grade" }, 0);
        JTable activityTable = new JTable(activityModel);
        styleTable(activityTable);

        // Add some sample activities
        for (Grade grade : system.getgrades()) {
            String studentName = system.getStudentName(grade.getID());
            String courseName = system.getCourseName(grade.getCourse_ID());
            activityModel.addRow(new Object[] { studentName, courseName, grade.getgrade() });
        }

        JScrollPane scrollPane = new JScrollPane(activityTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        activityPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(activityPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStatPanel(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color.darker()),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.setBackground(color.brighter());

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Title
        JLabel titleLabel = new JLabel("STUDENTS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table of students
        String[] columnNames = { "Student ID", "Name", "Courses Enrolled", "GPA" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Student student : system.getstudents()) {
            int courseCount = student.getEnrolledCourses().size();
            double gpa = student.calculateGPA();
            model.addRow(new Object[] {
                    student.getID(),
                    student.getName(),
                    courseCount,
                    String.format("%.2f", gpa)
            });
        }

        JTable studentTable = new JTable(model);
        styleTable(studentTable);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Student", PRIMARY_COLOR);
        addButton.addActionListener(e -> addStudentDialog());
        JButton enrollButton = createStyledButton("Enroll Student", PRIMARY_COLOR);
        enrollButton.addActionListener(e -> enrollStudentDialog());
        JButton refreshButton = createStyledButton("Refresh", SECONDARY_COLOR);
        refreshButton.addActionListener(e -> refreshStudentsPanel());

        buttonPanel.add(addButton);
        buttonPanel.add(enrollButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGradesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Title
        JLabel titleLabel = new JLabel("GRADES", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table of grades
        String[] columnNames = { "Student Name", "Course", "Grade" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Grade grade : system.getgrades()) {
            String studentName = system.getStudentName(grade.getID());
            String courseName = system.getCourseName(grade.getCourse_ID());
            model.addRow(new Object[] {
                    studentName,
                    courseName,
                    grade.getgrade()
            });
        }

        JTable gradeTable = new JTable(model);
        styleTable(gradeTable);

        // Add button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton assignButton = createStyledButton("Assign Grade", PRIMARY_COLOR);
        assignButton.addActionListener(e -> assignGradeDialog());
        JButton refreshButton = createStyledButton("Refresh", SECONDARY_COLOR);
        refreshButton.addActionListener(e -> refreshGradesPanel());

        buttonPanel.add(assignButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        // Title
        JLabel titleLabel = new JLabel("REPORTS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Report controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(BACKGROUND_COLOR);

        JLabel studentLabel = new JLabel("Student ID:");
        studentLabel.setForeground(Color.DARK_GRAY);

        JTextField studentIdField = new JTextField(10);
        studentIdField.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton generateButton = createStyledButton("Generate Report", PRIMARY_COLOR);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        reportArea.setBackground(PANEL_BACKGROUND);
        JScrollPane reportScrollPane = new JScrollPane(reportArea);
        reportScrollPane.setBorder(BorderFactory.createEmptyBorder());

        generateButton.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                String report = system.generateReportCard(studentId);
                reportArea.setText(report);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid student ID", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        controlPanel.add(studentLabel);
        controlPanel.add(studentIdField);
        controlPanel.add(generateButton);

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(reportScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void styleTable(JTable table) {
        table.setBackground(PANEL_BACKGROUND);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        // Style header
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        // Style rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(TABLE_ROW_COLOR);
                    } else {
                        c.setBackground(TABLE_ALTERNATE_ROW_COLOR);
                    }
                }

                return c;
            }
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void refreshStudentsPanel() {
        frame.remove(studentsPanel);
        studentsPanel = createStudentsPanel();
        ((JTabbedPane) frame.getContentPane().getComponent(0)).setComponentAt(1, studentsPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void refreshGradesPanel() {
        frame.remove(gradesPanel);
        gradesPanel = createGradesPanel();
        ((JTabbedPane) frame.getContentPane().getComponent(0)).setComponentAt(2, gradesPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void refreshDashboardPanel() {
        frame.remove(dashboardPanel);
        dashboardPanel = createDashboardPanel();
        ((JTabbedPane) frame.getContentPane().getComponent(0)).setComponentAt(0, dashboardPanel);
        frame.revalidate();
        frame.repaint();
    }

    // === Dialog Methods ===
    private void addStudentDialog() {
        JPanel dialogPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        dialogPanel.setBackground(PANEL_BACKGROUND);

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField();

        dialogPanel.add(idLabel);
        dialogPanel.add(idField);
        dialogPanel.add(nameLabel);
        dialogPanel.add(nameField);

        int option = JOptionPane.showConfirmDialog(frame, dialogPanel, "Add Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                system.add_student(id, name);
                refreshStudentsPanel();
                refreshDashboardPanel();
                JOptionPane.showMessageDialog(frame, "Student added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter numbers only.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addCourseDialog() {
        JPanel dialogPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        dialogPanel.setBackground(PANEL_BACKGROUND);

        JLabel idLabel = new JLabel("Course ID:");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Course Name:");
        JTextField nameField = new JTextField();
        JLabel hoursLabel = new JLabel("Credit Hours:");
        JTextField hoursField = new JTextField();

        dialogPanel.add(idLabel);
        dialogPanel.add(idField);
        dialogPanel.add(nameLabel);
        dialogPanel.add(nameField);
        dialogPanel.add(hoursLabel);
        dialogPanel.add(hoursField);

        int option = JOptionPane.showConfirmDialog(frame, dialogPanel, "Add Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int hours = Integer.parseInt(hoursField.getText());
                system.add_course(id, name, hours, 0);
                refreshDashboardPanel();
                JOptionPane.showMessageDialog(frame, "Course added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid number format. Please check your inputs.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void enrollStudentDialog() {
        JPanel dialogPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        dialogPanel.setBackground(PANEL_BACKGROUND);

        JLabel studentLabel = new JLabel("Student ID:");
        JTextField studentIdField = new JTextField();
        JLabel courseLabel = new JLabel("Course ID:");
        JTextField courseIdField = new JTextField();

        dialogPanel.add(studentLabel);
        dialogPanel.add(studentIdField);
        dialogPanel.add(courseLabel);
        dialogPanel.add(courseIdField);

        int option = JOptionPane.showConfirmDialog(frame, dialogPanel, "Enroll Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                system.enrollStudentInCourse(studentId, courseId);
                refreshStudentsPanel();
                JOptionPane.showMessageDialog(frame, "Enrollment successful!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format. Please check your inputs.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void assignGradeDialog() {
        JPanel dialogPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        dialogPanel.setBackground(PANEL_BACKGROUND);

        JLabel studentLabel = new JLabel("Student ID:");
        JTextField studentIdField = new JTextField();
        JLabel courseLabel = new JLabel("Course ID:");
        JTextField courseIdField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade (0-100):");
        JTextField gradeField = new JTextField();

        dialogPanel.add(studentLabel);
        dialogPanel.add(studentIdField);
        dialogPanel.add(courseLabel);
        dialogPanel.add(courseIdField);
        dialogPanel.add(gradeLabel);
        dialogPanel.add(gradeField);

        int option = JOptionPane.showConfirmDialog(frame, dialogPanel, "Assign Grade",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                float grade = Float.parseFloat(gradeField.getText());

                if (grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(frame, "Grade must be between 0 and 100",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                system.assign_grades(studentId, courseId, grade);
                refreshStudentsPanel();
                refreshGradesPanel();
                refreshDashboardPanel();
                JOptionPane.showMessageDialog(frame, "Grade assigned successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid number format. Please check your inputs.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradingGUI());
    }
}