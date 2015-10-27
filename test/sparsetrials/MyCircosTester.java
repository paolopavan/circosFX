package sparsetrials;


import circos.io.CircosLinksAndBundlesLoader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import circos.models.MouseMM9;
import circos.widget.ArcCollection;
import circos.widget.Circos;
import circos.widget.Link;
import circos.widget.LinkBuilder;
import circos.widget.UnconsistentDataException;
import circos.widget.eventHandlers.ArcEventHandler;
import circos.widget.eventHandlers.LinkEventHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pavanpa
 */
public class MyCircosTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {}
                
                JFrame frame = new JFrame("Circos plot test");
                JLabel label = new JLabel("Test environment for Circos widget.", JLabel.HORIZONTAL);
                /**
                * don't know why, but the following is needed to avoid the throw of
                * Toolkit not initialized exception
                */
                final JFXPanel dummy = new JFXPanel();
                
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                
                frame.add(label, BorderLayout.NORTH);
                JPanel mainPanel = new JPanel();
                frame.add(mainPanel);
                mainPanel.setLayout(new BorderLayout());
                
                new MyCircosTester().init(mainPanel);
                
                frame.pack();
                Dimension d;
                d = new Dimension(220, 240);
                d = new Dimension(700, 700);
                frame.setSize(d);
                frame.setVisible(true);
            }
        });
        
    }
    private void init(final JPanel panel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JFXPanel jfxPanel = new JFXPanel();
                panel.add(jfxPanel);
                panel.setVisible(true);
                
                final String linksResource = "resources/bundles.txt";
                //final String linksResource = "resources/links.txt";
                InputStream linksStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(linksResource);
                
                ArcCollection test = new MouseMM9();
                
                java.util.Date date;
                date = new java.util.Date();
                System.out.println(new Timestamp(date.getTime()));
                CircosLinksAndBundlesLoader testLoader=null;
                try {
                    testLoader = new CircosLinksAndBundlesLoader(linksStream, test, true);
                } catch (ParseException ex) {
                    Logger.getLogger(MyCircosTester.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                date = new java.util.Date();
                System.out.println(new Timestamp(date.getTime()));
                
                //Circos widget = new Circos(new long[]{34,56,90, 65, 10}, new ArcEventHandler(), new LinkEventHandler());
                Circos widget = new Circos(test, new ArcEventHandler(), new LinkEventHandler());
                // funziona anche questo:
                //Circos widget = new Circos(test, null,null);
                
                jfxPanel.setScene(new Scene(widget));
                
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
                Link l3 = new LinkBuilder()
                        .setSourceArc(1)
                        .setSourceStart(28)
                        .setSinkArc(3)
                        .setSinkStart(32)
                        .createLink();
                Link l4 = new LinkBuilder()
                        .setSourceArc(2)
                        .setSourceStart(50)
                        .setSinkArc(0)
                        .setSinkStart(20)
                        .createLink();
                
                try {
                    
                    widget.addLink(l1);
                    widget.addLink(l2);
                    widget.addLink(l3);
                    widget.addLink(l4);
                    
                    //widget.addLink(testLoader.getLinks());
                } catch (UnconsistentDataException e){
                    throw new Error(e);
                }
                
                
                
                widget.doFancyStuffs();
                MyTransition animation = new MyTransition();
                animation.setNode(widget);
                //animation.play();
        }});
    }

    
        
}