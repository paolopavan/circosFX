package main.java.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import main.java.BaseGuiTester;
import main.java.widget.Circos;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MultiChartTest extends BaseGuiTester {
    @Override
    public Circos configureCircos(JFXPanel p) {
        Circos[] widgets = new Circos[] {
                new Circos(new long[]{34, 56, 90, 65, 10},null,null),
                new Circos(new long[]{34, 56, 90, 200, 10},null,null),
                new Circos(new long[]{34, 56, 90, 32, 10},null,null)
        };
        widgets[0].setTitle("Plot 1");
        widgets[1].setTitle("Plot 2");
        widgets[1].setDrawRibbons(true);
        widgets[2].setTitle("Plot 3");

        for (Circos widget: widgets){
            widget.setStrokeWidth(1);
            widget.initialize();
            loadLinks(widget);
        }
        MultiChart gui = new MultiChart(widgets);
        p.setScene(new Scene(gui));

        // only one is moving... ;-)
        widgets[0].doFancyStuffs();

        return widgets[0];
    }

    @Test
    public void test() {
        try {
            MultiChartTest me = new MultiChartTest();
            me.initSwing();
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            fail();
        }
        assertTrue(true);
    }

}