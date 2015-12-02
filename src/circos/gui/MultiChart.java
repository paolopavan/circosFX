/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circos.gui;

import circos.widget.Circos;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
        pagination = new Pagination(charts.length);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(charts[pageIndex]);
                
                Label label = new Label(charts[pageIndex].getTitle());
                label.setFont(new Font(20));
                label.setAlignment(Pos.CENTER);
                label.prefWidthProperty().bind(borderPane.widthProperty());
                borderPane.setTop(label);
                return borderPane;
            }
        });
        pagination.currentPageIndexProperty().bindBidirectional(displayedPage);
        
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
