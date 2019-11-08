/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.widget;

import main.java.BaseGuiTester;
import main.java.widget.Circos;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import main.java.widget.CircosBasicConstructorTest;
import org.junit.jupiter.api.Test;


/**
 *
 * @author pavanpa
 */
public class CircosNoEventHandlersTest extends BaseGuiTester {

    @Override
    public void configureCircos(JFXPanel jfxPanel) throws RuntimeException {
        // no event handlers for this test, must work anyway
        Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, null, null);
        widget.setStrokeWidth(1);
        widget.initialize();
        loadLinks(widget);
        
        jfxPanel.setScene(new Scene(widget));
    }

    @Test
    public void test() throws Exception {
        CircosBasicConstructorTest me = new CircosBasicConstructorTest();
        me.initSwing();
    }
    
}
