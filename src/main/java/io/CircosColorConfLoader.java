/*
 *                    CircosFX development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU General Public Licence v2.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright for this code is held jointly by Paolo Pavan and 
 * individual authors that may want to contribute. These should 
 * be listed in @author doc comments.
 *
 */
 
 package main.java.io;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.paint.Color;

/**
 * Loads main.circos color code configuration file
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class CircosColorConfLoader {
    HashMap<String,Color> colors = new HashMap();
    
    
    public CircosColorConfLoader(InputStream... inputStreams) throws ParserException {
        for (InputStream is: inputStreams) {
            Scanner scanner = new Scanner(is);

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                if (line.startsWith("#")) continue;
                if (line.isEmpty()) continue;
                // char ( is found in color lists that are still not supported
                if (line.contains("(")) continue;
                // information on hierarchical inclusion of color definition is built in
                if (line.startsWith("<<include")) continue;
                
                // normalize color ID, it seems they are case insensitive
                line = line.toLowerCase();
                line = line.replaceAll("\\s", "");
                String[] fields = line.split("=");

                // check if field[1] is a synonym
                if (colors.containsKey(fields[1])) {
                    colors.put(fields[0], colors.get(fields[1]));
                    continue;
                }

                String[] rgb = fields[1].split(",");
                if (rgb.length > 3) 
                    throw new ParserException(
                            "Malformed color code. RGB code with more than 3 numbers met in:\n"+line);

                try {
                    colors.put(fields[0], Color.rgb(
                            new Integer(rgb[0]), 
                            new Integer(rgb[1]), 
                            new Integer(rgb[2])
                    ));
                } catch (NumberFormatException e) {
                    throw new ParserException(
                            "Invalid RGB color specification or invalid alias met in:\n"+line
                    );
                }
            }
        }
        // Add a n/a color, basically for DM6 genome bands which requires it
        colors.put("n/a", colors.get("dgrey"));
    }
    
    public HashMap<String,Color> getColorDatabase(){
        return colors;
    }
}