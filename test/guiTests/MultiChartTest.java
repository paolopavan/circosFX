/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guiTests;

import circos.widget.Circos;
import circos.widget.eventHandlers.ArcEventHandler;
import circos.widget.eventHandlers.LinkEventHandler;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

/**
 * Designed by Paolo Pavan.
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */

public class MultiChartTest extends BaseGuiTester {

    @Override
    void configureCircos(JFXPanel p) {
        Circos[] widgets = new Circos[] {
            new Circos(new long[]{34,56,90, 65, 10},null,null),
            new Circos(new long[]{34,56,90, 200, 10},null,null),
            new Circos(new long[]{34,56,90, 32, 10},null,null)
        };
        widgets[0].setTitle("Plot 1");
        widgets[1].setTitle("Plot 2");
        widgets[2].setTitle("Plot 3");
        
        for (Circos widget: widgets){
            widget.setStrokeWidth(1);
            widget.initialize();
            loadLinks(widget);
        }
        circos.gui.MultiChart gui = new circos.gui.MultiChart(widgets);
        p.setScene(new Scene(gui));
        
        // only one is moving... ;-)
        widgets[1].doFancyStuffs();
    }
    
    public static void main(String[] args){
        BaseGuiTester me = new MultiChartTest();
        me.initSwing();
    }

}
