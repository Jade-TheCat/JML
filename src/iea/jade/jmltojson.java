package iea.jade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;


public class jmltojson {
    static JSONObject parseJML(String jml) {
        JSONObject out = new JSONObject();
        try (BufferedReader br = new BufferedReader(new StringReader(jml))) {
            for (String line; (line = br.readLine()) != null; ) {
                if(line.trim().startsWith(".")) {
                    if(line.contains("OBJ:")) {
                        out.put(line.replace(".", "").replace("OBJ:", "").replace("(", ""), parseJML(line.replace(".", "")));
                    } else if (line.trim().equals(")")) {
                        
                    } else if (line.trim().contains("[")) {
                        JSONArray a = new JSONArray();
                        String array = line.trim().replace(".", "");
                        array = array.substring(array.indexOf("["), array.indexOf("]")).replace("; ", "\n");
                        try (BufferedReader br2 = new BufferedReader(new StringReader(array))) {
                            for (String line2; (line2 = br.readLine()) != null; ) {
                                a.put(line2);
                            }
                        }
                        out.put(line.trim().substring(1, line.indexOf("[") - 1), a);
                    } 
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(jmltojson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return
    }
}
