/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiTests;

import circos.models.MouseMM9;
import circos.widget.ArcCollection;
import circos.widget.Circos;
import circos.widget.eventHandlers.ArcEventHandler;
import circos.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

/**
 *
 * @author pavanpa
 */
public class CircosArcColectionConstructorTest extends BaseGuiTester {
    @Override
    void configureCircos(JFXPanel jfxPanel) {
        ArcCollection mouseGenome = new MouseMM9();
        Circos widget = new Circos(mouseGenome, new ArcEventHandler(), new LinkEventHandler());
                
        widget.setStrokeWidth(0.01);
        widget.initialize();
        loadMM9links(widget);
        
        jfxPanel.setScene(new Scene(widget));
        startIncorporatedAnimation(widget);
    }
    
    public static void main(String[] args){
        BaseGuiTester me = new CircosArcColectionConstructorTest();
        me.initSwing();
    }
}
