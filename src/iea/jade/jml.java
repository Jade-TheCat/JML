package iea.jade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/** A simple JSON to JML and back converter
 * @author Jade of Iearthia
 * @version 0.0.1
 */

public class jml {
    /** 
     * @param args The arguments for the program, first is the input, second is output.
     * @throws IOException
    */
    public static void main(String[] args) throws IOException {
        File in = new File(args[0]);
        File out = new File(args[1]);
        FileWriter writer = new FileWriter(out, false);
        if (in.getName().endsWith(".json")) {
            String jsonIn = "";
            try(BufferedReader br = new BufferedReader(new FileReader(in))) {
                for(String line; (line = br.readLine()) != null; ) {
                    jsonIn += line;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(jml.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(jml.class.getName()).log(Level.SEVERE, null, ex);
            }
            JSONObject json = new JSONObject(jsonIn);
        
            String fileName = in.getName();
            int pos = fileName.lastIndexOf(".");
            if (pos > 0) {
                fileName = fileName.substring(0, pos);
            }
        
            String head = String.format("OBJ:%s(%n", fileName);
            String outJML = (parseObject(head, json) + ")").replace(".", "\t.").replace(");", "\t)");
            System.out.print(outJML);
            writer.write(outJML);
            writer.close();
        } else if (in.getName().endsWith(".jml")) {
            
        }
        
    }
    private static String parseObject(JSONObject json) {
        String outJML = "";
        Iterator<String> itr = json.keys();
        while (itr.hasNext()) {
            String next = itr.next();
            if(!(json.get(next) instanceof JSONArray || json.get(next).toString().startsWith("{"))) {
                outJML += String.format(".%s(%s)%n", next, json.get(next));
            } else if (json.get(next) instanceof JSONArray) {
                outJML += String.format(".%s[%s]%n", next, json.get(next).toString().replaceAll("[\\[\\]\\\"]", "").replace(",","; "));
            } else if (json.get(next).toString().startsWith("{")) {
                outJML += String.format(".OBJ:%s(%n%s);%n", next, parseObject(new JSONObject(json.get(next).toString())).replace(".", "\t."));
            }
        }
        return outJML;
    }
    private static String parseObject(String header, JSONObject json) {
        String outJML = header + "\n";
        Iterator<String> itr = json.keys();
        while (itr.hasNext()) {
            String next = itr.next();
            if(!(json.get(next) instanceof JSONArray || json.get(next).toString().startsWith("{"))) {
                outJML += String.format(".%s(%s)%n", next, json.get(next));
            } else if (json.get(next) instanceof JSONArray) {
                outJML += String.format(".%s[%s]%n", next, json.get(next).toString().replaceAll("[\\[\\]\\\"]", "").replace(",","; "));
            } else if (json.get(next).toString().startsWith("{")) {
                outJML += String.format(".OBJ:%s(%n%s);%n", next, parseObject(new JSONObject(json.get(next).toString())).replace(".", "\t."));
            }
        }
        return outJML;
    }
    
}
