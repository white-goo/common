package RBTree;


public class RBTree<k extends Comparable<k>, v> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public v get(k key){
        return getByKey(key, this.root);
    }

    private v getByKey(k key,TreeNode node){

        if(node == null){
            return null;
        }

        if(key.compareTo((k) node.getKey()) > 0){
            return getByKey(key,node.right);
        }else if(key.compareTo((k) node.getKey()) < 0){
            return getByKey(key,node.left);
        }else {
            return (v) node.getValue();
        }
    }

    /**
     * 获取节点的父节点
     *
     * @param node
     * @return
     */
    private TreeNode parentOf(TreeNode node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }


    /**
     * 判断节点是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isRed(TreeNode node) {
        if (node != null) {
            return node.color == RED;
        }
        return false;
    }

    /**
     * 判断节点是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(TreeNode node) {
        if (node != null) {
            return node.color == BLACK;
        }
        return false;
    }

    /**
     * 设置节点为红色
     *
     * @param node
     */
    private void setRed(TreeNode node) {
        if (node != null) {
            node.color = RED;
        }
    }

    /**
     * 设置节点为黑色
     *
     * @param node
     */
    private void setBlack(TreeNode node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    /**
     * 打印中序便利
     */
    public void inOrderPrint() {
        inOrderPrint(root);
    }

    private void inOrderPrint(TreeNode node) {
        if (node.left != null) inOrderPrint(node.left);
        System.out.println("key: " + node.key + " value: " + node.value);
        if (node.right != null) inOrderPrint(node.right);
    }

    /**
     * 左旋
     *
     * @param x
     */
    private void leftRotate(TreeNode x) {
        TreeNode y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        if (x.parent != null) {
            y.parent = x.parent;
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        } else {
            //x为根节点
            this.root = y;
            y.parent = null;
        }
        x.parent = y;
        y.left = x;

    }

    /**
     * 右旋
     *
     * @param x
     */
    private void rightRotate(TreeNode x) {
        TreeNode y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        if (x.parent != null) {
            y.parent = x.parent;
            if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        } else {
            this.root = y;
            y.parent = null;
        }
        x.parent = y;
        y.right = x;

    }

    public void insert(k key, v value) {

        TreeNode treeNode = new TreeNode();
        treeNode.setKey(key);
        treeNode.setValue(value);
        //新节点一定是红色
        treeNode.setColor(RED);
        insert(treeNode);
    }

    private void insert(TreeNode node) {
        //查找当前node的父节点
        TreeNode parent = null;
        TreeNode x = this.root;

        while (x != null) {
            parent = x;
            //cmp > 0,表示node.key 大于x.key 需要到x的右子树查找
            //cmp == 0 ,表示相同，替换值
            int cmp = node.key.compareTo(x.key);
            if (cmp > 0) {
                x = x.right;
            } else if (cmp < 0) {
                x = x.left;
            } else {
                x.setValue(node.value);
                return;
            }

        }
        node.parent = parent;

        if (parent != null) {
            if (node.key.compareTo(parent.key) > 0) {
                parent.right = node;
            } else {
                parent.left = node;
            }
        } else {
            this.root = node;
        }

        //需要修复红黑树
        insertFixup(node);
    }

    /**
     * 修复红黑树
     *
     * @param node
     */
    private void insertFixup(TreeNode node) {

        this.root.setColor(BLACK);

        TreeNode parent = parentOf(node);
        TreeNode gParent = parentOf(parent);

        if (parent != null && isRed(parent)) {

            TreeNode uncle = null;

            if (parent == gParent.left) {

                uncle = gParent.right;

                if (uncle != null && isRed(uncle)) {

                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gParent);
                    insertFixup(gParent);
                    return;
                }else {

                    if(node == parent.left){
                        setBlack(parent);
                        setRed(gParent);
                        rightRotate(gParent);
                        return;
                    }else {

                        leftRotate(parent);
                        insertFixup(parent);
                        return;

                    }

                }

            } else {
                uncle = gParent.left;

                if (uncle != null && isRed(uncle)) {

                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gParent);
                    insertFixup(gParent);
                    return;
                }

                if(uncle == null || isBlack(uncle)){

                    if(node == parent.right){
                        setBlack(parent);
                        setRed(gParent);
                        leftRotate(gParent);
                        return;
                    }else {
                        rightRotate(parent);
                        insertFixup(parent);
                        return;
                    }

                }

            }
        }

    }

    static class TreeNode<k extends Comparable<k>, v> {
        private TreeNode parent;
        private TreeNode left;
        private TreeNode right;
        private boolean color;
        private k key;
        private v value;

        public TreeNode() {
        }

        public TreeNode(TreeNode parent, TreeNode left, TreeNode right, boolean color, k key, v value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public k getKey() {
            return key;
        }

        public void setKey(k key) {
            this.key = key;
        }

        public v getValue() {
            return value;
        }

        public void setValue(v value) {
            this.value = value;
        }
    }


}
