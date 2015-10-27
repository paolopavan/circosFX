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
 
 package circos.widget.eventHandlers;


import javafx.event.Event;
import javafx.event.EventHandler;
import circos.widget.CircosLink;

/**
 * This class must be extended in your application logic to handle link click events.
 * Override handle() method.
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class LinkEventHandler implements EventHandler {

    @Override
    public void handle(Event t) {
        CircosLink source = (CircosLink) t.getSource();
        System.out.println(source.toString());
    }
    
}