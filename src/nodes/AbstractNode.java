package nodes;

import java.util.ArrayList;
import java.util.List;
import turtle.Turtle;

public abstract class AbstractNode {
    
    private Turtle myTurtle;
    
    private double myValue;
    private AbstractNode myParent;
    private List<AbstractNode> myChildren = new ArrayList<AbstractNode>();
    private AbstractNode myLeftNode;
    private AbstractNode myRightNode;
    
    public AbstractNode(Turtle turtle) {
        myTurtle = turtle;
    }
    
    public String toString() {
        return this.getClass().getName();
    }
 
    public AbstractNode getLeftNode() {
        return myLeftNode;
    }
    
    public AbstractNode getRightNode() {
        return myRightNode;
    }
    
    public AbstractNode getParent() {
        return myParent;
    }
    
    public void setParent(AbstractNode node) {
        myParent = node;
    }
    
    public void setLeftNode(AbstractNode node) {
        myChildren.add(node);
        node.setParent(this);
        myLeftNode = node;
    }
    
    public void setRightNode(AbstractNode node) {
        myChildren.add(node);
        node.setParent(this);
        myRightNode = node;
    }
    
    public void addChild(AbstractNode node) {
        myChildren.add(node);
    }
    
    public boolean hasChild() {
        return (myChildren.size()>0);
    }

    /**
     * perform calculation changes the position of the turtle for all sub nodes and return the final value
     * @return
     */
    public abstract double evaluate();

    public List<AbstractNode> getChildren () {
        return myChildren;
    }

    /**
     * Check if a node allows two child nodes
     * @return
     */
    public abstract boolean allowsTwoChildren ();
    
    /**
     * check if a node allows more than two child nodes (such as BlockNode)
     * @return
     */
    public abstract boolean allowsMoreThanTwoChildren() ;
    
}
