import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Integer> student;
        List<ArrayList<Integer>> students = new ArrayList<>();
        ArrayList<ArrayList<Integer>> dorm = null;
        // below data structure is smelly
        List<ArrayList<ArrayList<Integer>>> dorms = new ArrayList<>();

        Scanner scanner = new Scanner(new File("src/main/resources/roommates.txt"));
        while(scanner.hasNext()) {

            student = new ArrayList<>();

            while(student.size() < 200) {
                // convert next string from scanner to integer and store it in next index of the current student array
                student.add(Integer.parseInt(scanner.next()));
            }

            // when the end of a line is reached add the student to the students array
            students.add(student);
        }

        // scanner is closed but it still contains a lot of information. I would kind of like to reclaim the space this
        // is taking up too
        scanner.close();

        // assign students in intervals of 4 to a room
        // arbitrary assigning that will be sorted later
        for (int i = 0; i < students.size(); i++) {

            // if student is an interval of 4 create a new dorm
            if (i%4 == 0) {
                dorm = new ArrayList<>();
            }

            // add students to a dorm until its full
            if (dorm.size() < 4) {
                dorm.add(students.get(i));
            }

            if (dorm.size() == 4) {
                dorms.add(dorm);
            }
        }

        // sort the dorms by how compatible students are by using simulated annealing technique
    }
}
