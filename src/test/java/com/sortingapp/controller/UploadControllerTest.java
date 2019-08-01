package com.sortingapp.controller;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import org.springframework.mock.web.MockMultipartFile;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UploadControllerTest {

@Test
    public void setUp(){

    File fr=new File("src/test/resources/file.txt");


    try {

        RedirectAttributes flashAttributes;
        flashAttributes= Mockito.mock(RedirectAttributes.class);

        FileInputStream input = new FileInputStream(fr);
        MultipartFile multipartFile = new MockMultipartFile("file",
                fr.getName(), "text/plain", IOUtils.toByteArray(input));

        UploadController uploadController = new UploadController();
        uploadController.singleFileUpload(multipartFile,flashAttributes);

        Assert.assertEquals("file.txt", multipartFile.getOriginalFilename());

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}



