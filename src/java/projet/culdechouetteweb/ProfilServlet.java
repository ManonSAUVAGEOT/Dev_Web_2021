/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.culdechouetteweb;

import DAO.AbstractDAOFactory;
import DAO.CulDeChouetteDAOFactory;
import DAO.DAO;
import DAO.DAOException;
import DAO.PersistenceKind;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pojo.Joueur;

/**
 *
 * @author manon
 */
@WebServlet(name = "ProfilServlet", urlPatterns = {"/ProfilServlet"})
public class ProfilServlet extends HttpServlet {

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
            out.println("<title>Servlet ProfilServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfilServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html");
        try {
            Joueur j1 = new Joueur();
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            DAO<Joueur> daoJoueur = factory.getDAOJoueur();
            String mdp = request.getParameter("mdp");
            //System.out.println(mdp);
            String pseudo = request.getParameter("nomUtil");
            //System.out.println(pseudo);
            if(!"none".equals(mdp)){
                j1 = daoJoueur.Connextion(pseudo, mdp);
            }else{
                j1 = daoJoueur.trouveJoueur(pseudo);
            }
            request.setAttribute("Joueur", j1);
            this.getServletContext().getRequestDispatcher( "/WEB-INF/MenuJoueur.jsp" ).forward( request, response );
            
        } catch (DAOException ex) {
            Logger.getLogger(ProfilServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Joueur j1 = new Joueur();
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            DAO<Joueur> daoJoueur = factory.getDAOJoueur();
            String mdp = request.getParameter("mdp");
            //System.out.println(mdp);
            String pseudo = request.getParameter("nomUtil");
            //System.out.println(pseudo);
            if(!"none".equals(mdp)){
                j1 = daoJoueur.Connextion(pseudo, mdp);
            }else{
                j1 = daoJoueur.trouveJoueur(pseudo);
            }
            request.setAttribute("Joueur", j1);
            this.getServletContext().getRequestDispatcher( "/WEB-INF/MenuJoueur.jsp" ).forward( request, response );
            
        } catch (DAOException ex) {
            Logger.getLogger(ProfilServlet.class.getName()).log(Level.SEVERE, null, ex);
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
