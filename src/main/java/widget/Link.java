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
 
 package main.java.widget;

/**
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class Link {
    private final long sourceStart, sourceEnd;
    private final long sinkStart, sinkEnd;
    private final int sourceArc, sinkArc;
    /**
     * maintain a relation strength/intensity value expressed in 
     * original units of measurements. Data will be normalized at draw time
     * WARNING Zero-based nomenclature
     */
    private final double strength;

    public Link(long sourceStart, long sourceEnd, long sinkStart, long sinkEnd, int sourceArc, int sinkArc, double strength) {
        this.sourceStart = sourceStart;
        this.sourceEnd = sourceEnd;
        this.sinkStart = sinkStart;
        this.sinkEnd = sinkEnd;
        this.sourceArc = sourceArc;
        this.sinkArc = sinkArc;
        this.strength = strength;
    }
    
    public boolean isAntisense(){
        return (sourceEnd - sourceStart) * (sinkEnd - sinkStart) < 0;
    }

    public long getSourceStart() {
        return sourceStart;
    }

    public long getSourceEnd() {
        return sourceEnd;
    }

    public long getSinkStart() {
        return sinkStart;
    }

    public long getSinkEnd() {
        return sinkEnd;
    }

    public int getSourceArc() {
        return sourceArc;
    }

    public int getSinkArc() {
        return sinkArc;
    }

    public double getStrength() {
        return strength;
    }
    @Override
    public String toString(){
        String desc =  "Link from "+ getSourceArc()+
                ":"+getSourceStart()+"-"+getSourceEnd()+
                " to "+ getSinkArc()+
                ":"+getSinkStart()+"-"+getSinkEnd();

        if (isAntisense()) desc += " (antisense)";

        return desc;
    }
    
}
