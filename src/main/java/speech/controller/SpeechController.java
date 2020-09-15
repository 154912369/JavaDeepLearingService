package speech.controller;

import com.google.gson.Gson;
import org.kaldi.KaldiRecognizer;
import org.kaldi.Model;
import org.kaldi.SpkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.Addressing;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller(value = "/")
public class SpeechController {
    @Autowired
    KaldiRecognizer rec;
    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/example")
    public String example(){
        return "example";
    }

    @RequestMapping(value = "/postdata")
    @ResponseBody
    public String postdata( MultipartFile filedata) throws IOException {
        InputStream ais = filedata.getInputStream();
        byte[] b = new byte[4096];
        int nbytes;
        while ((nbytes = ais.read(b)) >= 0) {
            if (rec.AcceptWaveform(b)) {
                System.out.println(rec.Result());
            } else {
                System.out.println(rec.PartialResult());
            }
            System.out.println(nbytes);
        }
        String text = rec.FinalResult();
        Gson gson = new Gson();
        Map tx =gson.fromJson(text, Map.class);
        String result= (String) tx.get("text");
        System.out.println(text);
        System.out.println(result);
        return result;
    }

}
