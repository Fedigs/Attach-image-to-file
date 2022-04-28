package com.task.test.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface TaskService {
	  public void init();
	  public String createEmptyPDF() throws IOException;
	  public void attachImage(int x, int y, MultipartFile img, String pdfPath) throws IOException;
	  public void save(MultipartFile file);


}
