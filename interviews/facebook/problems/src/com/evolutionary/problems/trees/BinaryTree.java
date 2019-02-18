package com.evolutionary.problems.trees;

import sun.reflect.generics.tree.Tree;

import java.util.*;

public class BinaryTree {

    TreeNode root;
    TreeNode newRoot;

    BinaryTree() {
        root = null;
    }

    BinaryTree(int data) {
        root = new TreeNode(data);
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getNewRoot() {
        return newRoot;
    }

    public void setNewRoot(TreeNode newRoot) {
        this.newRoot = newRoot;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void insertRecData(int data) {

        TreeNode n = this.getRoot();
        if (n == null) {
            this.setRoot(new TreeNode(data));
            return;
        }

        insertRecTree(n, data);
        return;

    }

    public TreeNode insertRecTree(TreeNode ins, int data) {

        if (ins == null) {
            ins = new TreeNode(data);
            return ins;
        }

        if (ins.getData() < data) {
            ins.setRight(insertRecTree(ins.getRight(), data));
        } else {
            ins.setLeft(insertRecTree(ins.getLeft(), data));
        }
        return ins;

    }

    public List<Integer> preOrder(TreeNode root) {

        List<Integer> res = new ArrayList<Integer>();
        TreeNode inorder = getRoot();

        if (inorder == null) {
            return null;
        }

        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(inorder);
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            res.add(n.getData());
            if (n.getRight() != null) {
                stack.push(n.getRight());
            }
            if (n.getLeft() != null) {
                stack.push(n.getLeft());
            }
        }
        return res;
    }

    void insertData(int data) {

        if (root == null) {
            this.setRoot(new TreeNode(data));
            return;
        }
        TreeNode n = this.getRoot();

        boolean notInserted = true;

        while (notInserted) {
            if (n == null) {
                n = new TreeNode(data);
                notInserted = false;
                break;
            }
            if (n.getData() < data) {
                if (n.getRight() == null) {
                    n.setRight(new TreeNode(data));
                    break;
                } else {
                    n = n.getRight();
                }
            } else {
                if (n.getLeft() == null) {
                    n.setLeft(new TreeNode(data));
                    break;
                } else {
                    n = n.getLeft();
                }
            }
        }

    }

    public List<Integer> postOrder2 (TreeNode root) {

        TreeNode n = root ;
        if (n == null) return null ;
        List<Integer> postList = new ArrayList<Integer>() ;
        Stack <TreeNode> stack = new Stack<TreeNode>() ;

        stack.push(n) ;
        TreeNode prev = null ;
        while (!stack.isEmpty()) {

            TreeNode post = stack.peek() ;
            if (prev == null ||prev.getLeft() == post || prev.getRight() == post) {

                if (post.getLeft()!= null) {
                    stack.push (post.getLeft()) ;
                }
                else if (post.getRight() != null) {
                    stack.push (post.getRight()) ;
                }
                else {
                    TreeNode res = stack.pop() ;
                    postList.add(res.getData()) ;
                }
            }
            else if (post.getLeft() == prev) {
              if (post.getRight() != null) {
                  stack.push(post.getRight()) ;
              }
              else {
                  TreeNode res = stack.pop() ;
                  postList.add(res.getData()) ;
              }
            }
            else if (post.getRight() == prev) {
                TreeNode res = stack.pop();
                postList.add(res.getData()) ;
            }
            prev = post ;

        }


        return postList ;


    }


    void printTree(TreeNode root) {
        TreeNode n = root;

        Queue<TreeNode> walks = new LinkedList<TreeNode>();
        int count = 0;
        int newcount = 0;
        walks.add(n);
        ++count;
        while (count > 0 || newcount > 0) {

            if (count > 0) {
                TreeNode prTreeNode = walks.remove();
                System.out.print(prTreeNode.getData() + " ");
                --count;
                if (prTreeNode.getLeft() != null) {
                    walks.add(prTreeNode.getLeft());
                    ++newcount;
                }
                if (prTreeNode.getRight() != null) {
                    walks.add(prTreeNode.getRight());
                    ++newcount;
                }
            } else {
                System.out.println();
                count = newcount;
                newcount = 0;
            }
        }

    }

    public List<Integer> inOrder(TreeNode root) {

        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> list = new ArrayList<Integer>();
        TreeNode n = root;
        if (n == null) return null;

        while (!stack.isEmpty() || n != null) {

            if (n != null) {
                stack.push(n);
                n = n.getLeft();
            } else {
                TreeNode r = stack.pop();
                list.add(r.getData());
                n = r.getRight();
            }
        }
        return list;
    }

    public List<Integer> postOrder(TreeNode root) {

        Stack<TreeNode> stack = new Stack<TreeNode>();
        List<Integer> list = new ArrayList<Integer>();
        TreeNode n = root ;
        if (n == null) return null;
        stack.push(n);
        while (!stack.isEmpty()) {
            TreeNode p = stack.peek();
            if (p.getLeft() == null && p.getRight() == null) {
                TreeNode r = stack.pop();
                ;
                list.add(r.getData());
            } else {
                if (p.getRight() != null) {
                    stack.push(p.getRight());
                    p.setRight(null);
                }
                if (p.getLeft() != null) {
                    stack.push(p.getLeft());
                    p.setLeft(null);
                }

            }
        }
        return list;
    }

    public void invertTree(TreeNode root) {

        TreeNode n = root;
        if (n == null) return;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();

        queue.add(n);

        while (!queue.isEmpty()) {
            TreeNode r = queue.poll();
            if (r.getLeft() != null) {
                queue.add(r.getLeft());
            }
            if (r.getRight() != null) {
                queue.add(r.getRight());
            }
            TreeNode temp = r.getRight();
            r.setRight(r.getLeft());
            r.setLeft(temp);
        }
    }

    public void buildTreeInPost() {
        List<Integer> inList = this.inOrder(this.getRoot()) ;
        List<Integer> postList = this.postOrder2(this.getRoot()) ;
        Integer [] inArr = inList.toArray(new Integer[inList.size()]) ;
        Integer [] postArr = postList.toArray(new Integer[postList.size()]) ;

        this.setNewRoot(buildTreefromInPost(inArr, 0, inArr.length -1 ,
                    postArr, 0, postArr.length-1));


    }

    public boolean isBST() {

        int max = Integer.MAX_VALUE ;
        int min = Integer.MIN_VALUE ;
        TreeNode root = this.getRoot() ;

        if (root == null) return  true ;
        return isBSTTree (root, min, max) ;
    }

    public int maxDepth (TreeNode root) {

        if (root == null) return 0 ;

        int leftnum = maxDepth(root.getLeft()) ;
        int rightnum = maxDepth(root.getRight()) ;

        int bigger = Math.max(leftnum, rightnum) ;

        return (bigger + 1) ;

    }

    public boolean isBSTTree(TreeNode TreeNode, int min, int max) {

        if (TreeNode == null) return  true ;

        if (TreeNode.getData()  <= min || TreeNode.getData() >= max) {
            return false ;
        }

        return  isBSTTree(TreeNode.getRight(), TreeNode.getData(), max) &&
                isBSTTree(TreeNode.getLeft(), min, TreeNode.getData()) ;


    }

    public int findMaxUtil(TreeNode root, Res val) {

        if (root == null) {
            return 0 ;
        }
        int l = findMaxUtil(root.getLeft(), val) ;
        int r = findMaxUtil(root.getRight(), val) ;

        int max_single = Math.max(Math.max(l,r) + root.getData(),
                root.getData()) ;

        int max_top = Math.max(l+r+root.getData(), max_single) ;

        val.val = Math.max(val.val, max_top) ;

        return max_single ;
    }

    class Index {
        int point ;
    }

    public void reverseAlRoot (TreeNode root, int [] arr, Index ind, int level) {

        if (root == null) {
            return ;
        }

        reverseAlRoot(root.getLeft(), arr, ind, level+ 1) ;
        if (level %2 != 0 ) {
            arr[ind.point] = root.getData() ;
            ind.point = ind.point+1 ;
        }
        reverseAlRoot(root.getRight(), arr, ind, level + 1) ;
    }
    public void reverseAlternate (TreeNode root) {

        int [] arr = new int [100] ;
        Index  ind = new Index() ;

        reverseAlRoot (root, arr, ind, 0 ) ;
        reverse(arr, ind) ;
        // Update tree by taking elements from array
        ind.point = 0;
        modifyTree(root, arr, ind, 0);
    }

    public TreeNode removeLeaf (TreeNode root, int level,  int k) {
        if (root == null) {
            return null ;
        }

        root.setLeft(removeLeaf(root.getLeft(),
                level + 1, k+1)) ;
        root.setRight(removeLeaf(root.getRight(), level+1, k));

        if (root.getLeft() == null && root.getRight() == null & level < k) {
            return null;
        }
        return root ;
    }


    public void modifyTree (TreeNode root, int [] arr, Index ind, int level) {

        if (root == null) return ;

        modifyTree (root.getLeft(), arr, ind, level+1) ;
        if (level %2 == 0 ) {
            root.setData(arr[ind.point]);
            ind.point = ind.point + 1;
        }
        modifyTree (root.getRight(), arr, ind, level+2) ;
    }

    public void reverse (int [] arr, Index ind) {
        int l = 0, r = ind.point -1 ;
        while (l < r) {
            int  temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
    }

    public void swapLevel(TreeNode root1, TreeNode root2, int level) {

        if (root1 == null && root2 == null) {
            return;
        }
        if (level %2 == 0) {
                int temp = root2.getData() ;
                root2.setData(root1.getData());
                root1.setData(temp);
        }
        swapLevel(root1.getLeft(), root2.getRight(), level+1) ;
        swapLevel(root1.getRight(), root2.getLeft(), level+2) ;
    }

    public void swapOddLevel (TreeNode root) {

        swapLevel(root.getLeft(), root.getRight(), 0) ;

    }

    public boolean isFullBT (TreeNode root) {
        if (root == null)
            return true;

        if (root.getLeft() == null && root.getRight() == null)
            return true;

        if (root.getLeft() != null && root.getRight() != null) {
            return (isFullBT(root.getLeft()) && isFullBT(root.getRight())) ;
        }

        return false;
    }

class Res {
        int val ;
    }

    public int maxSum(TreeNode root) {


        Res res = new Res() ;

        res.val = Integer.MIN_VALUE ;
       findMaxUtil(root, res) ;
        return res.val ;
    }


    public int minDepth (TreeNode root) {

        if (root == null)
            return 0 ;

        if (root.getRight() == null && root.getRight() == null)
            return 1 ;

        if (root.getRight() == null)
            return (1 + minDepth(root.getLeft())) ;

        if (root.getLeft() == null)
            return (1+ minDepth(root.getRight())) ;

        return Math.min (minDepth(root.getLeft()),
                minDepth(root.getRight())) + 1 ;
    }

    public TreeNode buildTreefromInPost(Integer[] inArr, int inStart, int inEnd,
                                    Integer[] postArr, int postStart, int postEnd) {

        if (inStart > inEnd || postStart > postEnd) return null ;
        int value = postArr[postEnd] ;
        TreeNode postTree = new TreeNode(value) ;
        int k = 0 ;
        for (int i = 0 ; i < inArr.length ; i++) {
            if (value == inArr[i] ) {
                k = i;
                break;
            }
        }

        postTree.setLeft(buildTreefromInPost(inArr, inStart, k -1, postArr,
                postStart, postStart +k - inStart -1));
        postTree.setRight(buildTreefromInPost(inArr, k+1, inEnd, postArr,
                postStart+k-inStart, postEnd-1));

        return postTree ;
    }



    public void buildTreeInPre() {

        List<Integer> listIn = this.inOrder(this.getRoot());
        List<Integer> listPre = this.preOrder(this.getRoot());

        Integer[] inArr = listIn.toArray(new Integer[listIn.size()]);
        Integer[] preArr = listPre.toArray(new Integer[listPre.size()]) ;

        this.setNewRoot( newTree(inArr, 0, inArr.length-1,
                preArr, 0, preArr.length-1)) ;


    }

    public TreeNode newTree(Integer[] inArr, int inStart, int inEnd,
                        Integer[] preArr, int preStart, int preEnd) {

        if (inStart > inEnd || preStart > preEnd) {
            return null;
        }

        int value = preArr[preStart];
        TreeNode newTree = new TreeNode(value);

        int k = 0;

        for (int i = 0; i < inArr.length ; i++) {
            if (inArr[i] == value) {
                k = i;
                break;
            }
        }

        newTree.setLeft(newTree(inArr, inStart, k - 1, preArr, preStart + 1,
                preStart + k - inStart
        ));
        newTree.setRight(newTree(inArr, k + 1, inEnd, preArr,
                preStart + k - inStart + 1,
                preEnd
        ));

        return newTree;
    }

    public int largetBST(TreeNode root) {

        return (largestBSTTree(root).getSize()) ;
    }

    public Wrapper largestBSTTree(TreeNode root) {

        Wrapper curr = new Wrapper() ;

        if (root == null) {
            curr.setBST(true);
            return curr ;
        }

        Wrapper l = largestBSTTree(root.getLeft()) ;
        Wrapper r = largestBSTTree(root.getRight()) ;
        curr.setLower(Math.min(root.getData(), l.getLower()) );
        curr.setUpper(Math.max(root.getData(), r.getUpper()) );

        if (l.isBST() && r.isBST() &&  l.upper <= root.getData() && r.lower >= root.getData()) {

            curr.setSize(l.getSize() + r.getSize() + 1); ;
            curr.setBST(true);
        }
        else {
            curr.setBST(false); ;
            curr.setSize(Math.max(l.getSize(), r.getSize()));
        }
        return curr ;
    }

    public List<ArrayList<String>> allPaths (TreeNode root) {

        return null ;
    }

    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>() ;
        if (findAllLeaves(root, res) > -1) {
            return res ;
        }
        else {
            return null ;
        }
    }

    public int findAllLeaves(TreeNode root, List<List<Integer>> res) {
        if (root == null) {
            return -1 ;
        }

        int left = findAllLeaves(root.getLeft(), res) ;
        int right = findAllLeaves(root.getRight(), res) ;

        int max = Math.max(left, right) + 1 ;

        if (res.size() <= max) {
            res.add(new ArrayList<Integer>()) ;
        }
        res.get(max).add(root.getData()) ;
        return max ;
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.insertRecData(100);
        tree.insertData(90);
        tree.insertRecData(85);
        tree.insertData(95);
        tree.insertRecData(110);
        tree.insertData(105);
        tree.insertRecData(115);
        tree.getRoot().printTree();
        System.out.println(tree.preOrder(tree.getRoot()));
        System.out.println (tree.inOrder(tree.getRoot())) ;
        System.out.println (tree.postOrder2(tree.getRoot())) ;

    }
}
