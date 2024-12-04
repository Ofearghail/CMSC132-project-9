package registrar;

import java.util.HashSet;
import java.util.Objects;

// Jordon Farrell, jfarrel3, 117675665, section 0104
// I pledge on my honor that I have not given or received any unauthorized
// assistance on this assignment

// The Student class is a class that stores the information of students
// firstNames, lastNames, and the number of courses they are enrolled.

public class Student {

    private String firstName;
    private String lastName;
    private HashSet<Course> courses = new HashSet<>();

    // Basic initial constructor

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // toString method concatenates firstName and lastName and returns it
    public String toString() {

        String temp = "First name is " + firstName + " " + "Last name is "
                + lastName;

        return temp;

    }

    // Method returns false if courses size is 0 or if the course
    // objects inside the ArrayList do not have the same department or number
    public boolean checkCourses(String course) {
        boolean checkCourses = false;

        if (courses.size() != 0) {
            for (Course course2 : courses) {

                if (course2.toString() == course) {

                    checkCourses = true;

                }

            }
        }

        return checkCourses;

    }

    // returns the size of the courses array which tells how many courses a
    // student is enrolled in
    public int numberOfCourses() {

        int temp = courses.size();

        return temp;

    }

    // Adds a course to the courses ArrayList and returns the student object to
    // allow chained calls
    public Student addCourse(Course course) {

        courses.add(course);

        return this;

    }

    // Removes a course from the courses ArrayList and returns the student
    // object to allow chained calls
    public Student removeCourse(Course course) {

        courses.remove(course);
        return this;

    }

    // Equals method that compares two student objects' firstName and lastName
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        return Objects.equals(firstName, other.firstName)
                && lastName == other.lastName;
    }

    // returns true if the lastName matches the students lastName field
    public boolean hasLastName(String lastName) {
        boolean hasName = false;

        if (lastName == this.lastName) {

            hasName = true;
        }

        return hasName;

    }

}
