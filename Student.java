import java.util.ArrayList;
import java.util.List;

public class Student {
    private int ID;
    private String name;
    private List<Course> enrolledCourses;
    //private Grade grade;

    Student(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.enrolledCourses = new ArrayList<>();  //ana zawedt el line da, howa leeh lazma?
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enroll(Course course) {
        enrolledCourses.add(course);
    }

    public double calculateGPA() {
        if (enrolledCourses == null || enrolledCourses.isEmpty()) {
            return 0.0;
        }
    
        double totalPoints = 0.0;
        double totalCredits = 0;
    
        for (Course course : enrolledCourses) {
            float percentageGrade = course.getgrade();
            double gradePoints;
            
            // Convert percentage to 4.0 scale directly
            if (percentageGrade >= 93) gradePoints = 4.0;
            else if (percentageGrade >= 90) gradePoints = 3.7;
            else if (percentageGrade >= 87) gradePoints = 3.3;
            else if (percentageGrade >= 83) gradePoints = 3.0;
            else if (percentageGrade >= 80) gradePoints = 2.7;
            else if (percentageGrade >= 77) gradePoints = 2.3;
            else if (percentageGrade >= 73) gradePoints = 2.0;
            else if (percentageGrade >= 70) gradePoints = 1.7;
            else if (percentageGrade >= 67) gradePoints = 1.3;
            else if (percentageGrade >= 65) gradePoints = 1.0;
            else gradePoints = 0.0;
    
            totalPoints += gradePoints * course.getcredit_hours();
            totalCredits += course.getcredit_hours();
        }
    
        // Calculate GPA and round to 2 decimal places
        double gpa = totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
        return Math.round(gpa * 100) / 100.0;
    }

    public String generateReportCard() {
        StringBuilder report = new StringBuilder();
        report.append("=== Report Card ===\n");
        report.append("Student: ").append(name).append("\n\n");
        
        if (enrolledCourses.isEmpty()) {
            report.append("No courses enrolled\n");
        } else {
            report.append("Courses:\n");
            for (Course course : enrolledCourses) {
                report.append("- ").append(course.getName())
                      .append(": ").append(course.getgrade())
                      .append("\n");
            }
        }
        
        report.append("\nGPA: ").append(String.format("%.2f", calculateGPA()));
        return report.toString();
    }
    

    //@SuppressWarnings("unused")
    //public String generateReportCard() {
    //    StringBuilder report = new StringBuilder();
    //    for (int i = 0; i < enrolledCourses.size(); i++) {
    //        return("course name: " + enrolledCourses.get(i).getName() + "\nCourse grade: "
    //                + enrolledCourses.get(i).getgrade());
    //    }
    //    double CurrGpa = calculateGPA();
    //    return("Your GPA is: " + CurrGpa);
    //}

}
