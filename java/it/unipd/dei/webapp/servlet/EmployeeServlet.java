package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.EmployeeDAO;
import it.unipd.dei.webapp.resource.Employee;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.utils.ErrorCode;
import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EmployeeServlet extends AbstractDatabaseServlet{

    public Logger logger;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String operation = req.getParameter("operation");

        if(operation != null && operation.equals("logout")){

            // invalidate user session
            req.getSession().invalidate();
            res.sendRedirect(req.getContextPath() + "/jsp/homepage.jsp");

        }
        else{
            writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String operation = req.getRequestURI();
        operation = operation.substring(operation.lastIndexOf("employee") + 9);

        switch (operation) {

            case "login/":
                loginEmployee(req, res);
                break;

            case "register/":
                registerEmployee(req, res);
                break;

            default:
                writeError(res, ErrorCode.OPERATION_UNKNOWN);
        }
    }

    //login the employee
    public void loginEmployee(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        try{
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if(email == null || email.equals("")){

                ErrorCode error = ErrorCode.EMAIL_MISSING;
                res.setStatus(error.getHTTPCode());
                Message message = new Message(true, error.getErrorMessage());
                req.setAttribute("message_email", message );
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

            }
            else if (password == null || password.equals("")){

                ErrorCode error = ErrorCode.PASSWORD_MISSING;
                res.setStatus(error.getHTTPCode());
                Message message = new Message( true, error.getErrorMessage());
                req.setAttribute("message_password", message);
                req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
            }
            else{

                Employee employee = new Employee(email, password);

                int authenticationRes = EmployeeDAO.authenticateEmployee(employee);

                if (authenticationRes == -1){

                    ErrorCode error = ErrorCode.WRONG_CREDENTIALS;
                    res.setStatus(error.getHTTPCode());
                    Message message = new Message(true, error.getErrorMessage());
                    req.setAttribute("message_credentials", message);
                    req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
                }
                else{
                    employee = EmployeeDAO.getEmployee(employee.getEmail());

                    HttpSession session = req.getSession();
                    session.setAttribute("email", employee.getEmail());
                    res.sendRedirect(req.getContextPath()+"/jsp/homepage.jsp");
                }
            }
        } catch (NamingException | SQLException ex){

            writeError(res, ErrorCode.INTERNAL_ERROR);
            //logger.error("stacktrace: ", ex);
        }

    }

    public void registerEmployee(HttpServletRequest req, HttpServletResponse res) throws IOException{
        try{

            //Take the registration parameters
            String email = req.getParameter("email");
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            String role = req.getParameter("role");

            if(email == null || email.equals("") ||
                    name == null || name.equals("") ||
                    surname == null || surname.equals("")||
                    password == null || password.equals("")||
                    phone == null || phone.equals("") ||
                    role == null || role.equals("")){


                ErrorCode error = ErrorCode.EMPTY_INPUT_FIELDS;
                res.setStatus(error.getHTTPCode());
                Message message = new Message(true, error.getErrorMessage());
                req.setAttribute("message_empty", message);
                req.getRequestDispatcher("/jsp/create-employee.jsp").forward(req, res);
            }

            else{
                Employee employee = EmployeeDAO.getEmployee(email);

                if(employee != null){
                    ErrorCode error = ErrorCode.MAIL_ALREADY_USED;
                    res.setStatus(error.getHTTPCode());
                    Message message = new Message(true, error.getErrorMessage());
                    req.setAttribute("message_mail_used", message);
                    req.getRequestDispatcher("/jsp/create-employee.jsp").forward(req, res);
                }
                else{
                    employee = new Employee(email, name, surname, password, phone, role);

                    int registrationResult = EmployeeDAO.registerEmployee(employee);
                    //Correct registration
                    if(registrationResult == 0){
                        req.getRequestDispatcher("/jsp/manager-page.jsp").forward(req, res);
                    }
                    else{
                        writeError(res, ErrorCode.INTERNAL_ERROR);
                    }
                }
            }
        } catch(SQLException | NamingException | ServletException ex){
            writeError(res, ErrorCode.INTERNAL_ERROR);
            //logger.error("stacktrace: " , ex);
        }

    }
}




