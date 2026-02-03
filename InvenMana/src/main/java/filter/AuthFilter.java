package filter;

import controller.AuthController;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.StaffSession;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Allow static assets and login endpoints
        if (path.startsWith("/assets/")
                || path.equals("/login")
                || path.equals("/logout")
                || path.equals("/index.html")) {
            chain.doFilter(request, response);
            return;
        }

        // Temporary: NO LOGIN REQUIRED
        // Redirect "/" to /book
        if (path.equals("/") || path.equals("/index.jsp")) {
            resp.sendRedirect(req.getContextPath() + "/book");
            return;
        }

        // Let everything through
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}


