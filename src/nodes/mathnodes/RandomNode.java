package nodes.mathnodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nodes.AbstractNode;
import turtle.Turtle;

public class RandomNode extends AbstractNode {

	private List<Turtle> myTurtles;

	public RandomNode(List<Turtle> turtles) {
		super(turtles);
		myTurtles = turtles;
	}

	@Override
	public double evaluate() throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, IOException {

		AbstractNode child = this.getLeftNode();
		double max = child.evaluate();

		MathResults.addToMathResultsList(String.valueOf(Math.random() * max));
		return Math.random() * max;

	}

	/**
	 * Both are false because this node only has one Child
	 */
	@Override
	public boolean allowsTwoChildren() {
		return false;
	}

	@Override
	public boolean allowsMoreThanTwoChildren() {
		return false;
	}

}
