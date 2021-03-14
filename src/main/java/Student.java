import java.util.ArrayList;
import java.util.List;

public class Student {
    ArrayList<Integer> compatibility;
    int studentID;

    Student(int studentID, ArrayList<Integer> compatibility) {
        this.compatibility = compatibility;
        this.studentID = studentID;
    }

    Integer getCompatibility(Student student) {
        int studentID = student.getStudentID();
        return compatibility.get(studentID);
    }

    int getStudentID() {
        return studentID;
    }
}
