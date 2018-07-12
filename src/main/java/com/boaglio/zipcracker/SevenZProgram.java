package com.boaglio.zipcracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Got from
 * https://memorynotfound.com/java-7z-seven-zip-example-compress-decompress-file/
 * 
 * Required: JDK 6u181+, 7u171+, 8u161+, 9b148+
 * 
 * @author Fernando Boaglio
 * 
 */
public class SevenZProgram {

    private static final int NUMBER_LIMIT = 2020;

    private static final String OUTPUT_CRACKER = "output-" + System.currentTimeMillis() + ".txt";

    private static final String OUTPUT_DIRECTORY = "/tmp";

    private static final String FILES_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main" + File.separator + "resources" + File.separator;

    // password file is abc123
    private static String file7zip = FILES_DIR + "secret.7z";

    @SuppressWarnings("unused")
    public static void main(String... args) throws IOException {

        PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_CRACKER, true), true);

        new File(OUTPUT_DIRECTORY).mkdirs();

        int count = 0;
        List<String> allWords = new ArrayList<>();
        for (Words word : Words.values()) {
            allWords.add(word.toString());
            allWords.add(word.toString().substring(0, 1).toUpperCase()
                    + word.toString().substring(1, word.toString().length()));
        }

        // estimate
        int total = 0;
        for (String wordStr : allWords) {
            for (Symbols symbols : Symbols.values()) {
                for (int n = 0; n < 2020; n++) {
                    total++;
                }
            }
        }

        System.out.println(" Total combinations = " + total + "\n");
        out.write(" Total combinations = " + total + "\n");

        // your password is like:
        //
        // word + number + symbol
        //
        for (String word : allWords) {
            for (Symbols symbols : Symbols.values()) {
                for (int n = 0; n <= NUMBER_LIMIT; n++) {
                    double pct = (count * 100d) / total;
                    String pwd = word.toString() + String.valueOf(n) + symbols.value();
                    out.write(count + " / " + total + " " + pct + "% > " + pwd + "\n");
                    count++;
                    extractFileWithPwd(pwd, out);
                }
            }
        }

        out.write("Total = " + count + "\n");

        out.close();
    }

    private static void extractFileWithPwd(String passwordToTest, PrintWriter out) {

        try {

            SevenZ.decompress(file7zip, new File(OUTPUT_DIRECTORY), passwordToTest);

            out.write(" \\o/ ");
            out.write("Right password: " + passwordToTest);
            out.write(" \\o/ ");

            System.out.println(" \\o/ ");
            System.out.println("Right password: " + passwordToTest);
            System.out.println(" \\o/ ");

            out.close();

            System.exit(0);

        } catch (IOException e) {
            // Wrong password
            if (passwordToTest.equals("abc123"))
                e.printStackTrace();
        }
    }
}
