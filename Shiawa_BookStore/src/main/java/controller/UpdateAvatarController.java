/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.io.File;
import java.nio.file.Paths;
import jakarta.servlet.http.Part;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "UpdateAvatarController", urlPatterns = {"/update-avatar"})
@MultipartConfig
public class UpdateAvatarController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer cus = (Customer) session.getAttribute("customer");

        if (cus == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Part filePart = request.getPart("avatarFile");

        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "Vui lòng chọn ảnh!");
            request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
            return;
        }
        
        if (filePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("error", "Ảnh không được vượt quá 2MB!");
            request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
            return;
        }

        String contentType = filePart.getContentType();
        if (!contentType.startsWith("image/")) {
            request.setAttribute("error", "Chỉ được upload file ảnh!");
            request.getRequestDispatcher("/WEB-INF/home/profile.jsp").forward(request, response);
            return;
        }

        String webappPath = getServletContext().getRealPath("/");
        File webappDir = new File(webappPath);

        File projectRoot = webappDir.getParentFile().getParentFile();
        String uploadPath = projectRoot.getAbsolutePath()
                + File.separator + "ShiawaUploads"
                + File.separator + "avatar";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = Paths.get(filePart.getSubmittedFileName())
                .getFileName()
                .toString();

        String newFileName = System.currentTimeMillis() + "_" + fileName;

        filePart.write(uploadPath + File.separator + newFileName);

        String avatarPath = "avatar/" + newFileName;

        CustomerDAO dao = new CustomerDAO();
        dao.updateAvatar(cus.getId(), avatarPath);

        cus.setAvatar(avatarPath);
        session.setAttribute("customer", cus);
        System.out.println("Upload path: " + uploadPath);

        response.sendRedirect("profile");
    }
}
