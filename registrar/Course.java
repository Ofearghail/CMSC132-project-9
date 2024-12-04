package registrar;

import java.util.HashSet;
import java.util.Objects;

// Jordon Farrell, jfarrel3, 117675665, section 0104
// I pledge on my honor that I have not given or received any unauthorized
// assistance on this assignment

// This class is an object that stores the information on a Course that gets
// stored in an ArrayList inside of the Registrar class. The Course class
// includes the deparment, course number, number of seats and an ArrayList
// roster of Student enrolled in the class.

public class Course {

    private String department;
    private int number, seats;

    // Stores Student objects that are enrolled in the course
    private HashSet<Student> roster = new HashSet<>();

    // Constructor for the Courses class

    public Course(String department, int number, int seats) {
        this.department = department;
        this.number = number;
        this.seats = seats;
    }

    // Constructor for the course class that allows the creation of a course
    // object without a seats parameter for the purpose of comparing course
    // objects
    public Course(String department, int number) {
        this.department = department;
        this.number = number;
        this.seats = 0;
    }

    // toString method that concatenates department and number fields
    public String toString() {

        return department + " " + number;

    }

    // Equals method that compared two course objects department and number
    // fields
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        return Objects.equals(department, other.department)
                && number == other.number;
    }

    // Adds a student to the roster Arraylist and returns the Course object to
    // allow for chained calls
    public Course addStudent(Student student) {

        roster.add(student);
        decrementSeats();
        return this;

    }

    // Removes a student from the roster Arraylist and returns the Course object
    // to allow for chained calls
    public Course removeStudent(Student student) {

        roster.remove(student);
        incrementSeats();
        return this;

    }

    // Checks if the Student parameter is equal to a Student already inside the
    // roster ArrayList if student with firstName and lastName is already
    // enrolled in course method returns true if not returns false
    public boolean checkDuplicate(Student student) {
        boolean duplicate = false;

        for (Student pupil : roster) {

            if (pupil.toString().equals(student.toString())) {
                duplicate = true;
            }
        }

        return duplicate;

    }

    public boolean checkDuplicate(String firstName, String lastName) {
        boolean duplicate = false;
        Student temp = new Student(firstName, lastName);

        for (Student pupil : roster) {

            if (pupil.toString().equals(temp.toString())) {
                duplicate = true;
            }
        }

        return duplicate;

    }

    // Returns the size of the roster ArrayList (how many students are enrolled
    // in course)
    public int rosterSize() {

        int temp = roster.size();

        return temp;
    }

    // Reduces the available seats the course has remaining
    private Course decrementSeats() {

        seats--;
        return this;
    }

    // Increases the available seats the course has
    private Course incrementSeats() {

        seats++;
        return this;

    }

    // Returns true if the available seats are more than 0 and false if there
    // are no more seats available
    public boolean hasSeatsAvailable() {

        if (seats > 0) {
            return true;
        }

        return false;
    }

    // Returns the numStudents with lastName that are enrolled in the course
    public int numStudentsWithLastName(String lastName) {

        int numStudents = 0;

        for (Student student : roster) {

            if (student.hasLastName(lastName)) {

                numStudents++;
            }

        }

        return numStudents;
    }

}
