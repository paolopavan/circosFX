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
 
 package main.java.templates;

import java.util.List;
import javafx.scene.paint.Color;
import main.java.widget.SubRegion;

/**
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public interface ArcCollection {

	/**
	 * Return a collection of arc lengths, compulsory to initialize Circos in its constructor
	 */
	long[] getArcLengths();

	/**
	 * Return a collection of arc names, to be loaded by Circos' constructor
	 */
	String[] getArcNames();

	/**
	 * Returns a collection of start coordinates relative to subregions in the specified arc
	 * @param arc Specify the index of the arc of the collection to get back
	 */
	List<SubRegion> getSubregions(int arc);
        
	/**
	 *
	 * @return an array of color objects to be used to paint arcs
	 */
	Color[] getArcColors();

}