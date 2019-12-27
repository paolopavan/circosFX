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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import main.java.templates.ArcCollection;
import main.java.widget.LinkBuilder;

/**
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class CircosLinksAndBundlesLoader {
    private final String fieldSeparator = "\\s+";
    List links = new ArrayList();
    
    public CircosLinksAndBundlesLoader(java.io.InputStream is, ArcCollection arcCollection, boolean collapse) throws ParseException{
        this(is, arcCollection.getArcNames(), collapse);
    }
    
    public CircosLinksAndBundlesLoader(java.io.InputStream is, String[] arcNames, boolean collapse) throws ParseException{
        Map<String,Integer>namesMapping = new HashMap(arcNames.length);
        for (int i = 0; i < arcNames.length; i++) namesMapping.put(arcNames[i], i);
        
        Scanner scanner = new Scanner(is); 
        
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.startsWith("#")) continue;
            if (line.isEmpty()) continue;

            String[] fields = line.split(fieldSeparator);
            if (fields.length > 7) 
                throw new ParseException(
                        "Malformed links file: more than 7 fields met in:\n"+line,
                        nthOccurrence(line, fieldSeparator, 7)
                );

            if (fields[0].equals("mmM") || fields[3].equals("mmM")) continue;


            LinkBuilder lb = new LinkBuilder();

            try {
                lb.setSourceArc(namesMapping.get(fields[0]));
                lb.setSinkArc(namesMapping.get(fields[3]));
            } catch (NullPointerException e){
                throw new ParseException(
                        "Invalid chromosome name (fields 1 or 4) specified in:\n"+line,
                        nthOccurrence(line, fieldSeparator, 1));
            }

            try {
                lb.setSourceStart(new Long(fields[1]));
                lb.setSourceEnd(new Long(fields[2]));

                lb.setSinkStart(new Long(fields[4]));
                lb.setSinkEnd(new Long(fields[5]));
            } catch (NumberFormatException e) {
                throw new ParseException(
                        "Invalid chromosome coordinates (fields 2-3 or 4-5) specified in:\n"+line,
                        nthOccurrence(line, fieldSeparator, 2)
                );
            } 

            // field[6] is found in bundles and appears similar to:
            // nlinks=6,bsize1=320001,bsize2=800003,bidentity1=1.000000,bidentity2=0.714288,depth1=418,depth2=718,
            // I should import links strength from here (maybe from nlinks?)

            links.add(lb.createLink());
        }
    }

    public List getLinks() {
        //http://www.leveluplunch.com/java/examples/how-to-build-java-util-stream/
        //http://stackoverflow.com/questions/21956515/how-to-create-an-infinite-streame-out-of-an-iteratore
        return links;
    }
    
    
    private static int nthOccurrence(String str, String c, int n) {
        int pos = str.indexOf(c, 0);
        while (n-- > 0 && pos != -1)
            pos = str.indexOf(c, pos+1);
        return pos;
    }
}
