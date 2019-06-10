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
 
 package circos.widget;


import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Designed by Paolo Pavan
 * You may want to find my contacts on Github and LinkedIn for code info 
 * or discuss major changes.
 * https://github.com/paolopavan
 * 
 * @author Paolo Pavan
 */
public class Circos extends Pane {
    private final EventHandler arcEventHandler;
    private final EventHandler linkEventHandler;
    private NumberBinding plotCenterBinding;
    private NumberBinding plotRadiusBinding;
    /**
     * multiplier for plot magnification
     */
    private DoubleProperty magnificationProperty;
    
    /**
     * some GUI elements
     */
    /**
     * caption shown on upper side of the picture
     */
    private Label captionLabel = new Label("Caption");
    
    private Group radialElements;
    private RotateTransition rotateTransition;
    
    private Color defaultBackgroundColor = Color.WHITE;
    private Color defaultArcColor = Color.CORAL;
    
    
    // DATA STRUCTURES
    /**
     * contains the length expressed in original Units of measurement 
     * (eg nucleotides length, if arcs represent chromosomes as usual).
     * This measure will be converted and length normalized
     */
    private long[] arcLengths; 
    private long arcLengthsSum;
    /**
     * optional, contains links representation
     */
    private List<Link> links = new ArrayList();
    /**
     * stroke width used to draw links
     * quad.setStrokeWidth(0.01); // full of links 25000
     * quad.setStrokeWidth(0.08); // lot of links 4500
     * quad.setStrokeWidth(1.8); // few links 10
     */
    private double strokeWidth = 0.08; 
    /**
     * contains the names of the arcs
     */
    private String[] arcNames ;
    /**
     * maintain the starting point in polar coordinates of every arc/chromosome
     * ** Include gaps **
     */
    private double arcStarts[];
    
    private Color[] arcColors;
    private ArcCollection arcCollection;
    /**
     * gap distance expressed in radians between two adjacent arcs
     */
    private double arcsGap = 0.1;
    
    private double paddingCircleDistance = 50;
    
    /**
     * gap distance expressed in pixels from arcs crown to circle outside
     * 
     */
    private int boundaryCircleGap = 3;
    
    /**
     * some configuration switches
     */
    private boolean drawOutboundCircle = false;
    private boolean drawRuler = true;
    private boolean drawSubregions = true;
    
    /**
     * gap from link start and arc applied to sources
     * In my opinion it should be:
     * sourceGapFromArc > sinkGapFromArc
     */
    private double sourceGapFromArc = 10;
    /**
     * gap from link end and arc applied to sink
     * In my opinion it should be:
     * sourceGapFromArc > sinkGapFromArc
     */
    private double sinkGapFromArc = 2;
    /**
     * arc thickness
     */
    private DoubleProperty circleThickness = new SimpleDoubleProperty(13);
    
    private DoubleProperty linkTension = new SimpleDoubleProperty(0);

    /**
     * property to declare if entire regions between dataXstart and dataXend
     * must be drawn as a ribbon. 
     * If it is false, will be taken the mean between the two points
     */
    private boolean drawRibbons = false;
    
    /**
     * set with relationIntensity, can also be set directly to 
     * specify a different upper bound
     */
    private double relationIntensityMaximum;
    
    /**
     * Minimum length allowed for the unit of a ruler. 
     * below this number of pixels, do not display the minor tick and the ruler numbers
     * 
     * Note that changing the plot size (or zooming in) and remaining this value constant 
     * more elements will be displayed in detail
     */
    private final double rulerUnitLengthThreshold = 2.5d;
    /**
     * Minimum length allowed for arc or subregion of the arc.
     * below this number of pixels, do not display the arc member.
     * 
     * Note that changing the plot size (or zooming in) and remaining this value constant 
     * more elements will be displayed in detail
     */
    private final double arcLengthThreshold = 0d;
    /**
     * Minimum width allowed for arc representation.
     * Below this number of pixels, do not display the link.
     * 
     * Note that changing the plot size (or zooming in) and remaining this value constant 
     * more elements will be displayed in detail
     */
    private double linkWidthThreshold = 0d;

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setLinkWidthThreshold(double linkWidthThreshold) {
        this.linkWidthThreshold = linkWidthThreshold;
    }

    /**
     * Base constructor.
     * It initializes a plot using a simple vector
     * @param arcLengths a vector representing lengths of arcs/chromosomes
     * @param arcEventHandler: Extend ArcEventHandler class to add your application logic.
     * It can be null if you don't have to manage this event
     * @param linkEventHandler: Extend LinkEventHandler class to add your application logic.
     * It can be null if you don't have to manage this event
     */
    public Circos(long[] arcLengths, EventHandler arcEventHandler, EventHandler linkEventHandler){
        this.arcEventHandler = arcEventHandler;
        this.linkEventHandler = linkEventHandler;
        
        arcNames = new String[arcLengths.length];
        for (int i=0; i < arcLengths.length; i++) arcNames[i] = "Arc "+ i;
        
        setArcLengths(arcLengths);
    }
    
    /**
    * Advanced Constructor. 
    * It uses an ArcCollection object to inherit complex arc configurations
    * @param arcCollection
    * @param arcEventHandler: Extend ArcEventHandler class to add your application logic. 
    * It can be null if you don't have to manage this event
    * @param linkEventHandler: Extend LinkEventHandler class to add your application logic. 
    * It can be null if you don't have to manage this event
    */
    public Circos(ArcCollection arcCollection, EventHandler arcEventHandler, EventHandler linkEventHandler){
        this.arcEventHandler = arcEventHandler;
        this.linkEventHandler = linkEventHandler;
        this.arcCollection = arcCollection;
        
        arcNames = arcCollection.getArcNames();
        arcColors = arcCollection.getArcColors();
        
        setArcLengths(arcCollection.getArcLengths());
    }

    public void setDrawRibbons(boolean drawRibbons) {
        this.drawRibbons = drawRibbons;
    }

    final public void setArcLengths(long[] arcLengths) {
        // final method because it is used in constructor
        this.arcLengths = arcLengths;
        long sum=0;
        for (int i =0; i< arcLengths.length; i++) sum += arcLengths[i];
        arcLengthsSum = sum;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }
    
    /**
     * add a single link instance to the plot.
     * BEWARE: coordinates in circos are 0 based! Both chromosomes and links start,end
     * @param l 
     * @throws circos.widget.UnconsistentDataException 
     */
    public void addLink(Link l) throws UnconsistentDataException{
        if (radialElements == null) throw new IllegalStateException("Plot not initialized yet");
        this.links.add(l);
        ObservableList<Node> widgetElements = radialElements.getChildren();
        CircosLink circosLink = buildLink(l);
        widgetElements.add(circosLink);
    }
    /**
     * add a collection of links to the plot
     * BEWARE: coordinates in circos are 0 based! Both chromosomes and links start,end
     * @param links 
     * @throws circos.widget.UnconsistentDataException 
     */
    public void addLink(Link... links) throws UnconsistentDataException{
        
        // nuovo thread?
        
        ObservableList<Node> widgetElements = radialElements.getChildren();
        
        for (Link l: links) {
            CircosLink circosLink = buildLink(l);
            widgetElements.add(circosLink);
        }
    }
    /**
     * add a collection of links to the plot
     * BEWARE: coordinates in circos are 0 based! Both chromosomes and links start,end
     * @param links 
     * @throws circos.widget.UnconsistentDataException 
     */
    public void addLink(List<Link> links) throws UnconsistentDataException{
        addLink(links.toArray(new Link[links.size()]));
    }

    public void setArcNames(String[] arcNames) {
        this.arcNames = arcNames;
    }
    
    public void setTitle(String t){
        captionLabel.setText(t);
    }
    
    public String getTitle(){
        return captionLabel.getText();
    }

    public NumberBinding getPlotRadiusBinding() {
        return plotRadiusBinding;
    }
    
    
    /**
     * returns the translation in radians of a point projected to an arc
     * @param x
     * @return 
     */
    private double translate(long x){
        double gapPortion = arcLengths.length * arcsGap;
        return (double) x / arcLengthsSum * (2 * PI - gapPortion);
    }
    
    /**
     * Initialize the plot. 
     * Must always be done after construction and before loading links. 
     * It is not called by constructor to allow further customization of the plot
     * with setter methods immediately after construction.
     */
    public void initialize() {
        links.clear();
        initPlot();
        initEffects();
    }

    
    private void initPlot(){
        // initPlot bindings:
        magnificationProperty = new SimpleDoubleProperty(1);
        // binds plot center to the available space in window 
        // (whatever is less between height and width
        //plotCenterBinding = Bindings.min(this.heightProperty(), this.widthProperty()).divide(2).multiply(magnificationProperty);
        plotCenterBinding = Bindings.min(this.heightProperty(), this.widthProperty()).divide(2);
        // plot radius takes less than the space available to allow room for external graphical elements as arc captions
        plotRadiusBinding = Bindings.subtract(plotCenterBinding,paddingCircleDistance);
        circleThickness.bind(plotCenterBinding.divide(30));
        
        BorderPane widgetPanel = new BorderPane();
        final Pane nodeContainer = new Pane();
        ScrollPane sp = new ScrollPane(); 
        sp.setPannable(true);
                
        //sp.setPrefSize(widgetPanel.getWidth(), widgetPanel.getHeight());
        //sp.setPrefSize(this.getWidth(), this.getHeight());
        
        this.getChildren().add(widgetPanel);
        Font f = new Font(21);
        captionLabel.setFont(f);
        Slider slider = createSlider();
        
        radialElements = new Group();
        nodeContainer.getChildren().add(sp);
        sp.setContent(radialElements);
        sp.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {

            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                nodeContainer.setPrefHeight(500);
                nodeContainer.setPrefWidth(500);
                //nodeContainer.getBoundsInParent()
                //new StackPane().
            }
            
        });
        
        //widgetPanel.setLeft(slider);
        widgetPanel.setCenter(radialElements);//was sp
        //widgetPanel.setTop(captionLabel);
        
        
        ObservableList<Node> widgetElements = radialElements.getChildren();
        
        // initPlot radians arc length
        double alpha = 0;
        arcStarts = new double[arcLengths.length];
        for (int i=0; i < arcLengths.length; i++){
            arcStarts[i] = alpha;
            double radiansLength = translate(arcLengths[i]);
            alpha += radiansLength + arcsGap;
        }
        
        // initPlot arcs
        for (int i=0; i < arcLengths.length; i++){
            widgetElements.add(buildArc(i));
            if (drawRuler) widgetElements.add(ruler(i));
        }
        
        if (drawOutboundCircle) widgetElements.add(boundaryCircle(boundaryCircleGap, Color.BLACK));
        widgetElements.add(cleaner());
        // invisible graphic element seted outside of all other elements
        // to make chart boundaries regular
        Circle padder = boundaryCircle(paddingCircleDistance, new Color(0,0,0,0));
        widgetElements.add(padder);
    }
    
    private void initEffects() {
        rotateTransition = RotateTransitionBuilder.create()
                .node(radialElements)
                .duration(Duration.seconds(6))
                .fromAngle(0)
                .toAngle(720)
                .cycleCount(2)
                .autoReverse(true)
                .build();
        
    }
    
    public void doFancyStuffs(int n){
        rotateTransition.setCycleCount(n);
        rotateTransition.play();
    }
    
    public void doFancyStuffs(){
        rotateTransition.play();
    }
    /**
     * draw primitives for a single arc (chromosome)
     * @param i the arc to be draw
     * @return 
     */
    private Group buildArc(int i){
        Group g = new Group();
        ObservableList<Node> members = g.getChildren();
        
        double alpha = arcStarts[i];
        double radiansLength = translate(arcLengths[i]);
        
        CircosArc arc = drawArc(alpha, radiansLength);
        arc.setRepresentation(arcNames[i]);
        
        // overrides the default color setted by getArc()
        if (arcColors != null) arc.setStroke(arcColors[i]);
        
        members.add(arc);
        
        // draw SubRegion s
        if (drawSubregions && arcCollection != null){
            List<SubRegion> l = arcCollection.getSubregions(i);
            for (SubRegion r: l) {
                alpha = arcStarts[i] + translate(r.getStart());
                radiansLength = translate(r.getEnd()-r.getStart());
                arc = drawArc(alpha, radiansLength);
                arc.setRepresentation(r.getLabel());
                
                Color fadedColor = new Color(
                        r.getColor().getRed(),
                        r.getColor().getGreen(),
                        r.getColor().getBlue(),
                        0.3
                );
                arc.setStroke(fadedColor);
                
                Tooltip t = new Tooltip(r.getLabel());
                Tooltip.install(arc, t);
                
                members.add(arc);
            }
        }
                
        return g;
    }
    
    private CircosLink buildLink(Link l) throws UnconsistentDataException {
        CircosLink curve = new CircosLink();
        curve.setRepresentation(l);
        
        // whatever we will draw links or ribbons will use a different primitive
        QuadCurve quad;
        Path path;
        double from, to;
        
        /**
         * since links' configuration is not checked for speed reasons I check
         * it here rising an exception in case they do not fit the constructed plot
         */
        try {
            // adjustments allow links that describe a interval (start and end coordinates very different)
            // to be depicted with a drawing centered in the interval.
            long sourceAdjustment = (l.getSourceEnd()-l.getSourceStart())/2;
            from = arcStarts[l.getSourceArc()] + translate(l.getSourceStart()+sourceAdjustment);
            
            long sinkAdjustment = (l.getSinkEnd()-l.getSinkStart())/2;
            to = arcStarts[l.getSinkArc()] + translate(l.getSinkStart()+sinkAdjustment);
            // the following epsilon adjustment is needed to draw self relations.
            // A quad curve with start end at the same point is not drawn.
            // NOTE: 
            // epsilon = 0.001 draws a line, the loop is invisible
            // epsilon = 0.01 visualize a tiny loop, it looks better
            if (from == to) to+=0.01;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new UnconsistentDataException(
                    l.toString()+" is not compatible with declared arc collection"
            );
        }
            
        if (drawRibbons){
            throw new UnsupportedOperationException("Not supported yet."); 
        } else {
            quad = drawCurve(from, to);
            curve.getChildren().add(quad);
        }
        curve.setOnMouseClicked(linkEventHandler);
        
        return curve;
    }
    
    private CircosArc drawArc(double startAngle, double radiansLength){
        CircosArc arc = new CircosArc();
        
        arc.centerXProperty().bind(plotCenterBinding);
        arc.centerYProperty().bind(plotCenterBinding);
        arc.radiusXProperty().bind(plotRadiusBinding);
        arc.radiusYProperty().bind(plotRadiusBinding);  
        
        // Visualize element only if it is big enough. Check that: 
        // radiansLength * plotRadius > threshold;
        BooleanBinding visibleBinding = Bindings.greaterThan(plotRadiusBinding.multiply(radiansLength), arcLengthThreshold);
        arc.visibleProperty().bind(visibleBinding);
        
        // Those methods needs degrees input. 
        // Sorry about that, I realize it only at debug time
        arc.setStartAngle(startAngle * 180 / PI);
        arc.setLength(radiansLength * 180 / PI);
        
        arc.setType(ArcType.ROUND);
        arc.strokeWidthProperty().bind(circleThickness);
        
        // set default arc color, it can be overridden by super caller
        arc.setStroke(defaultArcColor);
        arc.setStrokeType(StrokeType.INSIDE);
        arc.setFill(null);
        
        arc.setOnMouseClicked(arcEventHandler);
        
        return arc;
    }
    
    private QuadCurve drawCurve(double startAngle, double endAngle){
        // mmm there is something that escapes to me about getting radius back...
        DoubleProperty radius = new SimpleDoubleProperty(plotRadiusBinding.doubleValue());
        radius.bind(plotRadiusBinding);
        DoubleProperty center = new SimpleDoubleProperty(plotCenterBinding.doubleValue());
        center.bind(plotCenterBinding);
        
        NumberBinding xSourceBinding = new CartesianXBinding(radius, center, startAngle, circleThickness, sourceGapFromArc);
        NumberBinding ySourceBinding = new CartesianYBinding(radius, center, startAngle, circleThickness, sourceGapFromArc);
        NumberBinding xSinkBinding = new CartesianXBinding(radius, center, endAngle, circleThickness, sinkGapFromArc);
        NumberBinding ySinkBinding = new CartesianYBinding(radius, center, endAngle, circleThickness, sinkGapFromArc);
        
        QuadCurve quad = new QuadCurve();
        quad.setStroke(Color.GREY);
        
        
        // it could be managed also by a binder
        quad.setStrokeWidth(strokeWidth); 
        
        
        quad.setFill(null);
        
        quad.startXProperty().bind(xSourceBinding);
        quad.startYProperty().bind(ySourceBinding);
        quad.endXProperty().bind(xSinkBinding);
        quad.endYProperty().bind(ySinkBinding);
        quad.controlXProperty().bind(plotCenterBinding);
        quad.controlYProperty().bind(plotCenterBinding.add(linkTension));
        
        // Visualize arc only if it links two points that are distant enough. Check that: 
        //min(distance, 2*PI-distance) * plotRadius > threshold;
        double distance = abs(endAngle - startAngle);
        double l = min(distance, 2*PI-distance);
        BooleanBinding visibleBinding = Bindings.greaterThan(plotRadiusBinding.multiply(l), linkWidthThreshold);
        quad.visibleProperty().bind(visibleBinding);
        
        return quad;
    }
    
    /**
     * draws an optional bound circle outside the arcs crown
     * @return 
     */
    private Circle boundaryCircle(double gap, Color c) {
        Circle outbound = new Circle();
        
        outbound.centerXProperty().bind(plotCenterBinding);
        outbound.centerYProperty().bind(plotCenterBinding);
        outbound.radiusProperty().bind(plotRadiusBinding.add(gap));
        
        outbound.setStroke(c);        
        
        outbound.setStrokeType(StrokeType.INSIDE);
        outbound.setFill(null);
        
        return outbound;
    }
    
    /**
     * This is a patch because at jfx version 2.2 I haven't find the way to do 
     * not draw the "radial" elements of a round arc using StrokeType.INSIDE
     * Moreover, since I need to paint an annulus and I want to use a simple 
     * primitive for speeding draw, the round arc is the only one that has 
     * boundaries that apply to this case.
     * @return Circle object to be painted as last in the initPlot phase
     */
    private Circle cleaner(){
        Circle inbound = new Circle();
        
        inbound.centerXProperty().bind(plotCenterBinding);
        inbound.centerYProperty().bind(plotCenterBinding);
        inbound.radiusProperty().bind(plotRadiusBinding.subtract(circleThickness));
        
        inbound.setStrokeType(StrokeType.INSIDE);
        inbound.setFill(defaultBackgroundColor);
        
        return inbound;
    }
    
    private Group ruler(int a){
        final double rulerDistanceFromArc = 10;
        final double namesDistanceFromArc = 30;
        final double mayorTickLength = 5;
        final boolean useMinorTick = true;
        final long mayorTickInterval = 20000000;
        final DoubleProperty thickness = new SimpleDoubleProperty(0.1);
        DoubleProperty center = new SimpleDoubleProperty(plotCenterBinding.doubleValue());
        center.bind(plotCenterBinding);
        
        Group g = new Group();
        ObservableList<Node> members = g.getChildren();
        
        double alpha = arcStarts[a];
        double radiansLength = translate(arcLengths[a]);
        
        Arc arc = new Arc();
        
        arc.centerXProperty().bind(plotCenterBinding);
        arc.centerYProperty().bind(plotCenterBinding);
        arc.radiusXProperty().bind(plotRadiusBinding.add(rulerDistanceFromArc));
        arc.radiusYProperty().bind(plotRadiusBinding.add(rulerDistanceFromArc));
        
        arc.setStartAngle(alpha * 180 / PI);
        arc.setLength(radiansLength * 180 / PI);
        
        arc.setType(ArcType.OPEN);
        arc.setStrokeWidth(0.1);
        
        arc.setStroke(Color.BLACK);
        arc.setStrokeType(StrokeType.INSIDE);
        arc.setFill(null);
        
        //arc.setOnMouseClicked(rulerEventHandler); //?
        
        members.add(arc);
        
        // mmm there is something that escapes to me about getting the radius back...
        DoubleProperty radiusInternal = new SimpleDoubleProperty(plotRadiusBinding.doubleValue());
        radiusInternal.bind(plotRadiusBinding);
        
        // Visualize minor tick only if major ticks are distant enough. Check that: 
        //unitLength * plotRadius > threshold;
        double unitLength = translate(mayorTickInterval/2);
        BooleanBinding visibleBinding = Bindings.greaterThan(plotRadiusBinding.multiply(unitLength), rulerUnitLengthThreshold);
        
        boolean isMajorTick = true;
        for (int i=0; i < arcLengths[a]; i+=mayorTickInterval/2){
            if (!useMinorTick && !isMajorTick) continue;
            double tick;
            
            tick = mayorTickLength;
            if (!isMajorTick) tick = mayorTickLength/2;
            
            DoubleProperty radiusExternal = new SimpleDoubleProperty(
                    plotRadiusBinding.doubleValue() + 
                            rulerDistanceFromArc + tick);
            radiusExternal.bind(plotRadiusBinding.add(rulerDistanceFromArc+tick));

            NumberBinding xStartBinding = new CartesianXBinding(radiusInternal, center, alpha, thickness, 0);
            NumberBinding yStartBinding = new CartesianYBinding(radiusInternal,center, alpha, thickness, 0);
            NumberBinding xEndBinding = new CartesianXBinding(radiusExternal,center, alpha, thickness, 0);
            NumberBinding yEndBinding = new CartesianYBinding(radiusExternal, center, alpha, thickness, 0);

            Line line = new Line();
            line.setStroke(Color.GAINSBORO);
            line.startXProperty().bind(xStartBinding);
            line.startYProperty().bind(yStartBinding);
            line.endXProperty().bind(xEndBinding);
            line.endYProperty().bind(yEndBinding);
            line.setStrokeType(StrokeType.CENTERED);
            if (!isMajorTick) line.visibleProperty().bind(visibleBinding);
            
            members.add(line);
            
            Text t = new Text(i/1000000+"");
            t.xProperty().bind(xEndBinding);
            t.yProperty().bind(yEndBinding);
            t.setFont(new Font(6));
            t.visibleProperty().bind(visibleBinding);
            
            if (isMajorTick) members.add(t);
            
            alpha += translate(mayorTickInterval/2);
            isMajorTick = !isMajorTick;
        }
        
        // set arc names
        DoubleProperty namesDistance = new SimpleDoubleProperty(
                    plotRadiusBinding.doubleValue() + 
                            namesDistanceFromArc);
        namesDistance.bind(plotRadiusBinding.add(namesDistanceFromArc));
        
        double polarPosition = arcStarts[a] + translate(arcLengths[a]/2);
        NumberBinding xBinding = new CartesianXBinding(namesDistance, center, polarPosition, thickness, 0);
        NumberBinding yBinding = new CartesianYBinding(namesDistance,center, polarPosition, thickness, 0);
        Text nameLabel = new Text(arcNames[a]);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        
        double nameLabelLength = nameLabel.getLayoutBounds().getWidth();
        
        nameLabel.setRotate(toDegrees(PI/2 - polarPosition));
        nameLabel.xProperty().bind(xBinding.subtract(nameLabelLength/2));
        nameLabel.yProperty().bind(yBinding);
        nameLabel.setFont(new Font(12));
        
        members.add(nameLabel);
        
        return g;
    }
    
    private Slider createSlider() {
        Slider slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);
        slider.setMin(1);
        slider.setMax(10);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.5);
        //slider.setMinorTickCount(0.01);
        slider.setBlockIncrement(0.01);
        
        
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                magnificationProperty.set(new_val.doubleValue());
            }
        });
        // otherwise below, we also could delegate mediator, but in this case 
        // it is unnecessary because if the GUI that interacts with itself
        //slider.valueProperty().addListener(mediator);
        
        return slider;
    }
}
class radialElementsBinding extends DoubleBinding {
    private ReadOnlyDoubleProperty height;
    private ReadOnlyDoubleProperty width;
    private DoubleProperty magnification;
    
    private double savedHeight, savedWidth;
    radialElementsBinding(ReadOnlyDoubleProperty height, ReadOnlyDoubleProperty width, DoubleProperty magnification){
        this.height = height;
        this.width = width;
        this.magnification = magnification;
    }
    @Override
    protected double computeValue() {
        double v;
        if (magnification.get() == 1) {
            savedWidth = width.get();
            savedHeight = height.get();
            v = min(height.get(), width.get())/2;
        } else {
            v = min(savedHeight, savedWidth)/2 * magnification.get();
        }
        
        super.bind(new SimpleDoubleProperty(v));
        return v;
    }
    
}

class CartesianXBinding extends DoubleBinding {
    private double angle, gapFromArc;
    private DoubleProperty radiusProperty, traslationProperty, circleThicknessProperty;
    
    CartesianXBinding(DoubleProperty radiusProperty, DoubleProperty traslationProperty,
            double angle, DoubleProperty circleThickness, double gapFromArc){
        this.radiusProperty = radiusProperty;
        this.traslationProperty = traslationProperty;
        this.angle = angle;
        this.circleThicknessProperty = circleThickness;
        this.gapFromArc = gapFromArc;
        
        super.bind(radiusProperty);
    }

    @Override
    protected double computeValue() {
        double plotRadius = radiusProperty.get();
        double circleThickness = circleThicknessProperty.get();
        // transform polar to cartesian coordinates
        double x = cos(angle) * (plotRadius - circleThickness - gapFromArc);
        
        // apply center center
        x = x + traslationProperty.get();
        
        return x;
    }
    
}

class CartesianYBinding extends DoubleBinding {
    private double angle, gapFromArc;
    private DoubleProperty radiusProperty, traslationProperty, circleThicknessProperty;
    
    CartesianYBinding(DoubleProperty radiusProperty, DoubleProperty traslationProperty,
            double angle, DoubleProperty circleThickness, double gapFromArc){
        
        this.radiusProperty = radiusProperty;
        this.traslationProperty = traslationProperty;
        this.angle = angle;
        this.circleThicknessProperty = circleThickness;
        this.gapFromArc = gapFromArc;
        
        super.bind(radiusProperty);
    }

    @Override
    protected double computeValue() {
        double plotRadius = radiusProperty.get();
        double circleThickness = circleThicknessProperty.get();
        // transform polar to cartesian coordinates
        double y = sin(angle) * (plotRadius - circleThickness - gapFromArc);
        
        // apply center center
        // minus on y coordinates is needed since origin in javafx is on the upper left corner
        // so we need also to traslate from first to fourth quadrant.
        y = -y + traslationProperty.get();
        
        return y;
    }
    
}


