/*
 *                    CircosFX development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU General Public Licence v3.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright for this code is held jointly by Paolo Pavan and 
 * individual authors that may want to contribute. These should 
 * be listed in @author doc comments.
 *
 */
/**
 * Loads main.circos color code configuration file
 *
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
 
package tries;

import main.java.widget.Circos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Paolo Pavan
 */
public class Trials extends Pane{
    public Trials(Circos chart){
        try8(chart);
    }
    /**
     * non funziona. main.circos si adatta allo spazio disponibile e visualizza un titolo
     * ma copre il rettangolo disegnato e lo slider
     * @param chart 
     */
    private void try1(Circos chart){
                HBox hbox = new HBox(5);
        BorderPane border = new BorderPane();
        
        ObservableList<Node> guiComponents = hbox.getChildren();
        
        Slider slider = createSlider(1, 10, 0.05);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                //setMethilLevelDistrChartUpperBound((Double)new_val);
            }
        });
        
        Label label = new Label("Test Label:");
        TextField tb = new TextField();
        Button button = new Button("Button...");
        
        ScrollPane scroll = new ScrollPane();
        
        
        scroll.setPrefSize(200, 200);
        Rectangle rect = new Rectangle(200, 200, Color.RED);
        scroll.setContent(rect);
        scroll.setFitToHeight(true);
        
        //chart.setPrefSize(200, 200);
        //chart.heightProperty()///****************
        //chart.setPrefSize(200, 200);
        
        chart.prefHeightProperty().bind(this.heightProperty());
        chart.prefWidthProperty().bind(this.widthProperty());
        
        
        hbox.setAlignment(Pos.CENTER);
        guiComponents.add(slider);
        //guiComponents.add(rect);
        guiComponents.addAll(label, tb, button);
        guiComponents.add(chart);
        
        border.setTop(label);
        border.setRight(slider);
        GridPane grid = new GridPane();
        grid.add(chart, 0, 0);
        border.setCenter(grid);
        //rect.heightProperty().bind(this.heightProperty());
       
        
        // set the chosen layout
        //this.getChildren().add(hbox);
        this.getChildren().add(border);
    }
    /**
     * non funziona: main.circos non si adatta allo spazio disponibile
     * @param chart 
     */
    private void try2(Circos chart){
        // define components:
        Label title = new Label("This chart is:");
        Slider slider = createSlider(1, 10, 0.05);
        
        // define layout
        BorderPane pane = new BorderPane();
        HBox center = new HBox(5, chart);
        pane.setCenter(center);
        pane.setRight(slider);
        pane.setTop(title);
        
        // define chart bindings:
        //center.prefHeightProperty().bind(pane.heightProperty());
        //center.prefWidthProperty().bind(pane.widthProperty());
        //chart.setPrefSize(50, 50);
        chart.prefHeightProperty().bind(center.heightProperty());
        chart.prefWidthProperty().bind(center.widthProperty());
        // add all components
        this.getChildren().add(pane);
    }
    /**
     * funziona, rettangolo prende dimensioni di finestra
     * @param chart 
     */
    private void try3(Circos chart){
        Rectangle rect = new Rectangle(200,200,Color.RED);
        rect.heightProperty().bind(this.heightProperty());
        rect.widthProperty().bind(this.widthProperty());
        
        this.getChildren().add(rect);
    }
    
    private void try4(Circos chart){
        BorderPane pane = new BorderPane();
        
        Rectangle rect = new Rectangle(200,200,Color.RED);
        rect.heightProperty().bind(pane.heightProperty());
        rect.widthProperty().bind(pane.widthProperty());
        
        pane.setCenter(rect);
        this.getChildren().add(pane);
    }
    /**
     * questo funziona, main.circos prende le dimensioni della finestra
     * @param chart 
     */
    private void try5(Circos chart){
        Rectangle rect = new Rectangle(200,200,Color.RED);
        chart.prefHeightProperty().bind(this.heightProperty());
        chart.prefWidthProperty().bind(this.widthProperty());
        
        this.getChildren().add(chart);
    }
    /**
     * non funziona: non riesce a inserire contemporaneamente rettangolo e main.circos
     * @param chart 
     */
    private void try6(Circos chart){
        Rectangle rect = new Rectangle(200,200,Color.RED);
        chart.prefHeightProperty().bind(this.heightProperty());
        chart.prefWidthProperty().bind(this.widthProperty());
        
        BorderPane pane = new BorderPane();
        pane.setCenter(chart);
        pane.setRight(rect);
        
        this.getChildren().add(pane);
    }
    
    private void try7(Circos chart){
        HBox pane = new HBox();
        Rectangle rect = new Rectangle();
        chart.prefHeightProperty().bind(pane.heightProperty());
        chart.prefWidthProperty().bind(pane.widthProperty());
        
        rect.heightProperty().bind(pane.heightProperty());
        rect.widthProperty().bind(pane.widthProperty().divide(2));
        
        pane.prefHeightProperty().bind(this.heightProperty());
        pane.prefWidthProperty().bind(this.widthProperty());
        
        pane.getChildren().add(chart);
        pane.getChildren().add(rect);
        
        this.getChildren().add(pane);
    }
    /**
     * funziona, visualizza uno slider di fianco correttamente dimensionato.
     * lo slider non ha height e width property da impostare
     * @param chart 
     */
    private void try8(Circos chart){
        HBox pane = new HBox(20);
        Node slider = createSlider(1, 10, 0.05);
        chart.prefHeightProperty().bind(pane.heightProperty());
        chart.prefWidthProperty().bind(pane.widthProperty());
        
        //slider.prefHeightProperty().bind(pane.heightProperty());
        //slider.prefWidthProperty().bind(pane.widthProperty().divide(2));
        
        pane.prefHeightProperty().bind(this.heightProperty());
        pane.prefWidthProperty().bind(this.widthProperty());
        
        pane.getChildren().add(chart);
        pane.getChildren().add(slider);
        
        this.getChildren().add(pane);
    }
    
    private Slider createSlider(double chartUpperDefault, double chartUpperBound, double chartTickUnit) {
        Slider slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);
        slider.setMin(0);
        slider.setMax(chartUpperBound);
        slider.setValue(chartUpperDefault);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(chartTickUnit);
        //slider.setMinorTickCount(0.01);
        slider.setBlockIncrement(0.01);
        
        // otherwise below, we also could delegate mediator, but in this case 
        // it is unnecessary because if the GUI that interacts with itself
        //slider.valueProperty().addListener(mediator);
        
        return slider;
    }
}
