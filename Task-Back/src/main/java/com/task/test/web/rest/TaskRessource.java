package com.task.test.web.rest;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletContext;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.test.service.TaskService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class TaskRessource {
	@Autowired
	TaskService taskService;
	
	@Autowired
	ServletContext context;
	
	@PostMapping("/attachImageToPDF/{x}/{y}")
	public ResponseEntity<String> attachImageToPDF( @PathVariable("x") int x, @PathVariable("y") int y, @RequestParam("img") MultipartFile img) throws IOException {
	
		
		try {
			
			//Create pdfFolder and uploads folders
			//this.taskService.init();
			
			//upload image to uploads folder
			this.taskService.save(img);
			
			//create empty pdf in pdfFolder
			String pdfPath = this.taskService.createEmptyPDF();
			
			//attach image to the pdf 
			this.taskService.attachImage(x,y,img,pdfPath);
			
			return ResponseEntity.ok().body("Image attached with success !");
		
		}catch(Exception e) {
			return ResponseEntity.badRequest().body("Could not attach image !");
		
			
			
		}
	
	 
		
	}
	
	

}
