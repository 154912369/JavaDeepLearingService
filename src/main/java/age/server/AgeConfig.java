package age.server;

import org.kaldi.KaldiRecognizer;
import org.kaldi.Model;
import org.kaldi.SpkModel;
import org.opencv.AgeGenderCheck;
import org.opencv.core.Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AgeConfig {
    @Bean
    AgeServer getAgeServer(){
        AgeGenderCheck.NetInit("age_net.caffemodel","age_deploy.prototxt", "gender_net.caffemodel","gender_deploy.prototxt","opencv_face_detector_uint8.pb","opencv_face_detector.pbtxt");
        return new AgeServer();
    }
}
