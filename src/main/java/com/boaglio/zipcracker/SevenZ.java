package com.boaglio.zipcracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

/**
 * Got from
 * https://memorynotfound.com/java-7z-seven-zip-example-compress-decompress-file/
 * 
 * @author Fernando Boaglio
 *
 */
public class SevenZ {

    public static void decompress(String in, File destination, String password) throws IOException {
        @SuppressWarnings("resource")
        SevenZFile sevenZFile = new SevenZFile(new File(in), password.getBytes(StandardCharsets.UTF_16LE));
        SevenZArchiveEntry entry;
        while ((entry = sevenZFile.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }
            File curfile = new File(destination, entry.getName());
            File parent = curfile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(curfile);
            byte[] content = new byte[(int) entry.getSize()];
            sevenZFile.read(content, 0, content.length);
            out.write(content);
            out.close();
        }
    }

}
