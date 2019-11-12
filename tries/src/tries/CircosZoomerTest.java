/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tries;

import javafx.scene.layout.Pane;
import main.java.widget.Circos;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;



/**
 *
 * @author pavanpa
 */
public class CircosZoomerTest extends BaseGuiTester {
    @Override
    public Circos configureCircos(JFXPanel jfxPanel) {
        return null;
    }

    @Override
    protected Pane configureGUI(JFXPanel p) throws RuntimeException {
        Circos widget = new Circos(new long[]{34, 56, 90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
        widget.setStrokeWidth(1);
        widget.initialize();
        loadLinks(widget);
        widget.doFancyStuffs();

        Trials gui = new Trials(widget);

        return gui;
    }

    public static void main(String[] a) throws Exception {
        CircosZoomerTest me = new CircosZoomerTest();
        me.runGUI();
        //TimeUnit.SECONDS.sleep(10);
    }
    
}
