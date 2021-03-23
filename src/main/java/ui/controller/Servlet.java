package ui.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.db.DierDB;
import domain.model.Dier;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DierDB dierDB = new DierDB();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        System.out.println();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination;
        String command = request.getParameter("command");
        if (command == null || command.isEmpty())
            command = "";
        switch (command) {
            case "voegToeForm":
                destination = getVoegToeForm(request, response);
                break;
            case "overzicht":
                destination = getOverzicht(request, response);
                break;
            case "voegToe":
                destination = getVoegToe(request, response);
                break;
            default:
                destination = getHome(request, response);
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(destination);
        view.forward(request, response);

    }



    private String getHome(HttpServletRequest request, HttpServletResponse response) {
        return "index.jsp";
    }

    private String getVoegToeForm(HttpServletRequest request, HttpServletResponse response) {
        return "voegToe.jsp";
    }

    private String getVoegToe(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors = new ArrayList<String>();
        Dier dier = new Dier();
        setNaam(dier, request, errors);
        setSoort(dier, request, errors);
        setVoedsel(dier, request, errors);
        if (errors.size() == 0) {
            try {

                dierDB.voegToe(dier);
                return getOverzicht(request, response);
            }
            catch (IllegalArgumentException exc) {
                request.setAttribute("error", exc.getMessage());
                return "voegToe.jsp";
            }
        }
        else {
            request.setAttribute("errors", errors);
            return "voegToe.jsp";
        }
    }

    private void setNaam(Dier dier, HttpServletRequest request, ArrayList<String> errors) {
        String naam = request.getParameter("naam");
        try {
            dier.setNaam(naam);
            request.setAttribute("naamClass", "has-success");
            request.setAttribute("naamPreviousValue", naam);
        }
        catch (IllegalArgumentException exc) {
            errors.add(exc.getMessage());
            request.setAttribute("naamClass", "has-error");
        }
    }

    private void setSoort(Dier dier, HttpServletRequest request, ArrayList<String> errors) {
        String soort = request.getParameter("soort");
        try {
            dier.setSoort(soort);
            request.setAttribute("soortClass", "has-success");
            request.setAttribute("soortPreviousValue", soort);
        }
        catch (IllegalArgumentException exc) {
            errors.add(exc.getMessage());
            request.setAttribute("soortClass", "has-error");
        }
    }

    private void setVoedsel(Dier dier, HttpServletRequest request, ArrayList<String> errors) {
        int voedsel;
        if(request.getParameter("voedsel").isBlank()){
            voedsel = -1;
        }else{
             voedsel = Integer.parseInt(request.getParameter("voedsel"));
        }
        try {
            dier.setVoedsel(voedsel);
            request.setAttribute("voedselClass", "has-success");
            request.setAttribute("voedselPreviousValue", voedsel);
        }
        catch (IllegalArgumentException exc) {
            errors.add(exc.getMessage());
            request.setAttribute("voedselClass", "has-error");
        }
    }

    private String getOverzicht(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("alleDieren", dierDB.vindAlle());
        return "overzicht.jsp";
    }
}
