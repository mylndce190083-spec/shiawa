/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author BA LIEM
 */
public class FileUpload {

    public static final String UPLOAD_DIR = "D:/SWP391/shiawa/images";

    public static void saveFile(Part input, File output) throws IOException {
        try ( OutputStream os = new FileOutputStream(output);  InputStream in = input.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
