package registrar;

import java.util.Collection;
import java.util.HashSet;

// Jordon Farrell, jfarrel3, 117675665, section 0104
// I pledge on my honor that I have not given or received any unauthorized
// assistance on this assignment

// The Registrar class extends the Thread class and is used to store a HashSet
// of students as well as a HashSet of courses called registrar there are
// methods to add, remove, see how many classes a student is taking as well as
// to drop students from a course.

public class Registrar {

    private HashSet<Course> registrar;
    private HashSet<Student> students;

    private int maxCoursesPerStudent;

    // Constructor for the Registrar class
    public Registrar(int maxCoursesPerStudent) {

        this.maxCoursesPerStudent = maxCoursesPerStudent;
        this.registrar = new HashSet<>();
        this.students = new HashSet<>();

    }

    // addNewCourse method creates a course object, stores it inside
    // HashSet<Course> registrar and returns the calling object. The method will
    // check if the course with department and number already exists and return
    // false if it does. This method is synchronized to avoid threads from
    // accessing registrar at the same time
    public synchronized Registrar addNewCourse(String department, int number,
            int numSeats) {

        Course course = new Course(department, number, numSeats);
        boolean courseExists = false;

        // throws exception for any invalid parameter
        if (department == "" || number < 0 || numSeats < 0) {
            throw new IllegalArgumentException();
        }

        for (Course courses : registrar) {

            if (courses.equals(course)) {

                courseExists = true;
            }

        }

        // adds course to registrar if it has not been added already
        if (courseExists == false) {

            registrar.add(course);

        }

        return this;
    }

    // Removes a course from the registrar if the course exists and returns true
    // false if the course cannot be found
    public boolean cancelCourse(String department, int number) {
        Course tempCourse = new Course(department, number);
        boolean found = false;
        boolean removed = false;

        if (department == "" || number < 0) {
            throw new IllegalArgumentException();
        }

        // Iterates through the array
        for (Course course : registrar) {

            if (course.equals(tempCourse)) {

                tempCourse = course;
                // registrar.remove(tempCourse);
                found = true;
            }

        }

        if (found) {

            registrar.remove(tempCourse);
            removed = true;
        }

        return removed;

    }

    // returns the size of the HashSet<Course> registrar
    public int numCourses() {
        return registrar.size();
    }

    // Adds a student to a course by iterating through the registrar until it
    // finds the course. If the course is not found the method returns false. If
    // found it then checks to see if the student is already enrolled in that
    // course. If so the method returns false. Only if the course was found the
    // the student is not enrolled will it add the student and return true.
    public synchronized boolean addToCourse(String department, int number,
            String firstName, String lastName) {

        Course course = new Course(department, number);
        Student student = new Student(firstName, lastName);
        boolean added = false;

        if (department == "" || number < 0 || firstName == ""
                || lastName == "") {
            throw new IllegalArgumentException();
        }

        // if HashSet<Student> students size is 0 add student
        if (students.size() == 0) {

            students.add(student);
        }

        // if student is not found in students add student
        if (isNewStudent(student)) {
            students.add(student);
        }

        // stores student information from HashSet<Student> students to allow
        // editing of its courses field will be null if student is not found
        student = getStudent(student);
        // Stores the Course with department and number to allow editing of its
        // roster field will be null if course is not found
        course = getCourse(course);

        // checks if course is null or student is null
        if (course == null || student == null) {
            added = false;
            // checks if the numberofCourses is less than maxCoursesPerStudent
        } else if (student.numberOfCourses() >= maxCoursesPerStudent) {
            added = false;
            // checks if student is already enrolled and the course has
            // available seats
        } else if (!course.checkDuplicate(student)
                && course.hasSeatsAvailable()) {

            course.addStudent(student);
            student.addCourse(course);
            added = true;

        }

        return added;
    }

    // returns the number of students enrolled in a course by searching for a
    // course with department and number then assigns its roster size to
    // numStudents if no course if found numStudents will be -1
    public int numStudentsInCourse(String department, int number) {
        Course course = new Course(department, number);
        int numStudents = -1;

        if (department == "" || number < 0) {
            throw new IllegalArgumentException();
        }

        for (Course course1 : registrar) {

            if (course.equals(course1)) {

                numStudents = course1.rosterSize();

            }

        }

        return numStudents;

    }

    // searches for a course with department and number if found the method then
    // searches through the courses roster for a student with lastName. if no
    // course or student is found the method will return -1
    public int numStudentsInCourseWithLastName(String department, int number,
            String lastName) {

        Course tempCourse = new Course(department, number);
        int numStudents = -1;
        Course course = getCourse(tempCourse);

        if (department == "" || number < 0 || lastName == "") {
            throw new IllegalArgumentException();
        }

        if (course != null) {
            numStudents = course.numStudentsWithLastName(lastName);
        }

        return numStudents;
    }

    // searches through registrar for a course if found the method searches the
    // roster for a student with firstName lastName and returns true.
    public boolean isInCourse(String department, int number, String firstName,
            String lastName) {

        Student student;
        Course course;
        boolean inCourse = false;

        if (department == "" || number < 0 || firstName == ""
                || lastName == "") {
            throw new IllegalArgumentException();
        }

        // if a course with department number is not found course will be null
        course = getCourse(new Course(department, number));
        // if a student with firstName lastName is not found student will be
        // null
        student = getStudent(new Student(firstName, lastName));

        if (course != null && student != null) {

            if (course.checkDuplicate(student)) {

                inCourse = true;

            }

        }

        return inCourse;
    }

    // searches for a student with firstName lastName in students if found the
    // method sets coursesTaking equal to the size of the students courses field
    // if the student doesnt exists coursesTaking remains 0
    public int howManyCoursesTaking(String firstName, String lastName) {
        Student student;
        int coursesTaking = 0;

        if (firstName == "" || lastName == "") {
            throw new IllegalArgumentException();
        }

        // if student is not found student will be null
        student = getStudent(new Student(firstName, lastName));

        if (student != null) {
            coursesTaking = student.numberOfCourses();
        }

        return coursesTaking;
    }

    // if a course with department number has a student with firstName lastName
    // the student will be removed as well as the course being removed from the
    // students courses field if neither the student or the course can be found
    // the method will return false. if student is removed successfully the
    // method will return true
    public boolean dropCourse(String department, int number, String firstName,
            String lastName) {

        Course course;
        Student student;
        boolean dropped = false;

        if (department == "" || number < 0 || firstName == ""
                || lastName == "") {
            throw new IllegalArgumentException();
        }

        course = getCourse(new Course(department, number));
        student = getStudent(new Student(firstName, lastName));

        // if course and student are not null then both were found
        if (course != null && student != null) {

            if (course.checkDuplicate(firstName, lastName)) {

                course.removeStudent(student);
                student.removeCourse(course);
                dropped = true;
            }

        }

        return dropped;
    }

    // Removes the student from all courses in registrar and from students then
    // returns true if the student or course isnt found the method returns false
    public boolean cancelRegistration(String firstName, String lastName) {
        Student student;
        boolean cancelled = false;

        if (firstName == "" || lastName == "") {
            throw new IllegalArgumentException();
        }

        // if a student with firstName and lastName are not present the value
        // will be null
        student = getStudent(new Student(firstName, lastName));

        if (student != null) {

            for (Course course : registrar) {

                if (course.checkDuplicate(student)) {

                    course.removeStudent(student);

                }
            }

            // student gets cancelled
            students.remove(student);
            cancelled = true;
        }

        return cancelled;
    }

    // Iterates through the students ArrayList until it finds the Student of the
    // student that matches the input student parameter
    public Student getStudent(Student student) {
        // String studentTemp = student.toString();
        // String studentTemp1 = null;

        Student returnedStudent = null;

        for (Student student1 : students) {
            // studentTemp1 = student1.toString();

            if (student1.toString().equals(student.toString())) {
                returnedStudent = student1;
            }

        }

        return returnedStudent;

    }

    // Iterates through the registrar ArrayList until it finds the index of the
    // course that matches the input course parameter and returns int index
    private Course getCourse(Course course) {

        Course returnedCourse = null;

        for (Course course1 : registrar) {

            if (course1.equals(course)) {

                returnedCourse = course1;

            }

        }

        return returnedCourse;

    }

    // Iterates through the student HashSet and returns false if there is no
    // student that matches the input parameter
    private boolean isNewStudent(Student student) {
        boolean newStudent = false;

        if (student == null) {
            throw new IllegalArgumentException();
        }

        for (Student students : students) {

            if (!students.equals(student)) {
                newStudent = true;
            } else {
                newStudent = false;
            }

        }

        return newStudent;
    }

    // This method creates and stores Threads into Threads[] threads for each
    // entry in filenames and executes all of the threads
    public void doRegistrations(Collection<String> filenames) {
        Thread registrationThread;
        Thread[] threads = new Thread[filenames.size()];
        int i = 0;

        for (String filename : filenames) {

            // creates a Thread
            registrationThread = new Thread(
                    new RegistrationThread(filename, this));
            // stores registrationThread into threads array
            threads[i] = registrationThread;
            i++;

        }

        // calls start() on all threads in the threads array
        for (int j = 0; j < threads.length; j++) {

            threads[j].start();

        }

        // calls join() on all the threads in the threads array
        for (int j = 0; j < threads.length; j++) {

            try {
                threads[j].join();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

    }

}
