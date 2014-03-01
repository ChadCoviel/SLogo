package nodes.commandnodes;

import nodes.AbstractNode;
import turtle.Turtle;

public class LeftNode extends AbstractNode {

    private Turtle myTurtle;

    public LeftNode(Turtle turtle) {
        super(turtle);
        myTurtle = turtle;
    }

    public void setTurtle(Turtle turtle) {
        myTurtle = turtle;
    }

    @Override
    public double evaluate() {
        double deltaAngle = this.getLeftNode().evaluate();
        myTurtle.updateAngle(deltaAngle);
        return deltaAngle;
    }

    @Override
    public boolean allowsTwoChildren() {
        return false;
    }

    @Override
    public boolean allowsMoreThanTwoChildren() {
        return false;
    }

}