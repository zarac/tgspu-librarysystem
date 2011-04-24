package aVLTree;

import java.util.LinkedList;

public class AVLTreeNode<Value>
{
    public String key;
    public LinkedList<Value> values;
    public int height = 1;
    public AVLTreeNode<Value> parent;
    public AVLTreeNode<Value> left;
    public AVLTreeNode<Value> right;

    public AVLTreeNode(String key, Value value)
    {
        this.key = key;
        values = new LinkedList<Value>();
        values.add(value);
    }

    public String toString()
    {
        String parent;
        String self;
        String left;
        String right;

        if (this.parent == null)
            parent = "null";
        else
            parent = this.parent.key;

        self = key;

        if (this.left == null)
            left = "null";
        else
            left = this.left.key;

        if (this.right == null)
            right = "null";
        else
            right = this.right.key;
                

        return "SELF='" + self + "', <<<'" + left + "<<< >>>" + right + ">>> , ^^^" + parent + "^^^, height='" + height + "'";
        //return "parent='" + parent + "', self='" + self + "', left='" + left + "', right='" + right + "', height='" + height + "'";
    }
}

