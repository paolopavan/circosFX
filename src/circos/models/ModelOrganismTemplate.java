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
 
 package circos.models;

import circos.io.CircosColorConfLoader;
import circos.io.CircosKaryotypeConfLoader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import circos.widget.ArcCollection;
import circos.widget.SubRegion;

/**
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public abstract class ModelOrganismTemplate implements ArcCollection {
    static final Log LOG = LogFactory.getLog(ModelOrganismTemplate.class);
    final String colorsResource = "/circos/resources/colors.ucsc.conf";
    /**
     * ArcNames
     */
    private String[] chromosomeNames;
    /**
     * Arc lengths
     */
    private long[] chromosomeLengths;
    private List<List<SubRegion>> cytogeneticBands;
    private String[] chromosomeColorNames;
    
    private HashMap<String,Color> colorDatabase;

    public ModelOrganismTemplate(String karyotypeResource) {
        InputStream karyotypeStream = getClass()
                .getResourceAsStream(karyotypeResource);
        
        InputStream colorResourceStream = getClass()
                .getResourceAsStream(colorsResource);
        
        String debugDetailString = "color resource path "+colorsResource;
        if (colorResourceStream==null) debugDetailString += " and stream is null";
        
        try {
            colorDatabase = new CircosColorConfLoader(colorResourceStream).getColorDatabase();
            
            CircosKaryotypeConfLoader loader = new CircosKaryotypeConfLoader(karyotypeStream, colorDatabase);
            chromosomeNames = loader.getChromosomeNames();
            chromosomeLengths = loader.getChromosomeLengths();
            chromosomeColorNames = loader.getChromosomeColorNames();
            cytogeneticBands = loader.getCytogeneticBands();
            
        } catch (Exception e){
            throw new IllegalStateException("Resource files corrupted, this should actually never happen "
                    + "with prepared configurations. I cannot continue.\nReason:"+ debugDetailString +
                    e.getMessage());
        }
    }

    @Override
    public long[] getArcLengths() {
        return chromosomeLengths;
    }

    @Override
    public String[] getArcNames() {
        return chromosomeNames;
    }

    @Override
    public List<SubRegion> getSubregions(int arc) {
        return cytogeneticBands.get(arc);
    }
    
    @Override
    public Color[] getArcColors(){
        Color [] returnArray = new Color[chromosomeColorNames.length];
        for (int i =0;i < returnArray.length; i++) 
            returnArray[i] = colorDatabase.get(chromosomeColorNames[i]);
        
        return returnArray;
    }
}
