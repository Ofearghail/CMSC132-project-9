package registrar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Jordon Farrell, jfarrel3, 117675665, section 0104
// I pledge on my honor that I have not given or received any unauthorized
// assistance on this assignment

// The RegistrationThread class implements Runnable and executes commands that
// are stored in the file with fileName. The class splits each line and stores
// it in the commandLine array the first element in the array is the desired
// Restistrar method to be called while the rest of the elements correspond to
// that methods required information. The Registrar object is used as a field to
// allow for manipulating the data and accessing its methods easier.

public class RegistrationThread implements Runnable {

    private String filename;
    private String[] commandLine;
    private Registrar registrar;

    // constructor for RegistrationThread
    public RegistrationThread(String filename, Registrar registrar) {

        this.filename = filename;
        this.registrar = registrar;

    }

    // thread1() calls the addNewCourse method of Registrar. commandLine[0] is
    // always "addcourse" commandLine[1] is always a string representing the
    // department, commandLine[2] is always the deparment number, commandLine[3]
    // is always the number of seats
    void thread1() {
        String department = commandLine[1];
        int number = Integer.parseInt(commandLine[2]);
        int seats = Integer.parseInt(commandLine[3]);

        registrar.addNewCourse(department, number, seats);

    }

    // thread2() calls the addToCourse method in Registrar. commandLine[0] is
    // always "addregistration", commandLine[1] is always a string representing
    // the department, commandLine[2] is always the deparment number,
    // commandLine[3] is always the firstName, commandLine[4] is always the
    // lastName
    void thread2() {
        String department = commandLine[1];
        int number = Integer.parseInt(commandLine[2]);
        String firstName = commandLine[3];
        String lastName = commandLine[4];

        registrar.addToCourse(department, number, firstName, lastName);

    }

    // the run() method uses a bufferedReader as well as a fileReader to access
    // the files with the commands and information. Then each line is extracted,
    // split and stored in commandLine. if the first element is "addcourse"
    // thread1() gets called if it is "addregistration" thread2() gets called
    @Override
    public void run() {
        BufferedReader bufferedReader;
        String line;
        String command1 = "addcourse";
        String command2 = "addregistration";

        // try catch block for the bufferedReader
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));

            // reads through each line splits it and sends the data to the
            // appropriate thread method
            while ((line = bufferedReader.readLine()) != null) {

                // removes any trailing or leading whitespaces and splits the
                // string and stores it into an array
                commandLine = line.trim().split("\\s+");

                if (commandLine[0].equals(command1)) {

                    thread1();

                }

                if (commandLine[0].equals(command2)) {

                    thread2();

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
