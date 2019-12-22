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

import javafx.scene.paint.Color;
import main.java.widget.SubRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility class used to combine in on collection several others
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class ComparedArcCollection implements ArcCollection {
	ArcCollection[] models;
	int arcsNumber;
	/**
	 * 
	 * @param collections
	 */
	public ComparedArcCollection(ArcCollection... collections) {
		this.models = collections;
		for (ArcCollection m: models) arcsNumber += m.getArcNames().length;
	}

	@Override
	public long[] getArcLengths() {
		long[] r = new long[arcsNumber];
		int destinationPosition = 0;
		for (ArcCollection m: models) {
			System.arraycopy(m.getArcLengths(), 0, r, destinationPosition, m.getArcLengths().length);
			destinationPosition += m.getArcLengths().length;
		}
		return r;
	}

	@Override
	public String[] getArcNames() {
		String[] r = new String[arcsNumber];
		int destinationPosition = 0;
		for (ArcCollection m: models) {
			System.arraycopy(m.getArcNames(), 0, r, destinationPosition, m.getArcLengths().length);
			destinationPosition += m.getArcLengths().length;
		}
		return r;
	}

	@Override
	public List<SubRegion> getSubregions(int arc) {
		// search for destination model
		int modelNumber = 0;
		int arcSum = models[0].getArcLengths().length;
		int pad = 0;
		while (arc >= arcSum) {
			modelNumber++;
			pad = arcSum;
			arcSum += models[modelNumber].getArcLengths().length;
		}

		return models[modelNumber].getSubregions(arc - pad);
	}

	@Override
	public Color[] getArcColors() {
		Color[] r = new Color[arcsNumber];
		int destinationPosition = 0;
		for (ArcCollection m: models) {
			System.arraycopy(m.getArcColors(), 0, r, destinationPosition, m.getArcLengths().length);
			destinationPosition += m.getArcLengths().length;
		}
		return r;
	}
}