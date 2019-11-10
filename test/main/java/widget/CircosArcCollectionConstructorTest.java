/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.widget;

import main.java.BaseGuiTester;
import main.java.models.MouseMM9;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author pavanpa
 */
public class CircosArcCollectionConstructorTest extends BaseGuiTester {

    @Override
    public void configureCircos(JFXPanel jfxPanel) throws RuntimeException {
        ArcCollection mouseGenome = new MouseMM9();
        Circos widget = new Circos(mouseGenome, new ArcEventHandler(), new LinkEventHandler());
                
        widget.setStrokeWidth(0.01);
        widget.initialize();
        loadMM9links(widget);
        
        jfxPanel.setScene(new Scene(widget));
        startIncorporatedAnimation(widget);
    }

    @Test
    public void test() {
        try {
            CircosArcCollectionConstructorTest me = new CircosArcCollectionConstructorTest();
            me.initSwing();
            TimeUnit.SECONDS.sleep(80);
            assertTrue(true);
        } catch (Exception e) {
            fail();
            //throw e;
        }
    }
}
