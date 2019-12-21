package main.java.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.java.BaseGuiTester;
import main.java.models.*;
import main.java.widget.Circos;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MultiChartTest extends BaseGuiTester {
    @Override
    protected Circos configureCircos(JFXPanel p) throws RuntimeException {
        return null;
    }

    @Override
    public Pane configureGUI(JFXPanel p) {
        Circos[] widgets = new Circos[] {
                new Circos(new DrosophilaHiresDM6(),null,null),
                new Circos(new DrosophilaLowresDM3(),null,null),
                new Circos(new Yeast(),null,null),
                new Circos(new Sorghum(),null,null),
                new Circos(new Dog(),null,null),
                new Circos(new Dog3_1(),null,null)
        };
        widgets[0].setTitle("Drosophila DM6 (Hi-Res)");
        widgets[1].setTitle("Drosophila DM3 (Low-Res)");
        widgets[1].setDrawRibbons(true);
        widgets[2].setTitle("Yeast");
        widgets[3].setTitle("Sorghum");

        for (Circos widget: widgets){
            widget.setStrokeWidth(1);
            widget.initialize();
            loadLinks(widget);
        }
        MultiChart gui = new MultiChart(widgets);

        // only one is moving... ;-)
        widgets[1].doFancyStuffs();

        return gui;
    }

    @Test
    public void test() throws Exception {
        runGUI();
        TimeUnit.SECONDS.sleep(20);
    }

}