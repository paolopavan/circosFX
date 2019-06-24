/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.gui;

import main.java.widget.Circos;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 * Designed by Paolo Pavan.
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */

public class MultiChart extends Pane {
    private final Pagination pagination;
    private IntegerProperty displayedPage = new SimpleIntegerProperty(0);
    
    public MultiChart(final Circos ... charts){
        final HBox hbox = new HBox(20);
        final Font captionFont = new Font(20);
        pagination = new Pagination(charts.length);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                VBox box = new VBox();
                box.setAlignment(Pos.CENTER);
                Label label = new Label(charts[pageIndex].getTitle());
                label.setFont(captionFont);
                label.setAlignment(Pos.CENTER);
                label.prefWidthProperty().bind(box.widthProperty());

                box.getChildren().addAll(label, charts[pageIndex]);
                return box;
            }
        });
        pagination.currentPageIndexProperty().bindBidirectional(displayedPage);
        // Patch: avoid pagination to spread too much
        pagination.setMaxWidth(600);
        
        hbox.getChildren().add(pagination);
        
        for (Circos chart: charts){
            chart.prefHeightProperty().bind(hbox.heightProperty());
            chart.prefWidthProperty().bind(hbox.widthProperty());
        }
        
        hbox.prefHeightProperty().bind(this.heightProperty());
        hbox.prefWidthProperty().bind(this.widthProperty());
        
        this.getChildren().add(hbox);
    }
    
    public void setPage(int page){
        pagination.setCurrentPageIndex(page);
        displayedPage.set(page);
        pagination.getPageFactory().call(page);
    }
}
