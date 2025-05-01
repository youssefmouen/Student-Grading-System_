public class Course { 
    private int course_ID;
    private String name;
    private int credit_hours;
    private float grade;

    Course(int course_ID, String name, int credit_hours, int grade)
    {
        this.course_ID = course_ID;
        this.name = name;
        this.credit_hours = credit_hours;
        this.grade = grade;
    }

    public void setCourse_ID(int course_ID) {
        this.course_ID = course_ID;
    }
    public int getCourse_ID() {
        return course_ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setgrade(float grade){
        this.grade = grade;
    }
    public float getgrade(){
        return grade;
    }
    public void setcredit_hours(int credit_hours) {
        this.credit_hours = credit_hours;
    }
    public int getcredit_hours() {
        return credit_hours;
    }
    
}