package me.jmll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import me.jmll.io.NIO2RecursiveDir;

/**
 * Servlet implementation class ProcesaSaludo
 */
@WebServlet({ "/Lista", "/Lista.do", "/ListaArchivos.do" })
public class ListaArchivos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(ListaArchivos.class.getCanonicalName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaArchivos() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtiene parámetros de la solcitud
		String path = request.getParameter("path");
		// Genera ArrayList para guardar errores 
		ArrayList<String> errores = new ArrayList<String>();
		
		// Validar si el path tiene contenido
		if (path != null){
			// 3(a) Mediante java.nio.Path, 
			// crea un objeto basado en el parámetro path
			Path dir = Paths.get(path);
			
			// 3(b) Crea un objeto de tipo List que almacene objetos
			// java.nio.Path y sea ArrayList.
			List<Path> paths = new ArrayList<Path>();
			
			// 3(c) Valida que exista el objeto Path creado
			// en el paso 3(a) exista y sea directorio para
			// llamar a NIO2RecursiveDir.walkDir()
			if (dir != null && Files.exists(dir)){
				NIO2RecursiveDir.walkDir(dir, paths);
			} else {
				errores.add(String.format("No se encuentra directorio. %s", path));
				// 3(d) asigna el objeto errores en el 
				// atributo de la request con el nombre "errores"
				request.setAttribute("errores",errores);
				request.getRequestDispatcher("/Admin.do").forward(request, response);
			}
			// Agregar los resultados como atributo en el request
			request.setAttribute("path", path);
			request.setAttribute("paths", paths);
			
			// 3(e) Obtiene Requestdispatcher a DirView.jsp
			// y reenvía con el método forward
			request.getRequestDispatcher("WEB-INF/views/DirView.jsp").forward(request, response);
			
		} else {
			errores.add("Parámetro PATH requerido.");
			request.setAttribute("errores", errores);
			LOGGER.log(Level.WARNING, "Parámetro PATH requerido." );
			request.getRequestDispatcher("/Admin.do").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
