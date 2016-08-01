/**
 */

import com.example.ClassFinder;

public class Main {

    public static void main(String[] args) {

        // Added additional feature to generate a.b.FooBarBaz file
        // if started with -g --generate-class-list filename

        // TODO get pattern and path from system args
        String pattern = "test";
        String path = "{try to be funny}";

        ClassFinder cf = new ClassFinder();

        // what are you doing Dave?
        cf.debugOn();

        // profit
        cf.run(pattern, path);

    }
}
