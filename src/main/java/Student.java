import java.util.ArrayList;
import java.util.List;

public class Student {
    ArrayList<Integer> compatibility;
    int studentID;

    Student(int studentID, ArrayList<Integer> compatibility) {
        this.compatibility = compatibility;
        this.studentID = studentID;
    }

    ArrayList<Integer> getCompatibility() {
        return compatibility;
    }

    int getStudentID() {
        return studentID;
    }
}
