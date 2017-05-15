package iea.jade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class jml {
    /** A simple JML to JSON converter
     * @author Jade of Iearthia
     * @version 0.0.1
    */
    public static void main(String[] args) {
        File in = new File(args[0]);
        File out = new File(args[1]);
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
        String outJML = "";
        Iterator<String> itr = json.keys();
        while (itr.hasNext()) {
            String next = itr.next();
            outJML += String.format(".%s(%s)%n", next, json.get(next));
        }
        System.out.print(outJML);
    }
    
}
