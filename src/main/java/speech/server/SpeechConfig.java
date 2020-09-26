package speech.server;

import org.kaldi.KaldiRecognizer;
import org.kaldi.Model;
import org.kaldi.SpkModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeechConfig {
    @Bean
    KaldiRecognizer getspeechServer(){
        Model model = new Model("chinese/model");
        SpkModel spkModel = new SpkModel("model-spk");
        KaldiRecognizer rec = new KaldiRecognizer(model, spkModel,  16000f);
        return rec;
    }
}
