/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTests;

import main.java.widget.Circos;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;


/**
 *
 * @author pavanpa
 */
public class CircosRibbonsTest extends BaseGuiTester{

    @Override
    void configureCircos(JFXPanel jfxPanel) {
        Circos widget = new Circos(new long[]{34,56,90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
        widget.setDrawRibbons(true);
        widget.setStrokeWidth(1);
        widget.setTitle("This is a test");
        widget.initialize();
        loadLinks(widget);
        
        jfxPanel.setScene(new Scene(widget));
        startIncorporatedAnimation(widget);
    }
    
    public static void main(String[] args){
        BaseGuiTester me = new CircosRibbonsTest();
        me.initSwing();
    }
    
}
