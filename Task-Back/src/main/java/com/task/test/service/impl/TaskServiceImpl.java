package com.task.test.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.task.test.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	  private final Path rootUploads = Paths.get("uploads");
	  private final Path rootPdfFolder = Paths.get("pdfFolder");
	  
	  @Override
	  public void init() {
	    try {
	    		Files.createDirectory(rootUploads);
	    		Files.createDirectory(rootPdfFolder);
	    		
	    } catch (IOException e) {
	      throw new RuntimeException("Could not initialize folders ! Error: " + e.getMessage());
	    }
	  }
	  
	  @Override
	  public void save(MultipartFile img) {
	    try {
	    		Path resourceDirectory = Paths.get("uploads");
	    		String imgPath = resourceDirectory.toFile().getAbsolutePath()+"/"+img.getOriginalFilename();
	    		Path path = Paths.get(imgPath);
	    		if(Files.exists(path)) {
	    			//delete image
	    			Files.delete(path);
	    		}
	    		//upload image
	    		Files.copy(img.getInputStream(), this.rootUploads.resolve(img.getOriginalFilename()));
	    		
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the image ! Error: " + e.getMessage());
	    }
	  }
	  
	@Override
	public String createEmptyPDF() throws IOException {
		try {
				PDDocument pdfdoc= new PDDocument();  
				pdfdoc.addPage(new PDPage());  

		
				Path resourceDirectory = Paths.get("pdfFolder");
				String pdfPath = resourceDirectory.toFile().getAbsolutePath()+"/Sample.pdf";
	
				pdfdoc.save(pdfPath);
		
				//prints the message if the PDF is created successfully   
				System.out.println("PDF created");  
				//closes the document  
				pdfdoc.close();  
				return pdfPath;
				
		  } catch (IOException e) {
		      throw new RuntimeException("Could not create empty PDF ! Error: " + e.getMessage());
		    }
		
	}
	
	@Override
	public void attachImage(int x, int y, MultipartFile img , String pdfPath) throws IOException {
		  try {
			  
			  	//Loading an existing document
			  	File file = new File(pdfPath);
			  	PDDocument doc = Loader.loadPDF(file);
	        
			  	//Retrieving the page
			  	PDPage page = doc.getPage(0);
	      
			  	Path resourceDirectory = Paths.get("uploads");
			  	String imgPath = resourceDirectory.toFile().getAbsolutePath()+"/"+img.getOriginalFilename();
	       
			  	//Creating PDImageXObject object
			  	PDImageXObject pdImage = PDImageXObject.createFromFile(imgPath,doc);
	       
			  	//creating the PDPageContentStream object
			  	PDPageContentStream contents = new PDPageContentStream(doc, page);

			  	//Drawing the image in the PDF document
			  	contents.drawImage(pdImage, x, y, 100, 100 );

			  	System.out.println("Image inserted");
	      
			  	//Closing the PDPageContentStream object
			  	contents.close();		
			
			  	//Saving the document
			  	doc.save(pdfPath);
	            
			  	//Closing the document
			  	doc.close();
	      
		  } catch (IOException e) {
		      throw new RuntimeException("Could not attach image ! Error: " + e.getMessage());
		    }
		
	}

}
