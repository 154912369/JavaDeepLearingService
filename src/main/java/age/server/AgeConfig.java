package age.server;

import org.kaldi.KaldiRecognizer;
import org.kaldi.Model;
import org.kaldi.SpkModel;
import org.opencv.core.Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AgeConfig {
    @Bean
    AgeServer getAgeServer(){

        return new AgeServer();
    }
}
