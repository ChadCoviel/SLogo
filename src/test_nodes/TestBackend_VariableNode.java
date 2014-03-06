package test_nodes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import model.Parser;
import nodes.AbstractNode;
import nodes.BlockNode;
import nodes.FunctionNode;
import nodes.MakeNode;
import nodes.VariableNode;
import nodes.commandnodes.ForwardNode;
import nodes.controlnodes.ConditionNode;
import nodes.controlnodes.DoTimesNode;
import nodes.controlnodes.RepeatNode;
import nodes.leafnodes.NumberNode;
import turtle.Turtle;

public class TestBackend_VariableNode {
    

    @org.junit.Test
    public void testForCreateTree_Make() throws ClassNotFoundException,
    NoSuchMethodException, SecurityException, InstantiationException,
    IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, IOException, NoSuchFieldException {
        String string = "make :distance 100 repeat 2 [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, "English");
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        assert root instanceof BlockNode;
        AbstractNode leftNode = root.getLeftNode();
        AbstractNode rightNode = root.getRightNode();
        assert leftNode instanceof MakeNode;
        assert leftNode.getLeftNode() instanceof VariableNode; 
        assert rightNode instanceof RepeatNode;
        assert rightNode.getLeftNode() instanceof ConditionNode;
    }

    @org.junit.Test
    public void testForCreateTree_To() throws ClassNotFoundException,
    NoSuchMethodException, SecurityException, InstantiationException,
    IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, IOException, NoSuchFieldException {
        String string = "to function [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, "English");
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        assert root instanceof BlockNode;
        assert root.getLeftNode() instanceof FunctionNode;
        assert root.getLeftNode().getLeftNode() instanceof BlockNode;
        
        
    }

    @org.junit.Test
    public void testForCreateTree_MakeTo() throws ClassNotFoundException,
    NoSuchMethodException, SecurityException, InstantiationException,
    IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, IOException, NoSuchFieldException {
        String string = "make :distance 100 to function [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, "English");
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        assert root instanceof BlockNode;
        AbstractNode leftNode = root.getLeftNode();
        AbstractNode rightNode = root.getRightNode();
        assert leftNode instanceof MakeNode;
        assert leftNode.getLeftNode() instanceof VariableNode; 
        assert leftNode.getLeftNode().getLeftNode() instanceof NumberNode;
        assert rightNode instanceof FunctionNode;
        assert rightNode.getLeftNode() instanceof BlockNode;
        assert rightNode.getLeftNode().getLeftNode() instanceof ForwardNode;
        assert rightNode.getLeftNode().getLeftNode().getLeftNode() instanceof NumberNode;
    }

    @org.junit.Test
    public void testForCreateTree_To2() throws ClassNotFoundException,
    NoSuchMethodException, SecurityException, InstantiationException,
    IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, IOException, NoSuchFieldException {
        String string = "make :distance 100 to function [ :distance ] [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, "English");
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        assert root instanceof BlockNode;
        AbstractNode leftNode = root.getLeftNode();
        AbstractNode rightNode = root.getRightNode();
        assert leftNode instanceof MakeNode;
        assert leftNode.getLeftNode() instanceof VariableNode; 
        assert leftNode.getLeftNode().getLeftNode() instanceof NumberNode;
        assert rightNode instanceof FunctionNode;
        assert rightNode.getLeftNode() instanceof BlockNode;
        assert rightNode.getRightNode() instanceof BlockNode;
        assert rightNode.getLeftNode().getLeftNode() instanceof VariableNode;
        assert rightNode.getRightNode().getLeftNode() instanceof ForwardNode; 
        assert rightNode.getRightNode().getLeftNode().getLeftNode() instanceof NumberNode;
    }
    
    @org.junit.Test
    public void testForCreateTree_DoTimes() throws ClassNotFoundException,
    NoSuchMethodException, SecurityException, InstantiationException,
    IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, IOException, NoSuchFieldException {
        String string = "dotimes [ :distance 3 ] [ fd :distance ]";
        Turtle turtle = new Turtle(0, 0, "English");
        List<Turtle> allTurtles = new ArrayList<Turtle>();
        allTurtles.add(turtle);

        Parser parser = new Parser(allTurtles, string, "English");
        AbstractNode root = parser.createTree();
        assert root instanceof BlockNode;
        assert root.getLeftNode() instanceof DoTimesNode;
        assert root.getLeftNode().getLeftNode() instanceof VariableNode;
        assert root.getLeftNode().getLeftNode().getLeftNode() instanceof NumberNode;
        assert root.getLeftNode().getRightNode() instanceof BlockNode;
        assert root.getLeftNode().getRightNode().getLeftNode() instanceof ForwardNode;
        assert root.getLeftNode().getRightNode().getLeftNode().getLeftNode() instanceof NumberNode;
        
    }
    
    @org.junit.Test
    public void testForModel_VariableNode() throws ClassNotFoundException,
                    NoSuchMethodException, SecurityException, InstantiationException,
                    IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException, IOException, NoSuchFieldException {
        
    }
    
    
    
}
