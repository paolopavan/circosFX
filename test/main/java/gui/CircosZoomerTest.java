/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.gui;

import main.java.BaseGuiTester;
import main.java.widget.Circos;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author pavanpa
 */
public class CircosZoomerTest extends BaseGuiTester {

    @Override
    public Circos configureCircos(JFXPanel jfxPanel) {
        Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
        widget.setStrokeWidth(1);
        widget.initialize();
        loadLinks(widget);
        widget.doFancyStuffs();
        
        Trials gui = new Trials(widget);
        jfxPanel.setScene(new Scene(gui));

        return widget;
    }

    @Test
    public void test() throws Exception {
        CircosZoomerTest me = new CircosZoomerTest();
        me.initSwing();
        //TimeUnit.SECONDS.sleep(10);
        assertTrue(true);
    }
    
}
