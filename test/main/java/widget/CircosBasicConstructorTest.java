/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.widget;

import javafx.scene.layout.Pane;
import main.java.BaseGuiTester;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


/**
 *
 * @author pavanpa
 */
public class CircosBasicConstructorTest extends BaseGuiTester {
    @Override
    public Circos configureCircos(JFXPanel jfxPanel) throws Exception {
        Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
        widget.setStrokeWidth(1);
        widget.setTitle("This is a test");
        widget.initialize();
        loadLinks(widget);

        return widget;
    }

    @Override
    protected Pane configureGUI(JFXPanel p) throws Exception {
        return null;
    }

    @Test
    public void test() throws Exception {
        runWidget();
        TimeUnit.SECONDS.sleep(5);
    }
    
}
