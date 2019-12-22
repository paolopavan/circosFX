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
                .setSinkArc(1 + dm3.getArcLengths().length )
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[0])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[1])
                .createLink();
        Link l2 = new LinkBuilder()
                .setSourceArc(1)
                .setSinkArc(3 + dm3.getArcLengths().length )
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[1])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[3])
                .createLink();
        Link l3 = new LinkBuilder()
                .setSourceArc(2)
                .setSinkArc(4 + dm3.getArcLengths().length )
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[2])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[4])
                .createLink();
        Link l4 = new LinkBuilder()
                .setSourceArc(3)
                .setSinkArc(5 + dm3.getArcLengths().length )
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[3])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[5])
                .createLink();
        Link l5 = new LinkBuilder()
                .setSourceArc(4)
                .setSinkArc(6 + dm3.getArcLengths().length )
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[4])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[6])
                .createLink();
        Link l6 = new LinkBuilder()
                .setSourceArc(5)
                .setSinkArc(7 + dm3.getArcLengths().length )
                .setSourceStart(0)
                .setSourceEnd(dm3.getArcLengths()[5])
                .setSinkStart(0)
                .setSinkEnd(dm6.getArcLengths()[7])
                .createLink();

        widget.addLink(l1, l2, l3, l4, l5, l6);

        return widget;
    }

    @Override
    protected Pane configureGUI(JFXPanel p) throws Exception {
        return null;
    }

    @Test
    public void test() throws Exception {
        runWidget();
        TimeUnit.SECONDS.sleep(105);
    }

}
