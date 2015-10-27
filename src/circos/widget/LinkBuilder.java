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
 
 package circos.widget;

/**
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class LinkBuilder {
    private Long sourceStart;
    private Long sourceEnd;
    private Long sinkStart;
    private Long sinkEnd;
    private Integer sourceChr;
    private Integer sinkChr;
    private Double strength;

    public LinkBuilder() {
    }

    public LinkBuilder setSourceStart(long sourceStart) {
        this.sourceStart = sourceStart;
        return this;
    }

    public LinkBuilder setSourceEnd(long sourceEnd) {
        this.sourceEnd = sourceEnd;
        return this;
    }

    public LinkBuilder setSinkStart(long sinkStart) {
        this.sinkStart = sinkStart;
        return this;
    }

    public LinkBuilder setSinkEnd(long sinkEnd) {
        this.sinkEnd = sinkEnd;
        return this;
    }

    public LinkBuilder setSourceArc(int sourceChr) {
        this.sourceChr = sourceChr;
        return this;
    }

    public LinkBuilder setSinkArc(int sinkChr) {
        this.sinkChr = sinkChr;
        return this;
    }

    public LinkBuilder setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    public Link createLink() {
        if (sourceEnd==null) sourceEnd = sourceStart;
        if (sinkEnd==null) sinkEnd = sinkStart;
        if (strength == null) strength = -1d;
        return new Link(sourceStart, sourceEnd, sinkStart, sinkEnd, sourceChr, sinkChr, strength);
    }

}
