/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
@WebServlet("/gestorLogout")
public class GestorLogout extends HttpServlet{//creo que tendré que hacer 1 por cada usuario, por el hecho de tener + de 1 pág de perfil... a menos que hubiera hecho un menu general... pero no me convenía xD

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getSession().getAttribute("codigo"));
        request.getSession().removeAttribute("codigo");        
        request.getSession().invalidate();
        System.out.println(request.getSession().getAttribute("codigo"));//Si funciona xD porque se muestra que el atributo es null xD cuando antes de pasar por aquí no lo era xD

        response.sendRedirect(request.getContextPath() + "/Login.jsp");
    }            
}
