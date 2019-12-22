package main.java.widget;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import main.java.BaseGuiTester;
import main.java.models.DrosophilaLowresDM3;
import main.java.models.DrosophilaLowresDM6;
import main.java.templates.ArcCollection;
import main.java.templates.ComparedArcCollection;
import main.java.widget.eventHandlers.ArcEventHandler;
import main.java.widget.eventHandlers.LinkEventHandler;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class CircosComparedArcCollectionTest extends BaseGuiTester {
    @Override
    public Circos configureCircos(JFXPanel jfxPanel) throws Exception {
        ArcCollection dm3 = new DrosophilaLowresDM3();
        ArcCollection dm6 = new DrosophilaLowresDM6();

        ComparedArcCollection test = new ComparedArcCollection(dm3, dm6);

        Circos widget = new Circos(test, new ArcEventHandler(), new LinkEventHandler());
        widget.setDrawRibbons(true);
        widget.setStrokeWidth(1);
        widget.setTitle("Circos ComparedArcCollection Test");
        widget.initialize();

        Link l1 = new LinkBuilder()
                .setSourceArc(0)
                .setSinkArc(0 + dm6.getArcLengths().length)
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[0])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[0])
                .createLink();

        widget.addLink(l1);

        return widget;
    }

    @Override
    protected Pane configureGUI(JFXPanel p) throws RuntimeException {
        return null;
    }

    @Test
    public void test() throws Exception {
        runWidget();
        TimeUnit.SECONDS.sleep(5);
    }

}
