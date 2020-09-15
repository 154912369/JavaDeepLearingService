import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import net.sf.json.JSONArray;

import java.io.*;
import java.util.Map;

public class test {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/renweijie/code/java/speech/data/tmp.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String result="";
        String br;
        while((br = reader.readLine()) != null) {
            result+=br;
        }
        Gson gson = new Gson();
        Map b =gson.fromJson(result, Map.class);
        System.out.println(b.get("text"));
        int a=result.indexOf("text");
        System.out.println(result.substring(a+8,result.length()-2));


    }
}
