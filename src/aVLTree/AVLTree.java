package aVLTree;

import java.util.LinkedList;

public class AVLTree<Value>
{
    int size;

    AVLTreeNode<Value> root;

    // TODO : ? move to node class or create iterator class
    public AVLTreeNode<Value> pointer;

    /**
     * {@inheritDoc}
     * @see Dictionary#add(String,Value)
     */
    public void add(String key, Value value)
    {
        if (root == null)
            root = new AVLTreeNode<Value>(key, value);
        else 
        {
            AVLTreeNode<Value> current = root;
            while (true)
            {
                // allow for duplicate entries
                // TODO : Test how this effects the speed of adding.
                int difference = key.compareTo(current.key);
                if (difference == 0)
                {
                    current.values.add(value);
                    break;
                }
                else if (difference < 0)
                {
                    if (current.left == null)
                    {
                        current.left = new AVLTreeNode<Value>(key, value);
                        current.left.parent = current;
                        fixHeight(current);
                        balance(current);
                        break;
                    }
                    else
                        current = current.left;
                }
                else // difference > 0
                {
                    if (current.right == null)
                    {
                        current.right = new AVLTreeNode<Value>(key, value);
                        current.right.parent = current;
                        fixHeight(current);
                        balance(current);
                        break;
                    }
                    else
                        current = current.right;
                }
            }
        }

        size++;
    }

    protected void balance(AVLTreeNode<Value> node)
    {
        while (node != null)
        {
            // check if right rotate is needed
            if (getHeight(node.left) - getHeight(node.right) > 1)
            {
                // is left child is right heavy, rotate it left first (double right)
                if (getHeight(node.left.right) > getHeight(node.left.left))
                    rotateLeft(node.left);

                rotateRight(node);
            }
            // left rotate
            else if (getHeight(node.right) - getHeight(node.left) > 1)
            {
                // double left
                if (getHeight(node.right.left) > getHeight(node.right.right))
                    rotateRight(node.right);

                rotateLeft(node);
            }

            node = node.parent;
        }
    }

    /**
     * {@inheritDoc}
     * @see Dictionary#remove(String)
     */
    public LinkedList<Value> remove(String key)
    {
        AVLTreeNode<Value> node = removeNode(key);

        if (node == null)
            return null;

        return node.values;
    }

    /**
     * {@inheritDoc}
     * @see Dictionary#remove(String)
     */
    protected AVLTreeNode<Value> removeNode(String key)
    {
        //System.out.println("removeNode():");

        AVLTreeNode<Value> node = (AVLTreeNode<Value>)findNode(key);
        if (node != null)
        {
            //   O
            //    \
            if (node.right == null)
            {
                // assuming balanced tree
                if (node == root)
                {
                    root = node.left;
                    if (node.left != null)
                        node.left.parent = root;
                }
                else
                {
                    if (node.parent.right == node)
                        node.parent.right = node.left;
                    else
                        node.parent.left = node.left;

                    if (node.left != null)
                        node.left.parent = node.parent;

                    fixHeight(node.parent);
                    balance(node.parent);
                }
            }

            //   O
            //  / \
            //     o
            else if (node.left == null)
            {
                // assuming balanced tree
                if (node == root)
                {
                    root = node.right;
                    root.parent = null;
                }
                else
                {
                    node.right.parent = node.parent;
                    if (node.parent.right == node)
                        node.parent.right = node.right;
                    else
                        node.parent.left = node.right;

                    fixHeight(node.parent);
                    balance(node.parent);
                }
            }

            //   O
            //  / \
            // o   o
            else
            {
                //   O
                //  / \
                // o   o
                //  \
                if (node.left.right == null)
                {
                    if (node == root)
                        root = node.left;
                    else if (node.parent.right == node)
                        node.parent.right = node.left;
                    else
                        node.parent.left = node.left;
                    node.left.right = node.right;
                    node.left.parent = node.parent;
                    node.right.parent = node.left;
                    fixHeight(node.left);
                    balance(node.left);
                }
                //   O
                //  / \
                // o   o
                //  \
                //   o
                else
                {
                    AVLTreeNode<Value> newRoot = getRightMost(node.left);
                    AVLTreeNode<Value> toBalance = newRoot.parent;
                    if (newRoot.left != null)
                        newRoot.left.parent = newRoot.parent;
                    newRoot.parent.right = newRoot.left;

                    newRoot.left = node.left;
                    newRoot.left.parent = newRoot;
                    newRoot.right = node.right;
                    newRoot.right.parent = newRoot;
                    if (node == root)
                        root = newRoot;
                    else if (node.parent.right == node)
                        node.parent.right = newRoot;
                    else
                        node.parent.left = newRoot;
                    newRoot.parent = node.parent;

                    fixHeight(toBalance);
                    balance(toBalance);
                }
            }
        }

        size--;
        return node;
    }

    protected AVLTreeNode<Value> getRightMost(AVLTreeNode<Value> node)
    {
        AVLTreeNode<Value> rightMost = node;
        while (rightMost.right != null)
            rightMost = rightMost.right;

        return rightMost;
    }

    protected int getHeight(AVLTreeNode<Value> node)
    {
        return node == null ? 0 : node.height;
    }

    protected void fixHeight(AVLTreeNode<Value> node)
    {
        while (node != null)
        {
            int max = Math.max(getHeight(node.left), getHeight(node.right));
            node.height = max + 1;
            node = node.parent;
        }
    }

    protected AVLTreeNode<Value> findNode(String key)
    {
        AVLTreeNode<Value> node = (AVLTreeNode<Value>)root;
        while (node != null)
        {
            if (key.compareTo(node.key) < 0)
                node = node.left;
            else if (key.compareTo(node.key) > 0)
                node = node.right;
            else
                return node;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @see Dictionary#find(String)
     */
    public LinkedList<Value> find(String key)
    {
        AVLTreeNode<Value> node = (AVLTreeNode<Value>)findNode(key);
        if (node == null)
            return new LinkedList<Value>();
        else
            return node.values;
        // TODO : ? Return array rather than list. It's a shame you can't
        // create generic arrays in Java.
    }

    /**
     * {@inheritDoc}
     * @see Dictionary#size()
     */
    public int size()
    {
        return size;
    }

    protected AVLTreeNode<Value> getFirst()
    {
        //System.out.println("getFirst():");
        AVLTreeNode<Value> first = root;

        if (first == null)
        {
            //System.out.println("getFirst(): null");
            pointer = null;
            return null;
        }

        while (first.left != null)
            first = first.left;

        pointer = first;
        return first;
    }

    protected AVLTreeNode<Value> getNext()
    {
        pointer = getNext(pointer);
        return pointer;
    }

    /**
     * Get next item, in-order. Can return null if empty tree or lonely node.
     *
     * {@inheritDoc}
     * @see Dictionary#getNext()
     */
    protected AVLTreeNode<Value> getNext(AVLTreeNode<Value> currentNode)
    {
        return getNext(root, currentNode);
    }

    /**
     * Get next item, in-order. Can return null if empty tree or lonely node.
     *
     * {@inheritDoc}
     * @see Dictionary#getNext()
     */
    protected AVLTreeNode<Value> getNext(AVLTreeNode<Value> root, AVLTreeNode<Value> currentNode)
    {
        AVLTreeNode<Value> next;

        // no node
        if (currentNode == null)
        {
            //System.out.println("getNext(): Node is 'null', can't get next.");
            return null;
        }
        else
        {
            //System.out.println("getNext(): " + currentNode.toString());
        }

        // on right, get left most or self
        //    O
        //     \
        //      o
        //     /
        //    x
        if (currentNode.right != null)
        {
            //System.out.println("getNext(): >");
            next = currentNode.right;
            while (next.left != null)
            {
                //System.out.println("getNext():<");
                next = next.left;
            }
            return next;
        }

        // first right parent
        //    x
        //   /
        //  o
        //   \
        //    O
        next = currentNode;
        while (next != root)
        {
            if (next == next.parent.left)
            {
                //System.out.println("getNext():parent was next yay..");
                return next.parent;
            }
            next = next.parent;
        }

        // no next
        //    x
        return null;
    }

    protected void rotateRight(AVLTreeNode<Value> node)
    {
        // in case we do manual rotate right in GUI
        if (node.left == null)
            return;

        AVLTreeNode<Value> newRoot = node.left;
        newRoot.parent = node.parent;
        if (node == root)
            root = newRoot;
        else if (node.parent.left == node)
            node.parent.left = newRoot;
        else
            node.parent.right = node.left;

        node.left = newRoot.right;
        if (node.left != null)
            node.left.parent = node;
        newRoot.right = node;
        node.parent = newRoot;

        fixHeight(node);
    }

    protected void rotateLeft(AVLTreeNode<Value> node)
    {
        // in case we do manual rotate left in GUI
        if (node.right == null)
            return;

        AVLTreeNode<Value> newRoot = node.right;
        newRoot.parent = node.parent;

        if (node == root)
            root = newRoot;
        else if (node.parent.right == node)
            node.parent.right = newRoot;
        else
            node.parent.left = newRoot;

        node.right = newRoot.left;
        if (node.right != null)
            node.right.parent = node;
        newRoot.left = node;
        node.parent = newRoot;

        fixHeight(node);
    }
}
