/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import model.Account;
import model.Customer;

/**
 *
 * @author BA LIEM
 */
@WebServlet(name = "StaffAvatarController", urlPatterns = {"/staff-avatar"})
@MultipartConfig
public class StaffAvatarController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        Account account = (Account) session.getAttribute("user");
//
//        if (account == null) {
//            response.sendRedirect("login");
//            return;
//        }
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        if (!user.getRole().equalsIgnoreCase("Admin")
                && !user.getRole().equalsIgnoreCase("Inventory")) {
            response.sendRedirect("home");
            return;
        }

        // ===== lấy file
        Part filePart = request.getPart("avatarS");

        // ===== validate file
        // Kiểm tra có chọn file không
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("error", "Vui lòng chọn ảnh!");
            request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp").forward(request, response);
            return;
        }

        // Kiểm tra dung lượng (ví dụ: tối đa 2MB)
        if (filePart.getSize() > 2 * 1024 * 1024) {
            request.setAttribute("error", "Ảnh không được vượt quá 2MB!");
            request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp").forward(request, response);
            return;
        }

        // Kiểm tra loại file
        String contentType = filePart.getContentType();
        if (!contentType.startsWith("image/")) {
            request.setAttribute("error", "Chỉ được upload file ảnh!");
            request.getRequestDispatcher("/WEB-INF/profile/staff-profile.jsp").forward(request, response);
            return;
        }

        // ===== lưu file
//        String uploadPath = "D:/ShiawaUploads/avatar";
        String webappPath = getServletContext().getRealPath("/");
        File webappDir = new File(webappPath);

// đi lên 2 cấp
        File projectRoot = webappDir.getParentFile().getParentFile();
        String uploadPath = projectRoot.getAbsolutePath()
                + File.separator + "ShiawaUploads"
                + File.separator + "avatarStaff";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = Paths.get(filePart.getSubmittedFileName())
                .getFileName()
                .toString();

        String newFileName = System.currentTimeMillis() + "_" + fileName;

        filePart.write(uploadPath + File.separator + newFileName);

        String avatarPath = "avatarStaff/" + newFileName;

        // ===== update db
        AccountDAO dao = new AccountDAO();
        dao.updateAvatar(user.getId(), avatarPath);

        user.setAvatar(avatarPath);
        session.setAttribute("account", user);
        System.out.println("Upload path: " + uploadPath);

        response.sendRedirect("staff-profile");
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
