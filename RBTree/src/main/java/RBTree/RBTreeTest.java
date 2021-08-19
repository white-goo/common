package RBTree;

import java.util.Scanner;

public class RBTreeTest {

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);

        RBTree<String, Object> stringObjectRBTree = new RBTree<String, Object>();

        while (true) {
            System.out.println("请输入key");
            final String next = scanner.next();
            System.out.println();
            stringObjectRBTree.insert(next, next);

            TreeOperation.show(stringObjectRBTree.getRoot());

            System.out.println(stringObjectRBTree.get(next));
        }

    }

}
