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

package main.java.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatTest {
    @Test
    public void CatTest() {
        Cat test = new Cat();
        assertNotNull(test);
    }
}
