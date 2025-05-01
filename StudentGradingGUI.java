import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentGradingGUI {
    private StudentGradingSystem system;
    private JFrame frame;
    private JTextArea outputArea;

    public StudentGradingGUI() {
        system = new StudentGradingSystem();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Student Grading System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        
        String[] buttonLabels = {
            "Add Student", "Add Course", "Enroll Student", 
            "Assign Grade", "Calculate GPA", "View Report",
            "List Students", "List Courses"
        };
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this::handleButtonClick);
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private void handleButtonClick(ActionEvent e) {
        String command = ((JButton) e.getSource()).getText();
        
        try {
            switch (command) {
                case "Add Student":
                    addStudentDialog();
                    break;
                case "Add Course":
                    addCourseDialog();
                    break;
                case "Enroll Student":
                    enrollStudentDialog();
                    break;
                case "Assign Grade":
                    assignGradeDialog();
                    break;
                case "Calculate GPA":
                    calculateGPADialog();
                    break;
                case "View Report":
                    viewReportDialog();
                    break;
                case "List Students":
                    listStudents();
                    break;
                case "List Courses":
                    listCourses();
                    break;
            }
        } catch (Exception ex) {
            outputArea.append("Error: " + ex.getMessage() + "\n");
        }
    }

    // === Dialog Methods ===
    private void addStudentDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();

        Object[] message = {
            "Student ID:", idField,
            "Student Name:", nameField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                system.add_student(id, name);
                outputArea.append("Added student: " + name + " (ID: " + id + ")\n");
            } catch (NumberFormatException e) {
                outputArea.append("Invalid ID format. Please enter numbers only.\n");
            }
        }
    }

    private void addCourseDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField hoursField = new JTextField();

        Object[] message = {
            "Course ID:", idField,
            "Course Name:", nameField,
            "Credit Hours:", hoursField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Course", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int hours = Integer.parseInt(hoursField.getText());
                system.add_course(id, name, hours, 0);
                outputArea.append("Added course: " + name + " (ID: " + id + ")\n");
            } catch (NumberFormatException e) {
                outputArea.append("Invalid number format. Please check your inputs.\n");
            }
        }
    }

    private void enrollStudentDialog() {
        JTextField studentIdField = new JTextField();
        JTextField courseIdField = new JTextField();

        Object[] message = {
            "Student ID:", studentIdField,
            "Course ID:", courseIdField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Enroll Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                system.enrollStudentInCourse(studentId, courseId);
                outputArea.append("Enrolled " + system.getStudentName(studentId) + " in course " + system.getCourseName(courseId) + "\n");
            } catch (NumberFormatException e) {
                outputArea.append("Invalid ID format. Please check your inputs.\n");
            }
        }
    }

    private void assignGradeDialog() {
        JTextField studentIdField = new JTextField();
        JTextField courseIdField = new JTextField();
        JTextField gradeField = new JTextField();

        Object[] message = {
            "Student ID:", studentIdField,
            "Course ID:", courseIdField,
            "Grade (0-100):", gradeField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Assign Grade", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                int courseId = Integer.parseInt(courseIdField.getText());
                float grade = Float.parseFloat(gradeField.getText());
                system.assign_grades(studentId, courseId, grade);
                outputArea.append("Assigned grade " + grade + " to " + system.getStudentName(studentId) + " in " + system.getCourseName(courseId) + "\n");
            } catch (NumberFormatException e) {
                outputArea.append("Invalid number format. Please check your inputs.\n");
            }
        }
    }

    private void calculateGPADialog() {
        JTextField studentIdField = new JTextField();

        Object[] message = {
            "Student ID:", studentIdField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Calculate GPA", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                double gpa = system.calculateGPA(studentId);
                if (gpa != -1.0) {
                    outputArea.append("GPA for student " + studentId + ": " + String.format("%.2f", gpa) + "\n");
                }
            } catch (NumberFormatException e) {
                outputArea.append("Invalid ID format. Please enter numbers only.\n");
            }
        }
    }

    private void viewReportDialog() {
        JTextField studentIdField = new JTextField();

        Object[] message = {
            "Student ID:", studentIdField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "View Report Card", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                String report = system.generateReportCard(studentId);
                JOptionPane.showMessageDialog(frame, report, "Report Card", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                outputArea.append("Invalid ID format. Please enter numbers only.\n");
            }
        }
    }

    private void listStudents() {
        StringBuilder sb = new StringBuilder("=== Students ===\n");
        for (Student student : system.getstudents()) {
            sb.append("ID: ").append(student.getID())
              .append(", Name: ").append(student.getName()).append("\n");
        }
        outputArea.append(sb.toString());
    }

    private void listCourses() {
        StringBuilder sb = new StringBuilder("=== Courses ===\n");
        for (Course course : system.getcourses()) {
            sb.append("ID: ").append(course.getCourse_ID())
              .append(", Name: ").append(course.getName())
              .append(", Credits: ").append(course.getcredit_hours()).append("\n");
        }
        outputArea.append(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradingGUI());
    }
}