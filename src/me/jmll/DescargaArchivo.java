package me.jmll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet({ "/DescargaArchivo", "/Descarga", "/Descarga.do", "/Download", "/Download.do" })
public class DescargaArchivo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // 0. obtiene la referencia a un objeto del logger
    private final static Logger LOGGER = Logger.getLogger(DescargaArchivo.class.getCanonicalName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DescargaArchivo() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doRequest(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setStatus(405);
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	ArrayList<String> errores = new ArrayList<String>();
        // a. Obtener el parámetro fileName
        String fileName = request.getParameter("archivo");
        // b. Validar que fileName viene en la solicitud
        if (fileName != null) {
            // c. Obtener el contexto del servlet en la variable servletContext
            ServletContext servletContext = request.getServletContext();
            
        } else {
        	errores.add(String.format("Se requiere el parámetro archivo a descargar"));
            request.setAttribute("errores", errores);
			request.getRequestDispatcher("/Admin.do").forward(request, response);
        }
    }

}
