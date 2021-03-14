import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        // student array consisting of compatibility score for each student
        //ArrayList<Integer> student;
        Student student;
        // students array consisting of Student objects
        List<Student> students = new ArrayList<>();
        // dorm consisting of 4 students
        ArrayList<Student> dorm = null;
        // below data structure is smelly
        // dorms consisting of all dorms
        List<ArrayList<Student>> dorms = new ArrayList<>();

        // read resource file into student array then add each student to students array
        Scanner scanner = new Scanner(new File("src/main/resources/roommates.txt"));
        int counterForStudentID = 0;
        while(scanner.hasNext()) {

            ArrayList<Integer> compatibilityList = new ArrayList<>();

            // each line is 200 characters long not including spaces as they are not counted and instead used as delimiter
            while(compatibilityList.size() < 200) {
                // convert next string from scanner to integer and store it in next index of the current student array
                compatibilityList.add(Integer.parseInt(scanner.next()));
            }
            // when the end of a line is reached add the compatibilityList to new Student object, increment counter and
            // add to students list
            student = new Student(counterForStudentID, compatibilityList);
            counterForStudentID++;
            students.add(student);
        }
        // scanner is closed but it still contains a lot of information. I would kind of like to reclaim the space this
        // is taking up too - reached out to fellow classmates and they said that the java garbage collection would take
        // care of this
        scanner.close();

        // assign students in intervals of 4 to a room
        // sorting by position in list that will be properly sorted later by compatibility
        for (int i = 0; i < students.size(); i++) {
            // if student position in array is an interval of 4 create a new dorm
            if (i%4 == 0) {
                dorm = new ArrayList<>();
            }
            // add students to a dorm until its full
            if (dorm.size() < 4) {
                dorm.add(students.get(i));
            }
            // add dorm to dorms list when it has reached capacity
            if (dorm.size() == 4) {
                dorms.add(dorm);
            }
        }

        // sort the dorms by how compatible students are by using simulated annealing technique
        // iterate through dorms
        // for each dorm find total compatibility score
        // find compatibility score for all students in dorm (AB, AC, AD, BC, BD, CD)
        
    }

    // lower compatibility score means greater compatibility
    double probability(int student1CompScore, int student2CompScore, double temp) {
        if (student2CompScore > student1CompScore) return 1;
        return Math.exp((student1CompScore - student2CompScore)/temp);
    }
}
