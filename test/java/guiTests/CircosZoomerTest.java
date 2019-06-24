/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTests;

import main.java.gui.Trials;
import main.java.widget.Circos;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;


/**
 *
 * @author pavanpa
 */
public class CircosZoomerTest extends BaseGuiTester {

    @Override
    void configureCircos(JFXPanel jfxPanel) {
        Circos widget = new Circos(new long[]{34,56,90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
        widget.setStrokeWidth(1);
        widget.initialize();
        loadLinks(widget);
        widget.doFancyStuffs();
        
        Trials gui = new Trials(widget);
        jfxPanel.setScene(new Scene(gui));
    }
    
    public static void main(String[] args){
        BaseGuiTester me = new CircosZoomerTest();
        me.initSwing();
    }
    
}
