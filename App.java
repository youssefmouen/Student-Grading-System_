import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentGradingSystem system = new StudentGradingSystem();
        
        System.out.println("Welcome to Student Grading System");
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Assign Grade");
            System.out.println("5. Calculate GPA");
            System.out.println("6. Generate Report Card");
            System.out.println("7. Exit");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    system.add_student(studentId, studentName);
                    break;
                    
                case 2:
                    System.out.print("Enter course ID: ");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine();
                    System.out.print("Enter credit hours: ");
                    int creditHours = scanner.nextInt();
                    system.add_course(courseId, courseName, creditHours, 0);
                    break;

                case 3:
                    System.out.print("Enter student ID: ");
                    int enrollStudentId = scanner.nextInt();
                    System.out.print("Enter course ID: ");
                    int enrollCourseId = scanner.nextInt();
                    system.enrollStudentInCourse(enrollStudentId, enrollCourseId);
                    break;
                    
                case 4:
                    System.out.print("Enter student ID: ");
                    int gradeStudentId = scanner.nextInt();
                    System.out.print("Enter course ID: ");
                    int gradeCourseId = scanner.nextInt();
                    System.out.print("Enter grade: ");
                    float gradeValue = scanner.nextFloat();
                    system.assign_grades(gradeStudentId, gradeCourseId, gradeValue);
                    break;
                    
                case 5:
                    System.out.print("Enter student ID to calculate GPA: ");
                    int gpaStudentId = scanner.nextInt();
                    double gpa = system.calculateGPA(gpaStudentId);
                    if (gpa != -1.0) {
                        System.out.println("GPA: " + gpa);
                    }
                    break;
                    
                case 6:
                    System.out.print("Enter student ID to generate report card: ");
                    int reportStudentId = scanner.nextInt();
                    String report = system.generateReportCard(reportStudentId);
                    System.out.println(report);
                    break;
                    
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}