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


/**
 *
 * @author pavanpa
 */
public class CircosArcCollectionRibbonsTest extends BaseGuiTester {
    @Override
    public Circos configureCircos(JFXPanel jfxPanel) {
        ArcCollection mouseGenome = new MouseMM9();
        Circos widget = new Circos(mouseGenome, new ArcEventHandler(), new LinkEventHandler());
        widget.setDrawRibbons(true);

        widget.setStrokeWidth(0.5);
        widget.initialize();
        loadMM9ribbons(widget);
        
        return widget;
    }

    @Test
    public void test() throws Exception {
        initSwing();
        TimeUnit.SECONDS.sleep(5);
    }
}
