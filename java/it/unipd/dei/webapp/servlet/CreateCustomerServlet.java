package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.dao.CustomerDAO;
import it.unipd.dei.webapp.dao.EmployeeDAO;
import it.unipd.dei.webapp.resource.Customer;
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


public class CreateCustomerServlet extends AbstractDatabaseServlet{


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
        operation = operation.substring(operation.lastIndexOf("customer") + 9);

        switch (operation) {

            case "login/":
                System.out.println("Login part");
                loginUser(req, res);
                break;

            case "register/":
                System.out.println("Register part");
                registerUser(req, res);
                break;

            default:
                writeError(res, ErrorCode.OPERATION_UNKNOWN);

        }
    }

        //loginUser, check if the user and the psw are in the DB
        public void loginUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
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
                    /*
                    If the email and password correspond to a customer the user will be sent to the homepage
                    to continue his order.
                    If the email and password correspond to a manager or an employee the user will be sent
                    to the appropiate page
                     */
                    Customer customer = new Customer(email, password);
                    Employee employee = new Employee(email, password);

                    int authenticationRes = CustomerDAO.authenticateCustomer(customer);
                    int authenticateResEmployee = EmployeeDAO.authenticateEmployee(employee);

                    if (authenticationRes == -1 && authenticateResEmployee == -1){

                        ErrorCode error = ErrorCode.WRONG_CREDENTIALS;
                        res.setStatus(error.getHTTPCode());
                        Message message = new Message(true, error.getErrorMessage());
                        req.setAttribute("message_credentials", message);
                        req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);

                    }
                    else {
                        if (authenticationRes == 0){

                            customer = CustomerDAO.getCustomer(customer.getEmail());
                            HttpSession session = req.getSession();
                            session.setAttribute("email", customer.getEmail());
                            res.sendRedirect(req.getContextPath() + "/jsp/homepage.jsp");
                        }
                        else{
                            employee = EmployeeDAO.getEmployee(employee.getEmail());

                            if(employee.getRole().equals("Manager")){

                                HttpSession session = req.getSession();
                                session.setAttribute("email", employee.getEmail());
                                res.sendRedirect(req.getContextPath() + "/jsp/manager-page.jsp");

                            }
                            else {

                                HttpSession session = req.getSession();
                                session.setAttribute("email", employee.getEmail());
                                res.sendRedirect(req.getContextPath() + "/jsp/employee-page.jsp");
                            }

                        }
                    }
                }
            } catch (NamingException | SQLException ex){

                writeError(res, ErrorCode.INTERNAL_ERROR);
                logger.error("stacktrace: ", ex);
            }

        }

        public void registerUser(HttpServletRequest req, HttpServletResponse res) throws IOException{
            try{

                //Take the registration parameters
                String email = req.getParameter("email");
                String name = req.getParameter("name");
                String surname = req.getParameter("surname");
                String password = req.getParameter("password");
                String phone = req.getParameter("phone");
                String addresses = req.getParameter("addresses");
                Boolean newsletter = req.equals("newsletter");
                String get_to_know = req.getParameter("get_to_know");
                String sizes = req.getParameter("sizes");
                String lifestyle = req.getParameter("lifestyle");



                if(email == null || email.equals("") ||
                    name == null || name.equals("") ||
                    surname == null || surname.equals("")||
                    password == null || password.equals("")||
                    phone == null || phone.equals("") ||
                    addresses == null || addresses.equals("") ||
                    get_to_know == null || get_to_know.equals("") ||
                    sizes == null || sizes.equals("") ||
                    lifestyle == null || lifestyle.equals("")){

                    ErrorCode error = ErrorCode.EMPTY_INPUT_FIELDS;
                    res.setStatus(error.getHTTPCode());
                    Message message = new Message(true, error.getErrorMessage());
                    req.setAttribute("message_empty", message);
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
                }
                //TODO Maybe it is necessary to give the user the possibility to re-insert psw

                else{

                    Customer customer = CustomerDAO.getCustomer(email);


                    if(customer != null){
                        ErrorCode error = ErrorCode.MAIL_ALREADY_USED;
                        res.setStatus(error.getHTTPCode());
                        Message message = new Message(true, error.getErrorMessage());
                        req.setAttribute("message_mail_used", message);
                        req.getRequestDispatcher("/jsp/register.jsp").forward(req, res);
                    }
                    else {
                        customer = new Customer(email, name, surname, password, phone, addresses,
                                newsletter, get_to_know, sizes, lifestyle);

                        int registrationResult = CustomerDAO.registerCustomer(customer);

                        //Correct registration
                        if (registrationResult == 0) {
                            req.getRequestDispatcher("/jsp/login.jsp").forward(req, res);
                        } else {

                            writeError(res, ErrorCode.INTERNAL_ERROR);
                        }
                    }
            }
        } catch(SQLException | NamingException | ServletException ex){
                writeError(res, ErrorCode.INTERNAL_ERROR);
           
                logger.error("stacktrace: " , ex);
            }

    }
}