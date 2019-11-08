/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.widget;

import main.java.BaseGuiTester;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;


/**
 *
 * @author pavanpa
 */
public class CircosBasicConstructorTest extends BaseGuiTester {

    @Override
    public void configureCircos(JFXPanel jfxPanel) {
        Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
        widget.setStrokeWidth(1);
        widget.setTitle("This is a test");
        widget.initialize();
        loadLinks(widget);
        
        jfxPanel.setScene(new Scene(widget));
        startIncorporatedAnimation(widget);
    }

    @Test
    public void test() throws Exception {
        BaseGuiTester me = new CircosBasicConstructorTest();
        me.initSwing();
    }
    
}