package guiTests;

import circos.io.CircosLinksAndBundlesLoader;
import circos.models.MouseMM9;
import circos.widget.Circos;
import circos.widget.Link;
import circos.widget.LinkBuilder;
import circos.widget.UnconsistentDataException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import sparsetrials.MyCircosTester;

/**
 *
 * @author pavanpa
 */
public abstract class BaseGuiTester {
    public void initSwing() {
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
                frame.setVisible(true);
                
                frame.add(label, BorderLayout.NORTH);
                JPanel mainPanel = new JPanel();
                frame.add(mainPanel);
                mainPanel.setLayout(new BorderLayout());
                
                final JFXPanel jfxPanel = new JFXPanel();
                mainPanel.add(jfxPanel);
                mainPanel.setVisible(true);
                
                Platform.runLater(new Runnable() {
                @Override
                    public void run() {
                        configureCircos(jfxPanel);
                    }
                });
                
                frame.pack();
                Dimension d;
                
                d = new Dimension(220, 240);
                d = new Dimension(700, 700);
                frame.setSize(d);
                
                frame.setVisible(true);
            }
        });
        
    }
    
    abstract void configureCircos(final JFXPanel p);
    
    void loadMM9links(Circos circos){
        final String linksResource = "resources/bundles.txt";
        //final String linksResource = "resources/links.txt";
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
    
    void loadLinks(Circos circos) {
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
        Link l_inverse = new LinkBuilder()
                .setSourceArc(2)
                .setSourceStart(50)
                .setSinkArc(2)
                .setSinkStart(20)
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
    
    void startIncorporatedAnimation(Circos widget){
        widget.doFancyStuffs();
    }
    
    void alternateAnimationTest(Circos widget){
        /*
        MyTransition animation = new MyTransition();
        animation.setNode(widget);
        animation.play();
        */
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pavanpa
 */
class MyGuiTester {
    /**
     * @param args the command line arguments
     */
    
    private void init(final JPanel panel) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //
        }});
    }

    
        
}
