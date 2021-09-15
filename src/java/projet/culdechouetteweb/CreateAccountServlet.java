/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.culdechouetteweb;


import DAO.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pojo.*;

/**
 *
 * @author manon
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateAccountServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateAccountServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        this.getServletContext().getRequestDispatcher( "/WEB-INF/createaccount.jsp" ).forward( request, response );
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html");
        try {
            
            PrintWriter out = response.getWriter();
            Joueur j1 = new Joueur();
            //System.out.println(request.getParameter("nomUtil") + " " + request.getParameter("mdp2") + " " + request.getParameter("age") + " " + request.getParameter("sexe") + " " + request.getParameter("ville"));
            String pseudo = request.getParameter("nomUtil");
            String mdp = request.getParameter("mdp2");
            j1.setPseudo(request.getParameter("nomUtil"));
            j1.setMotDePasse(request.getParameter("mdp2"));
            j1.setAge(Integer.parseInt(request.getParameter("age")));
            j1.setSexe(request.getParameter("sexe"));
            j1.setVille(request.getParameter("ville"));
            //System.out.println("factory");
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            //System.out.println("dao");
            DAO<Joueur> daoJoueur = factory.getDAOJoueur();
            //System.out.println("createdao");
            daoJoueur.create(j1);
            j1 = daoJoueur.Connextion(pseudo, mdp);
            request.setAttribute("Joueur", j1);
            this.getServletContext().getRequestDispatcher( "/WEB-INF/MenuJoueur.jsp" ).forward( request, response );
            
            
            } catch (DAOException ex) {
            Logger.getLogger(CreateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
