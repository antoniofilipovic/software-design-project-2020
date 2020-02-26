package hr.fer.opp.webmeisters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ShowPictureController {

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/showPicture/{id}", method = RequestMethod.GET)
    public void showPicture(@PathVariable String id, Model model, OutputStream out) throws IOException {

        BufferedImage slika;
        Path path = Paths.get(servletContext.getRealPath(servletContext.getContextPath()),"/WEB-INF/pictures/"+id);
        System.out.println("path SLIKE: " + path);

        if(Files.exists(path)){
            slika = ImageIO.read(path.toFile());
            ImageIO.write(slika, "png", out);
            out.close();
        }
    }

    @RequestMapping(value = "/showVideo/{id}", method = RequestMethod.GET)
    public void showVideo(@PathVariable String id, OutputStream out, HttpServletResponse response) throws IOException {
    	
		Path filepath = Paths.get(servletContext.getRealPath(servletContext.getContextPath()),"/resources/static/videos/", id);

        System.out.println("path VIDEO: " + filepath);
    
      

        InputStream input = servletContext.getResourceAsStream(id);
        if(input==null) {
        	System.out.println("NUL SAM JA");
        	System.out.println(filepath.toString());
        }

        //response.setContentType("video/quicktime"); //Use this for VLC player

        response.setContentType("video/mp4");



//        response.setHeader("Content-Disposition", "inline; filename=\""
//
//            + fileName + "\"");

        OutputStream output = response.getOutputStream();



        byte[] buffer = new byte[2096];

        int read = 0;

        while ((read = input.read(buffer)) != -1) {

          output.write(buffer, 0, read);

        }

        input.close();

        output.flush();

        output.close();

    }

    /*
    @RequestMapping(value = "/showVideo/{id}", method = RequestMethod.GET)
    public void showVideo(@PathVariable String id, Model model, OutputStream out) throws IOException {
        Path path = Paths.get(servletContext.getRealPath("/WEB-INF/videos/" + id));
        System.out.println("path VIDEO: " + path);

        if(Files.exists(path)){
            System.out.println("0");
            File vid = new File(path.toString());
            InputStream is = new FileInputStream(vid);
            System.out.println("1");
            long len = vid.length();
            byte[] bytes = new byte[(int) len];
            int offset = 0;
            int numRead = 0;
            System.out.println("2");
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            System.out.println("3");
            try {
                out.write(bytes);
            } catch(IOException ex){
                ex.printStackTrace();
            }

            System.out.println("4");
            out.close();
            System.out.println("5");

        }
    }

     */
}
