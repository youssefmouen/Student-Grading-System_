import java.util.ArrayList;
import java.util.List;

public class StudentGradingSystem {
    private List<Student> students;
    private List<Course> courses;
    private List<Grade> grades;

    public StudentGradingSystem() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public void setstudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getstudents() {
        return students;
    }

    public void setcourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getcourses() {
        return courses;
    }

    public void setgrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Grade> getgrades() {
        return grades;
    }

    public void add_student(int ID, String name) {
        Student newStudent = new Student(ID, name);
        students.add(newStudent);
        System.out.println("\nYour name: " + name + " ,ID: " + ID);
    };

    public void add_course(int course_ID, String name, int credit_hours, int grades) {
        Course newCourse = new Course(course_ID, name, credit_hours, grades);
        courses.add(newCourse);
        System.out.println(
                "\nName of the course: " + name + " it's ID: " + course_ID + ", it's credit hours: " + credit_hours);
    }

    public void assign_grades(int ID, int course_ID, float grade) {
        if (grade < 0 || grade > 100) {
            System.out.println("Error: Grade must be 0-100");
            return;
        }
        // Find the student
        for (Student student : students) {
            if (student.getID() == ID) {
                // Check if student is enrolled in this course
                boolean isEnrolled = false;
                for (Course course : student.getEnrolledCourses()) {
                    if (course.getCourse_ID() == course_ID) {
                        isEnrolled = true;
                        break;
                    }
                }

                if (isEnrolled) {
                    Grade newGrade = new Grade(ID, course_ID, grade);
                    grades.add(newGrade);

                    // Update the grade in the course object
                    for (Course course : courses) {
                        if (course.getCourse_ID() == course_ID) {
                            course.setgrade((float) grade); // Note: Consider changing to float
                            break;
                        }
                    }
                    System.out.println("\nGrade assigned successfully.");
                } else {
                    System.out.println("\nStudent is not enrolled in this course!");
                }
                return;
            }
        }
        System.out.println("\nStudent not found!");
    }

    public double calculateGPA(int ID) {
        for (Student student : students) {
            if (student.getID() == ID) {
                return student.calculateGPA();
            }
        }
        System.out.println("\nStudent not found");
        return -1.0;
    }

    private Grade findGrade(int studentId, int courseId) {
        for (Grade g : grades) {
            if (g.getID() == studentId && g.getCourse_ID() == courseId) {
                return g;
            }
        }
        return null;
    }

    public String generateReportCard(int ID) {
        for (Student student : students) {
            if (student.getID() == ID) {
                StringBuilder report = new StringBuilder();
                report.append("Report Card for ").append(student.getName()).append("\n");
                report.append("-------------------------\n");

                for (Course course : student.getEnrolledCourses()) {
                    Grade grade = findGrade(ID, course.getCourse_ID());
                    if (grade != null) {
                        report.append(course.getName()).append(": ")
                                .append(grade.getgrade()).append("\n");
                    }
                }

                report.append("-------------------------\n");
                report.append("GPA: ").append(String.format("%.2f", calculateGPA(ID)));
                return report.toString();
            }
        }
        return "Student not found";
    }
    // public String generateReportCard(int ID){
    // for(Student student : students){
    // if (student.getID() == ID){
    // return student.generateReportCard();
    // }
    // }
    // return "\nStudent not found.";
    // }

    public List<Student> filterByStudentName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Course> filterByCourseName(String name) {
        List<Course> result_c = new ArrayList<>();
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                result_c.add(course);
            }
        }
        return result_c;
    }

    public void enrollStudentInCourse(int studentId, int courseId) {
        // Find the student
        Student student = null;
        for (Student s : students) {
            if (s.getID() == studentId) {
                student = s;
                break;
            }
        }

        // Find the course
        Course course = null;
        for (Course c : courses) {
            if (c.getCourse_ID() == courseId) {
                course = c;
                break;
            }
        }

        // If both exist, enroll the student
        if (student != null && course != null) {
            student.enroll(course);
            System.out.println("\nStudent " + student.getName() + " enrolled in " + course.getName());
        } else {
            System.out.println("\nStudent or course not found!");
        }
    }

    public String getStudentName(int id) {
        for (Student s : students) {
            if (s.getID() == id)
                return s.getName();
        }
        return "Unknown";
    }

    public String getCourseName(int id) {
        for (Course c : courses) {
            if (c.getCourse_ID() == id)
                return c.getName();
        }
        return "Unknown";
    }

}
