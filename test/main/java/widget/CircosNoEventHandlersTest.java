/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.widget;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import main.java.BaseGuiTester;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


/**
 *
 * @author pavanpa
 */
public class CircosNoEventHandlersTest extends BaseGuiTester {

    @Override
    public Circos configureCircos(JFXPanel jfxPanel) throws Exception {
        // no event handlers for this test, must work anyway
        Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, null, null);
        widget.setStrokeWidth(1);
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
