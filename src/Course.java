

public class Course {
    private String title;
    private String number;
    private String abbrev;
    private String tags = "";

    public Course(String title, String number, String abbrev) {
        this.title = title;
        this.number = number;
        this.abbrev = abbrev;
    }

    public void add(String s) {
        this.tags += (s + ", ");
    }

    public String getOfficialCourse() {
        return abbrev + " " + number + " " + title;
    }

    @Override
    public String toString() {
        String courseInfo = "Course: " + title + " (" + abbrev + " " + number + ")";
        String reqs = "\n    This course does not meet any core requirements";
        if (tags.length() > 0) {
            reqs = "\n    Meets the following core requirements: " + tags.substring(0, tags.length() - 2);
        }
        return courseInfo + reqs;

    }
}
