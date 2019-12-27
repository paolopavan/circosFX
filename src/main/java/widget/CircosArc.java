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
 
 package main.java.widget;
import javafx.scene.shape.Arc;

/**
 * Class extending Arc became also a data structure that allows to export 
 * some useful properties directly to listeners
 * 
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class CircosArc extends Arc {
    String represent;
    
    public void setRepresentation(String s) {
        this.represent = s;
    }
    public String getRepresentation() {
        return represent;
    }
    @Override
    public String toString(){
        return represent;
    }
}
