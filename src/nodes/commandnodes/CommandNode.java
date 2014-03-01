package nodes.commandnodes;

import nodes.AbstractNode;
import nodes.Token;
import turtle.Turtle;

public class CommandNode extends AbstractNode implements Token {

    public CommandNode (Turtle turtle, String token, double value) {
        super(turtle, token, value);
        
    }

    @Override
    public void action () {
        //implemented in sub classes
    }

    @Override
    public double evaluate () {
        // do nothing
        return 1;
    }

}