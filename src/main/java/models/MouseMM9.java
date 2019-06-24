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
 
 package main.java.models;

/**
 * ArcCollection representing mouse mm9 karyotype.
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class MouseMM9 extends ModelOrganismTemplate {
    public MouseMM9() {
        super("/main/resources/karyotype.mouse.mm9.txt");
    }
}