package nodes.controlnodes;

import turtle.Turtle;
import nodes.AbstractNode;

public class IfNode extends AbstractNode {

    private Turtle myTurtle; 
    
    public IfNode (Turtle turtle) {
        super(turtle);
        myTurtle = turtle;
    }

    private double traverseSubtree() {
        // check left (condition node)
        AbstractNode conditionNode = this.getLeftNode();
        double count = conditionNode.evaluate();
        if (count == 1) { // condition is true
            this.getRightNode().evaluate();
        }
        return 0;
    }

    @Override
    public double evaluate () {
        return traverseSubtree();
    }

    @Override
    public boolean allowsTwoChildren () {
        return true;
    }

    @Override
    public boolean allowsMoreThanTwoChildren () {
        return false;
    }

}