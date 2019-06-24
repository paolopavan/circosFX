/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTests;

import main.java.widget.Circos;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;


/**
 *
 * @author pavanpa
 */
public class CircosNoEventHandlersTest extends BaseGuiTester{

    @Override
    void configureCircos(JFXPanel jfxPanel) {
        // no event handlers for this test, must work anyway
        Circos widget = new Circos(new long[]{34,56,90, 65, 10}, null, null);
        widget.setStrokeWidth(1);
        widget.initialize();
        loadLinks(widget);
        
        jfxPanel.setScene(new Scene(widget));
    }
    
    public static void main(String[] args){
        guiTests.CircosBasicConstructorTest me = new guiTests.CircosBasicConstructorTest();
        me.initSwing();
    }
    
}
