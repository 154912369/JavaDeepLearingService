package age.controller;

import age.server.AgeServer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class AgeController {
    @Autowired
    AgeServer ageServer;



    @RequestMapping("/age/image")

    public void AgeImage(MultipartFile filedata, HttpServletResponse response) throws IOException {
        Mat mat=ageServer.processPicture(filedata);
        ageServer.writeJpg(response.getOutputStream(),mat);
    }
    @GetMapping("/age")
    public String age(){
        return "age";
    }
}
