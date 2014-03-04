package model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import turtle.Turtle;
import nodes.AbstractNode;
import nodes.BlockNode;
import nodes.NodeFactory;
import nodes.controlnodes.ConditionNode;
import nodes.controlnodes.IfElseNode;
import nodes.controlnodes.IfNode;
import nodes.controlnodes.RepeatNode;
import nodes.leafnodes.LeafNode;
import nodes.leafnodes.NumberNode;
import nodes.leafnodes.VariableNode;

public class Parser {

    private Turtle myTurtle;
    private boolean myValidBoolean = true;
    
    private String myLanguage;

    private List<VariableNode> myVariables = new ArrayList<VariableNode>();
    private List<Function> myFunctions = new ArrayList<Function>();
    
    private List<Turtle> myAllTurtles = new ArrayList<Turtle>();

    public Parser(Turtle turtle, List<Turtle> allTurtles) {
        myTurtle = turtle;
        myLanguage = turtle.getLangauge();
        myAllTurtles = allTurtles;
    }

    public List<Function> getFunctions () {
        return myFunctions;
    }

    public List<VariableNode> getVariables() {
        return myVariables; // TODO put in model: myFunc, myVar, myTurtle, myPencolor, ...
    }

    public boolean isValid() {
        return myValidBoolean;
    }

    public void createFunctionsAndVariables(String s) {
        String[] words = s.split(" ");
        // if does not have the word "to" (only has one function)
        boolean hasTo = false;
        for (String word : words){
            if (word.equals("to")) {
                hasTo = true;
            }
        }
        if (hasTo) {
            String[] functionString = s.split("to");

            for (String thisFunction : functionString) {
                if (!thisFunction.equals("")) {

                    String[] wordsInFunction = thisFunction.split(" ");
                    int functionNameIndex = 0;
                    int functionContentStartIndex = 0;
                    while (wordsInFunction[functionNameIndex].equals("") ||
                            wordsInFunction[functionNameIndex].equals(" ")) {
                        functionNameIndex ++;
                        functionContentStartIndex += wordsInFunction[functionNameIndex].length() + 1;
                    }
                    String functionName = wordsInFunction[functionNameIndex];
                    String content = thisFunction.substring(functionContentStartIndex);
                    myFunctions.add(new Function(functionName, content));
                }
            }
        } else { //hasTo = false
            myFunctions.add(new Function("NoName", s));
        }

        for (int i=0;i<words.length;i++) {
            if (words[i].charAt(0) == ':') {
                //create a variable node
                VariableNode vn = new VariableNode(myTurtle, Double.parseDouble(words[i+1]));
            }
        }

    }

    public AbstractNode createTree(Function function, Turtle turtle) throws ClassNotFoundException, 
    NoSuchMethodException, SecurityException, InstantiationException, 
    IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoSuchFieldException {
        myTurtle = turtle;
        NodeFactory nodeFactory = new NodeFactory(myTurtle, myLanguage);
        AbstractNode root = new BlockNode(myTurtle);
        processFunction(function);

        String[] words = function.getContent().split(" ");
        Queue<String> queue = new LinkedList<String>();
        for (String word : words) {
            queue.add(word);
        }
        String currentWord = queue.poll();
        AbstractNode currentNode = nodeFactory.createNode(currentWord);
        root.setLeftNode(currentNode);

        while (!queue.isEmpty()) {
        	/**
        	 * Benson to Tara (3/1/14): Made change here to leafnode instead of number || variable 
        	 */
            if (currentNode instanceof LeafNode) {
                // return to parent
                currentNode = currentNode.getParent();
                if (!currentNode.allowsTwoChildren() || 
                        (currentNode.allowsTwoChildren() && currentNode.getChildren().size()==2)) {
                    currentNode = currentNode.getParent();
                }
            }

            if (currentNode instanceof IfElseNode) {
                // create two block nodes
                AbstractNode bnLeft = new BlockNode(myTurtle);
                AbstractNode bnRight = new BlockNode(myTurtle);
                currentNode.setLeftNode(bnLeft);
                currentNode.setRightNode(bnRight);
                // create a condition node for left block
                AbstractNode conditionLeft = new ConditionNode(myTurtle);
                bnLeft.setLeftNode(conditionLeft);
                bnLeft.setRightNode(new BlockNode(myTurtle));

                // create condition for condition left; condition right is the opposite of condition left
                currentNode = bnLeft.getLeftNode();
            }
            if ((currentNode instanceof IfNode || currentNode instanceof RepeatNode) && currentNode.getLeftNode() == null) {
                // create 1 block node
                AbstractNode bn = new BlockNode(myTurtle);
                // create 1 condition node
                AbstractNode conditionNode = new ConditionNode(myTurtle);
                currentNode.setLeftNode(conditionNode);
                currentNode.setRightNode(bn);
                currentNode = conditionNode;
            }

            String nextWord = queue.poll();
            if (nextWord == null) {
                return root;
            }
            for (int i=0;i<2;i++) {
                if (nextWord.equals("[")) { //TODO counter for brackets
                    // if the parent node is a repeat node or an if node, or the parent of the parent node is an if else node
                    if (currentNode.getParent() instanceof RepeatNode || currentNode.getParent() instanceof IfNode ||
                            currentNode.getParent().getParent() instanceof IfElseNode) {
                        currentNode = currentNode.getParent().getRightNode(); // go to block
                    } else if (currentNode instanceof RepeatNode || currentNode instanceof IfNode) {
                        currentNode = currentNode.getRightNode(); // go to block
                    }
                    nextWord = queue.poll();
                }

                if (nextWord.equals("]")) {    
                    if (currentNode == null) {
                        return root;
                    }
                    currentNode = currentNode.getParent();
                    if (currentNode.getParent() instanceof IfElseNode) {
                        currentNode = currentNode.getParent();
                    }
                    nextWord = queue.poll();
                    if (nextWord == null) {
                        return root;
                    }
                }
            }
            AbstractNode nextNode = nodeFactory.createNode(nextWord);

            if (currentNode.getLeftNode()== null) {
                currentNode.setLeftNode(nextNode);
            } else if (currentNode.getRightNode() == null && currentNode.allowsTwoChildren()) {
                currentNode.setRightNode(nextNode);
            } else if (currentNode.getRightNode() != null && currentNode.allowsMoreThanTwoChildren()){
                currentNode.addChild(nextNode);
            }

            currentNode = nextNode;
            currentWord = nextWord;

        }
        return root;

    }

    private void processFunction (Function function) {
        // getting rid of initial spaces and outside brackets
        int beginIndex = 0;
        while (function.getContent().charAt(beginIndex)==' ' || function.getContent().charAt(beginIndex)=='[') {
            beginIndex ++;
        }
        int endIndex = function.getContent().length() - 1;
        while(function.getContent().charAt(endIndex) == ' ' ||  function.getContent().charAt(endIndex)=='[') {
            endIndex --;
        }
        function.setContent(function.getContent().substring(beginIndex, endIndex + 1));
    }

    public void traverseTree(Turtle turtle, AbstractNode root) {  
        myTurtle = turtle;
        if (root!=null) {
            for (AbstractNode childNode : root.getChildren()) {
                traverseTree(myTurtle, childNode);
            }
            root.evaluate();
        }
    }
}