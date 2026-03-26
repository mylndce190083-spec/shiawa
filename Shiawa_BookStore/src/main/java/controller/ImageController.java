/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;



/**
 *
 * @author Lenovo
 */
@WebServlet(name = "ImageController", urlPatterns = {"/image"})
public class ImageController extends HttpServlet {

    private String uploadRoot;
    
    @Override
    public void init() throws ServletException {
        // lấy path webapp
        String webappPath = getServletContext().getRealPath("/");
        File webappDir = new File(webappPath);

        // đi lên 2 cấp để về root project
        File projectRoot = webappDir.getParentFile().getParentFile();

        uploadRoot = projectRoot.getAbsolutePath() + File.separator + "ShiawaUploads";

        System.out.println("Upload root: " + uploadRoot);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileParam = request.getParameter("file");

        if (fileParam == null || fileParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // decode URL
        fileParam = java.net.URLDecoder.decode(fileParam, "UTF-8");

        File file = new File(uploadRoot, fileParam);

        System.out.println("Reading file: " + file.getAbsolutePath());

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getName());

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
