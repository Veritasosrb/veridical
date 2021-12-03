package com.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileUpload
 */

@MultipartConfig
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	FileUpload()
	{
		
	}
	protected void  processRequest(HttpServletRequest request,
	        HttpServletResponse response)
	                throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8");
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletFileUpload sf=new ServletFileUpload(new DiskFileItemFactory());
		try {
			List<FileItem> multifiles=sf.parseRequest(request);
			for(FileItem item:multifiles)
			{
				item.write(new File("C:\\Users\\aditi.inamdar\\eclipse-workspace\\nlp\\input"+item.getName()));
			}
			System.out.println("File Uploaded");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
