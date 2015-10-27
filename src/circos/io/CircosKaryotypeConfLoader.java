/*
 *                    CircosFX development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU General Public Licence v3.  This should
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
 
 package circos.io;

import circos.widget.SubRegion;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.scene.paint.Color;

/**
 * Class used to parse karyotype.txt files, usually in data/karyotype (e.g. data/karyotype/karyotype.mouse.mm9.txt).
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class CircosKaryotypeConfLoader {
    /**
     * ArcNames
     */
    private List<String> chromosomeNames = new ArrayList();
    /**
     * Arc lengths
     */
    private List<Long> chromosomeLengths = new ArrayList();
    private LinkedHashMap<String, ArrayList<SubRegion>> cytogeneticBands = 
            new LinkedHashMap<String, ArrayList<SubRegion>>();
    private List<List<SubRegion>> returningCytogeneticBands;
    private List<String> chromosomeColorNames = new ArrayList();
    private final String fieldSeparator = " ";

    /**
     * 
     * @param is InputStream to the configuration resource file
     */
    public CircosKaryotypeConfLoader(java.io.InputStream is, HashMap<String,Color> colors) throws ParseException{
        Scanner scanner = new Scanner(is);    

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.startsWith("#")) continue;
            if (line.isEmpty()) continue;

            String[] fields = line.split(fieldSeparator);
            if (fields.length > 7) 
                throw new ParseException(
                        "Malformed Karyotype specification. Line with more than 7 fields met in:\n"+line,
                        nthOccurrence(line, fieldSeparator, 7)
                );
            
            // normalize color names, it seems they are case insensitive
            Color parsedColor = colors.get(fields[6].toLowerCase());
            if (parsedColor == null) throw new ParseException(
                    "Undefined color in:\n"+line,
                    nthOccurrence(line, fieldSeparator, 6)
                );

            try {
                if (fields[0].equals("chr")){
                    chromosomeNames.add(fields[2]);
                    chromosomeLengths.add(new Long(fields[5]));
                    chromosomeColorNames.add(fields[6]);
                    cytogeneticBands.put(fields[2], new ArrayList());
                }

                if (fields[0].equals("band")){
                    SubRegion r = new SubRegion(
                            fields[2],
                            fields[3],
                            new Long(fields[4]),
                            new Long(fields[5]),
                            parsedColor,
                            fields[0]
                    );
                    cytogeneticBands.get(fields[1]).add(r);
                }
            } catch (NumberFormatException e) {
                throw new ParseException(
                        "Invalid chromosome coordinates (fields 5 or 6) specified in:\n"+line,
                        nthOccurrence(line, fieldSeparator, 6)
                );
            }
        }
        // translate cytogenetics band data structure in one compatible with others
        returningCytogeneticBands = new ArrayList();
        for (Map.Entry<String, ArrayList<SubRegion>> entry : cytogeneticBands.entrySet()){
            returningCytogeneticBands.add(entry.getValue());
        }
    }
    
    private static int nthOccurrence(String str, String c, int n) {
        int pos = str.indexOf(c, 0);
        while (n-- > 0 && pos != -1)
            pos = str.indexOf(c, pos+1);
        return pos;
    }

    public String[] getChromosomeNames() {
        return chromosomeNames.toArray(new String[chromosomeNames.size()]);
    }

    public long[] getChromosomeLengths() {
        long[] primitives = new long[chromosomeLengths.size()];
        // non null guaranteed during parsing by Long() constructor
        for (int i = 0; i < chromosomeLengths.size(); i++)
            primitives[i] = chromosomeLengths.get(i);

        return primitives;
    }

    public List<List<SubRegion>> getCytogeneticBands() {
        return returningCytogeneticBands;
    }

    public String[] getChromosomeColorNames() {
        return chromosomeColorNames.toArray(new String[chromosomeColorNames.size()]);
    }
    
    
}