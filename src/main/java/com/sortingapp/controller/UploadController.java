package com.sortingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    File directory = new File("./");

    private String UPLOADED_FOLDER = directory.getAbsolutePath();

    @GetMapping("/")
    public String index() {

        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());

        try {

            byte[] bytes = file.getBytes();
            String s = new String(bytes);
            String[] strArray = s.split(" ");
            int[] intArray = new int[strArray.length];
            for(int i = 0; i < strArray.length; i++) {
                intArray[i] = Integer.parseInt(strArray[i]);
            }
            Arrays.sort(intArray);
            String res = Arrays.toString(intArray).replace("," ,"")
                    .replace("[" ,"").replace("]","");

            byte[] byteString = res.getBytes();

            Files.write(path, byteString);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded");
            } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> walk = Files.walk(Paths.get(directory.getAbsolutePath()))) {


            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

            redirectAttributes.addFlashAttribute("results", result);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}