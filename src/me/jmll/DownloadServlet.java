package me.jmll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 0. obtiene la referencia a un objeto del logger
	private final static Logger LOGGER = Logger.getLogger(DownloadServlet.class.getCanonicalName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
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
		// a. Obtener el parámetro fileName
		String fileName = request.getParameter("fileName");// escribe tu código aquí
		// b. Validar que fileName viene en la solicitud
		if (fileName != null) {
			// c. Obtener el contexto del servlet en la variable servletContext
			ServletContext servletContext =  this.getServletContext();// escribe tu código aquí
			
			// d. Obtener el context init param pathDestino y crear el path completo
			String fullPath = servletContext.getInitParameter("pathDestino")+"/"+fileName;// escribe tu código aquí
			try {
				// Crea un objeto File con el path solicitado
				File fileDownload = new File(fullPath);
				if (fileDownload.exists()) {
					// e. Crear instancias de FileInputStrem a partir del archivo a descargar
					//    y OutputStream a partir del objeto response
					try (FileInputStream fileInputStream = new FileInputStream(fileDownload); // escribe tu código aquí;
							OutputStream outputStream =  response.getOutputStream();){// escribe tu código aquí) {

						// f. Obtener MimeType del Archivo
						String mimeType = fileName.substring(fileName.indexOf("."),fileName.length());
						// escribe tu código aquí
						
						if (mimeType == null)
							mimeType = "application/octet-stream"; // escribe tu código aquí
						LOGGER.log(Level.INFO, "MimeType identificado: {0}", new Object[] { mimeType });
						
						// g. Crear response: MimeType
					 	// escribe tu código aquí
						response.setContentType(mimeType);
						response.setContentLengthLong(fileDownload.length());
						// h. Crear response: Headers
						response.setHeader("Content-Dispotition", "attachment; filename=" + fileName);
					    // escribe tu código aquí
						LOGGER.log(Level.INFO, "Iniciando transferencia de {0} {1} Bytes a {2}", new Object[] {
								fileDownload.getName(), fileDownload.length(), request.getRemoteAddr() });
						// Transmite datos
						byte[] buffer = new byte[4096];
						int bytesRead = -1;

						while ((bytesRead = fileInputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, bytesRead);
						}
						LOGGER.log(Level.INFO, "Transferencia completada {0}", fileDownload.getName());
					}

				} else {
					// i. Si no existe el archivo responder con un 
					PrintWriter out = response.getWriter();
					response.setStatus(404);
					 // escribe tu código aquí
					out.println(String.format("El archivo solicitado no existe: %s", fileName));
				}

			} catch (Exception ex) {
				PrintWriter out = response.getWriter();
				out.println(String.format("Ocurrio un error: %s", ex));
				LOGGER.log(Level.SEVERE, "Prolemas durante la descarga de archivo. Error: {0}",
						new Object[] { ex.getMessage() });
			}
		} else {
			// b. Responder con un error 400 si el archivo no existe
			 // escribe tu código aquí
			response.setStatus(404);
		}
	}

}
