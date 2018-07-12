package com.boaglio.zipcracker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Logger
 * 
 * not used because close and open I/O operation is slow
 * 
 * @author Fernando Boaglio
 *
 */
public class Logger {
    public static void log(String message) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("output.txt", true), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(message + "\n");
        out.close();
    }
}