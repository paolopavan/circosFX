package main.java;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.java.io.CircosLinksAndBundlesLoader;
import main.java.models.MouseMM9;
import main.java.widget.Circos;
import main.java.widget.Link;
import main.java.widget.LinkBuilder;
import main.java.widget.UnconsistentDataException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;

import javafx.embed.swing.JFXPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author pavanpa
 */
public abstract class BaseGuiTester {
    protected final JFXPanel jfxPanel = new JFXPanel();
    static private int xyIncrement = 250;
    static private int xyLimit = 3 * xyIncrement;
    static private int x = 0;
    static private int y = -1 * xyIncrement;
    private Dimension d = new Dimension(700, 700);

    public void runGUI() throws Exception {
        Pane gui = configureGUI(jfxPanel);
        execThread(gui);
    }
    public void runWidget() throws Exception {
        Circos widget = configureCircos(jfxPanel);
        widget.doFancyStuffs();
        execThread(widget);
    }

    private void execThread(Pane widget) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {}
                
                JFrame frame = new JFrame("Circos plot test");
                JLabel label = new JLabel("Swing test environment for Circos widget.", JLabel.HORIZONTAL);
                /**
                * don't know why, but the following is needed to avoid the throw of
                * Toolkit not initialized exception
                */
                final JFXPanel dummy = new JFXPanel();
                
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                updateCoordinates();
                frame.setLocation(x,y);

                frame.add(label, BorderLayout.NORTH);
                JPanel mainPanel = new JPanel();
                frame.add(mainPanel);
                mainPanel.setLayout(new BorderLayout());
                
                mainPanel.add(jfxPanel);
                mainPanel.setVisible(true);
                
                Platform.runLater(new Runnable() {
                @Override
                    public void run() {
                        jfxPanel.setScene(new Scene(widget));
                    }
                });

                frame.pack();
                frame.setSize(d);
                frame.setVisible(true);
            }
        });
    }

    protected abstract Circos configureCircos(final JFXPanel p) throws RuntimeException;
    protected abstract Pane configureGUI(final JFXPanel p) throws RuntimeException;

    private void updateCoordinates() {
        y += xyIncrement;
        if (y >= xyLimit) {
            x += xyIncrement;
            y = 0;
        }
    }

    protected void loadMM9links(Circos circos){
        final String linksResource = "resources/bundles.txt";
        InputStream linksStream = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(linksResource);

        try {
            java.util.Date date = new java.util.Date();
            System.out.println("Started loading links at: "+new Timestamp(date.getTime()));
            CircosLinksAndBundlesLoader testLoader=null;
            
            testLoader = new CircosLinksAndBundlesLoader(linksStream,  new MouseMM9(), true);
        
            date = new java.util.Date();
            System.out.println("Finished loading links at: "+new Timestamp(date.getTime()));
            System.out.println("Total number of links loaded: "+testLoader.getLinks().size());
            
            circos.addLink(testLoader.getLinks());
        } catch (UnconsistentDataException e){
            throw new Error(e);
        } catch (ParseException ex) {
            throw new Error(ex);
        }
    }

    protected void loadMM9ribbons(Circos circos){
        Link l1 = new LinkBuilder()
                .setSourceArc(0)
                .setSourceStart(97195432)
                .setSourceEnd(197195432)
                .setSinkArc(3)
                .setSinkStart(80000000)
                .setSinkEnd(100000000)
                .createLink();

        Link l2 = new LinkBuilder()
                .setSourceArc(2)
                .setSourceStart(50000000)
                .setSourceEnd(140000000)    // TODO: implement a check on congruence of coordinates and arcs?
                .setSinkArc(17)
                .setSinkStart(60319150)
                .setSinkEnd(68319150)
                .createLink();

        Link l3 = new LinkBuilder()
                .setSourceArc(10)
                .setSourceStart(60000000)
                .setSourceEnd(80000000)
                .setSinkArc(17)
                .setSinkStart(90319150)
                .setSinkEnd(98319150)
                .setSinkEnd(90772031)
                .createLink();

        Link l4 = new LinkBuilder()
                .setSourceArc(4)
                .setSourceStart(52537259)
                .setSourceEnd(102537259)
                .setSinkArc(19)
                .setSinkStart(66650296)
                .setSinkEnd(106650296)
                .createLink();

        Link l5 = new LinkBuilder()
                .setSourceArc(7)
                .setSourceStart(52537259)
                .setSourceEnd(66650296)
                .setSinkArc(18)
                .setSinkStart(20537259)
                .setSinkEnd(40650296)
                .createLink();

        Link l6 = new LinkBuilder()
                .setSourceArc(8)
                .setSourceStart(52537259)
                .setSourceEnd(66650296)
                .setSinkArc(18)
                .setSinkStart(20537259)
                .setSinkEnd(40650296)
                .createLink();

        Link l7 = new LinkBuilder()
                .setSourceArc(5)
                .setSourceStart(120000000)
                .setSourceEnd(40000000)
                .setSinkArc(11)
                .setSinkStart(20000000)
                .setSinkEnd(100000000)
                .createLink();

        // inverse ribbon should fold
        Link l_inverse = new LinkBuilder()
                .setSourceArc(4)
                .setSourceStart(52537259)
                .setSourceEnd(102537259)
                .setSinkArc(19)
                .setSinkStart(106650296)
                .setSinkEnd(66650296)
                .createLink();

        Link l_self = new LinkBuilder()
                .setSourceArc(13)
                .setSourceStart(52537259)
                .setSourceEnd(66650296)
                .setSinkArc(13)
                .setSinkStart(102537259)
                .setSinkEnd(106650296)
                .createLink();

        try {
            circos.addLink(l1);
            circos.addLink(l2);
            circos.addLink(l3);
            circos.addLink(l4);
            circos.addLink(l5);
            circos.addLink(l6);
            circos.addLink(l7);
            circos.addLink(l_inverse);
            circos.addLink(l_self);
        } catch (UnconsistentDataException e){
            throw new Error(e);
        }
    }
    
    protected void loadLinks(Circos circos) {
        Link l1 = new LinkBuilder()
                .setSourceArc(0)
                .setSourceStart(0)
                .setSinkArc(2)
                .setSinkStart(0)
                .createLink();
        Link l2 = new LinkBuilder()
                .setSourceArc(4)
                .setSourceStart(0)
                .setSinkArc(2)
                .setSinkStart(0)
                .createLink();
        // Ribbon
        Link l3 = new LinkBuilder()
                .setSourceArc(1)
                .setSourceStart(28)
                .setSourceEnd(40)
                .setSinkArc(3)
                .setSinkStart(32)
                .setSinkEnd(50)
                .createLink();
        Link l4 = new LinkBuilder()
                .setSourceArc(2)
                .setSourceStart(50)
                .setSinkArc(0)
                .setSinkStart(20)
                .createLink();

        // Ribbon
        Link l_inverse = new LinkBuilder()
                .setSourceArc(2)
                .setSourceStart(50)
                .setSourceEnd(60)
                .setSinkArc(2)
                .setSinkStart(20)
                .setSinkEnd(40)
                .createLink();
        Link l_self = new LinkBuilder()
                .setSourceArc(1)
                .setSourceStart(20)
                .setSinkArc(1)
                .setSinkStart(20)
                .createLink();

        try {

            circos.addLink(l1);
            circos.addLink(l2);
            circos.addLink(l3);
            circos.addLink(l4);
            circos.addLink(l_inverse);
            circos.addLink(l_self);
        } catch (UnconsistentDataException e){
            throw new Error(e);
        }
    }
    
    protected void alternateAnimationTest(Circos widget){
        /*
        MyTransition animation = new MyTransition();
        animation.setNode(widget);
        animation.play();
        */
    }
}

