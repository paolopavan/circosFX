package tries;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

public class MyTransition extends Transition {
    
    {
        setCycleDuration(Duration.millis(100000));
    }
    
    private double start = 0;
    private double delta = .1;
    private Node cachedNode;

    @Override
    protected void interpolate(double frac) {
        start += delta;
        cachedNode.setRotate(start);
        
    }

    public void setNode(Node n){
        cachedNode = n;
    }
}