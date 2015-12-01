/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circos.gui;

import circos.widget.Circos;
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
    
    public MultiChart(final Circos ... charts){
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
        
        HBox pane = new HBox(20);
        pane.getChildren().add(pagination);
        pagination.prefWidthProperty().bind(pane.widthProperty());
        
        for (Circos chart: charts){
            chart.prefHeightProperty().bind(pane.heightProperty());
            chart.prefWidthProperty().bind(pane.widthProperty());
        }
        
        pane.prefHeightProperty().bind(this.heightProperty());
        pane.prefWidthProperty().bind(this.widthProperty());
        
        this.getChildren().add(pane);
    }
    
    public void setPage(int page){
        pagination.setCurrentPageIndex(page);
    }
}
