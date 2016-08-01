package com.example;


import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class ClassFinder {

    private String mPattern;
    private boolean mDebug = false;
    private String mPath;
    private String fileType = ".java";
    private List<File> mJavaFiles = new ArrayList<>();


    /**
     * Turns debug messages on
     */
    public void debugOn() {
        mDebug = true;
    }

    /**
     * Turns debug messages off (no, really why should you?)
     */
    public void debugOff() {
        mDebug = false;
    }


    /**
     * This is poor man's debug method
     * Displays debug msg only if mDebug is true
     * TODO: hide from all the people for making this this
     *
     * @param msg stuff to print
     */
    private void debug(String msg) {
        if (mDebug) System.out.println("DEBUG: " + msg);
    }

    public void run(String pattern, String path) {
        mPattern = pattern;
        mPath = path;

        debug("Pattern is: " + mPattern);
        debug("Path is: " + mPath);

        File startDir = new File(path);
        if (!startDir.isDirectory()) {
            System.out.println("Error: cant access path!");
            System.exit(1);
        }

        findJavaFiles(mPath);

        try (
                Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("classes.txt"))))
        {

            for (File file : mJavaFiles) {
                String pckg = extractPackage(file);
                if (!pckg.equals("")) {
                    writer.write(pckg+"\n");
                }
            }
            writer.write("something");
        } catch (IOException e){
            e.printStackTrace();
        }




    }

    /**
     * Scans java source code to extract package and gets back
     * the amazing a.b.FooBarBaz :/
     * @param javaFile file to scan
     * @return a.b.FooBarBaz (if a.b does not exists then FooBarBaz)
     * @since madness begun
     */
    public String extractPackage(File javaFile) {
        String pckg = "";
        try {
            List<String> lines = Files.readAllLines(javaFile.toPath());
            for (String line : lines) {
                if(line.startsWith("package ")) {

                    String extract = line.replace("package ", "");
                    int semiColon = extract.indexOf(';');
                    if(semiColon > 0) {
                        pckg = extract.substring(0,semiColon);

                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        String fileName=javaFile.getName();
        String className = fileName.substring(0,fileName.indexOf('.'));
        if(pckg.equals("")) return className;
        return  pckg + "." + className;
    }


    /**
     * Searches for java files recursively
     * Stores findings to mJavaFiles
     * @param path file path where to start searching
     */
    public void findJavaFiles(String path) {

        try {
            File directory = new File(path);
            File[] fList = directory.listFiles();

            for (File file : fList) {
                if (file.isFile()) {

                    // hack out file extension
                    String extension = "";
                    String fileName = file.getName();
                    int lastDot = fileName.lastIndexOf('.');

                    if (lastDot > 0) {
                        extension = fileName.substring(lastDot + 1);
                        if (extension.equals("java")) {
                            mJavaFiles.add(file);
                        }
                    }

                } else if (file.isDirectory()) {
                    findJavaFiles(file.getAbsolutePath());
                }
            }
        } catch (NullPointerException e) {
            // TODO: @someday, investigate fList possibilty for nullpointer exception
            System.out.println("Yuck! File scan error!");
            e.printStackTrace();
        }
    }
}

