package nodes.booleannodes;

import turtle.Turtle;
import nodes.AbstractNode;

public class GreaterNode extends AbstractNode {

    public GreaterNode (Turtle turtle) {
        super(turtle);
    }

    @Override
    public void action () {
        // do nothing
    }

    @Override
    /**
     * Return 1 if left node is greater than right node; return 0 if left node is less than right node
     */
    public double evaluate () {
        AbstractNode leftNode = this.getLeftNode();
        AbstractNode rightNode = this.getRightNode();
        if (leftNode.evaluate() > rightNode.evaluate()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean allowsTwoChildren () {
        return true;
    }

    @Override
    public boolean allowsThreeChildren () {
        return false;
    }

}
