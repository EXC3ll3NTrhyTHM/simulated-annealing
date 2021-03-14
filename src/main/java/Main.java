import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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
        // List consisting of all dorms
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

        double temperature = 20000;
        double coolingFactor = 0.99995;
        // sort the dorms by how compatible students are using simulated annealing technique
        // iterate through dorms until temperature reaches 0
        // iterate 20k times
        int dorm1Comp = calculateDormTotalComp(dorms.get(0));

        // idea from here https://stackabuse.com/simulated-annealing-optimization-algorithm-in-java/
        for (double t = temperature; t > 1; t *= coolingFactor) {
            // select dorm at random and student from dorm at random
            // do same for another student and swap them in a dummy dorm first, check new compatibility score
            // before swapping in real dorms we will want to compare the potential new compatibility scores and only swap if the probability
            // is high enough

            // Random number generator implementation from here:
            // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
            int indexForStudent1 = ThreadLocalRandom.current().nextInt(0, 4);
            int indexForDorm1 = ThreadLocalRandom.current().nextInt(0, 50);

            ArrayList<Student> dorm1 = dorms.get(indexForDorm1);
            ArrayList<Student> dummyDorm1 = new ArrayList<>();
            copy(dorm1, dummyDorm1);
            Student student1 = dorm1.get(indexForStudent1);

            int indexForStudent2 = ThreadLocalRandom.current().nextInt(0, 4);
            int indexForDorm2 = ThreadLocalRandom.current().nextInt(0, 50);

            ArrayList<Student> dorm2 = dorms.get(indexForDorm2);
            ArrayList<Student> dummyDorm2 = new ArrayList<>();
            copy(dorm1, dummyDorm2);
            Student student2 = dorm2.get(indexForStudent2);

            dummyDorm1.set(indexForStudent1, student2);
            dummyDorm2.set(indexForStudent2, student1);
            
            double randomNumber = Math.random();
            double probability1 = probability(calculateDormTotalComp(dorm1), calculateDormTotalComp(dummyDorm1), t);
            double probability2 = probability(calculateDormTotalComp(dorm2), calculateDormTotalComp(dummyDorm2), t);

//            if (t < 5) {
//                System.out.println("low temp");
//            }

            if (randomNumber < probability1 &&
                    randomNumber < probability2) {
                dorm2 = dummyDorm2;
                dorm1 = dummyDorm1;
            }
        }

        int[] dormTotals = new int[50];
        for (int i = 0; i < 50; i++) {
            dormTotals[i] = calculateDormTotalComp(dorms.get(i));
        }
        System.out.println(Arrays.stream(dormTotals).average());
    }

    static void copy(ArrayList<Student> dorm1, ArrayList<Student> dorm2) {
        for (int i = 0; i < 4; i++) {
            dorm2.add(dorm1.get(i));
        }
    }

    // lower compatibility score means greater compatibility
    static double probability(int dorm1CompScore, int dorm2CompScore, double temp) {
        if (dorm2CompScore < dorm1CompScore) return 1;
        int difference = dorm1CompScore - dorm2CompScore;
        double diffOverTemp = difference/temp;
        return Math.exp(diffOverTemp);
    }

    // find compatibility score for all students in dorm (AB, AC, AD, BC, BD, CD)
    static int calculateDormTotalComp(ArrayList<Student> dorm) {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            if (i==0) {
                Student student1 = dorm.get(i);
                for (int j = 0; j < 3; j++) {
                    Student student2 = dorm.get(j+1);
                    total += student1.getCompatibility(student2);
                }
            }
            if (i==1) {
                Student student1 = dorm.get(i);
                for (int j = 0; j < 2; j++) {
                    Student student2 = dorm.get(j+2);
                    total += student1.getCompatibility(student2);
                }
            }
            if (i==2) {
                Student student1 = dorm.get(i);
                Student student2 = dorm.get(i+1);
                total += student1.getCompatibility(student2);
            }
        }
        return total;
    }
}
