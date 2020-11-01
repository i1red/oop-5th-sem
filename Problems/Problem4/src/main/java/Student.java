public class Student extends Human {
    private String universityName;
    private int course;

    public Student(String firstName, String lastName, String universityName, int course) {
        super(firstName, lastName);
        this.setUniversityName(universityName);
        this.setCourse(course);
    }

    public void study() {
        System.out.println("Pretending that I'm studying");
    }

    public String getUniversityName() {
        return this.universityName;
    }

    public int getCourse() {
        return this.course;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setCourse(int course) {
        this.course = course;
    }
}

