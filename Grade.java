public class Grade {
    private int ID;
    private int course_ID;
    private float grade;

    Grade(int ID, int course_ID, float grade){
        this.ID = ID;
        this.course_ID = course_ID;
        this.grade = grade;
    }

    public void setID(int iD) {
        ID = iD;
    }
    public int getID() {
        return ID;
    }
    public void setCourse_ID(int course_ID) {
        this.course_ID = course_ID;
    }
    public int getCourse_ID() {
        return course_ID;
    }
    public void setgrade(float grade) {
        this.grade = grade;
    }
    public float getgrade() {
        return grade;
    }
}
