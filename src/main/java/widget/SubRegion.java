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

import javafx.scene.paint.Color;

/**
 * Used to model complex arcs. A subregion is a portion of a specified arc and describes some properties of this portion.
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class SubRegion {

	/**
	 * unique id for this sub-region object. Not self assigned since read from main.circos configuration files.
	 */
	private String id;
	/**
	 * Label to be displayed on the ideogram.
	 * From the original documentation: "If you are working with multiple species, I suggest prefixing the chromosome number with a species identifier (e.g., hs = Homo sapiens, mm = Mus musculus, etc). Even when working with only one species, prefixing the chromosome with a species code is highly recommended"
	 */
	private String label;
	private long start;
	private long end;
	/**
	 * this color is combined with parent arc one.
	 */
	private javafx.scene.paint.Color color;
	/**
	 * String qualifying the subregion, e.g Band, Gene. 
         * Want not to be an enum to leave some degree of freedom.
	 */
	private String subregionType;

	public String getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public long getStart() {
		return this.start;
	}

	public long getEnd() {
		return this.end;
	}

	public javafx.scene.paint.Color getColor() {
		return this.color;
	}

	public String getSubregionType() {
		return this.subregionType;
	}

    public SubRegion(String id, String label, long start, long end, Color color, String subregionType) {
        this.id = id;
        this.label = label;
        this.start = start;
        this.end = end;
        this.color = color;
        this.subregionType = subregionType;
    }
        
        

}