package com.datastruct;

import sun.reflect.generics.tree.Tree;

public class BinaryTree {

    //http://blog.csdn.net/wuwenxiang91322/article/details/12231657

    private TreeNode root;

    public BinaryTree() {
        root = new TreeNode(1, "rootNode(a)");
    }

    /**
     * 创建一棵二叉树
     * <pre>
     *           A
     *     B          C
     *  D     E            F
     *  </pre>
     *
     * @param root
     * @author WWX
     */
    public void createBinTree(TreeNode root) {
        TreeNode newNodeB = new TreeNode(2, "B");
        TreeNode newNodeC = new TreeNode(3, "C");
        TreeNode newNodeD = new TreeNode(4, "D");
        TreeNode newNodeE = new TreeNode(5, "E");
        TreeNode newNodeF = new TreeNode(6, "F");
        root.leftChild = newNodeB;
        root.rightChild = newNodeC;
        root.leftChild.leftChild = newNodeD;
        root.leftChild.rightChild = newNodeE;
        root.rightChild.leftChild = newNodeF;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int height() {
        return height(root);
    }

    public int size() {
        return size(root);
    }

    public TreeNode parent(TreeNode target) {
        if (root == null || root == target) {
            return null;
        }
        return parent(root, target);
    }


    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getLeftChild(TreeNode treeNode) {
        if (treeNode != null) {
            return treeNode.leftChild;
        }
        return null;
    }

    public TreeNode getRightChild(TreeNode treeNode) {
        if (treeNode != null) {
            return treeNode.rightChild;
        }
        return null;
    }


    public void destory(TreeNode treeNode) {
        if (treeNode != null) {
            destory(treeNode.leftChild);
            destory(treeNode.rightChild);
            treeNode = null;
        }
    }

    private int height(TreeNode treeNode) {
        if (treeNode != null) {
            int i = height(treeNode.leftChild);
            int j = height(treeNode.rightChild);
            return (i < j) ? (j + 1) : (i + 1);
        }
        return 0;

    }

    private int size(TreeNode treeNode) {
        if (treeNode != null) {
            return 1 + size(treeNode.rightChild) + size(treeNode.leftChild);
        }
        return 0;
    }


    private TreeNode parent(TreeNode treeNode, TreeNode target) {
        if (treeNode == null) {
            return null;
        }
        if (treeNode.leftChild == target || treeNode.rightChild == target) {
            return treeNode;
        }
        TreeNode resultLeft = parent(treeNode.leftChild, target);
        if (resultLeft != null) {
            return resultLeft;
        } else {
            return parent(treeNode.rightChild, target);
        }
    }

    private class TreeNode {
        private int key = 0;
        private String data = null;
        private boolean isVisted = false;
        private TreeNode leftChild = null;
        private TreeNode rightChild = null;

        public TreeNode() {
        }

        public TreeNode(int key, String data) {
            this.key = key;
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }

        public TreeNode(int key, String data, boolean isVisted, TreeNode leftChild, TreeNode rightChild) {
            this.key = key;
            this.data = data;
            this.isVisted = isVisted;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
}
