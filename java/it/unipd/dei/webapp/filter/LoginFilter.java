package it.unipd.dei.webapp.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter extends AbstractFilter {

    final static Logger logger = LogManager.getLogger(LoginFilter.class);

    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        String loginURI = req.getContextPath() + "/jsp/login.jsp";

        boolean loggedIn = session != null && session.getAttribute("email") != null;
        boolean loginRequest = req.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect(loginURI);
        }
    }
}
