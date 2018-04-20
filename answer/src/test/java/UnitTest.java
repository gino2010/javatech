import com.gino.anwser.DoublyLinkedList;
import com.gino.anwser.Node;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit Test
 *
 * @author gino
 * Created on 2018/4/20
 */
@RunWith(JUnit4.class)
public class UnitTest {
    @Test
    public void test() {
        testReverse(0);
        testReverse(1);
        testReverse(2);
        testReverse(10);
    }

    private void testReverse(int count) {
        DoublyLinkedList<Integer> linkedList = new DoublyLinkedList<>();
        StringBuilder forward = new StringBuilder();
        StringBuilder backward = new StringBuilder();
        // init test data
        for (int i = 0; i < count; i++) {
            linkedList.addLast(i);
            forward.append(i);
            backward.insert(0, i);
        }

        // two ways for forward detection
        Assert.assertEquals(forward(linkedList), forward.toString());
        Assert.assertEquals(backward(linkedList), backward.toString());

        System.out.print("count " + count + " before reverse: ");
        for (int i = 0; i < count; i++) {
            Node node = linkedList.getNode(i);
            System.out.print(node.element + " ");
            Assert.assertEquals(node.element, i);
            if (i != 0) {
                Assert.assertEquals(node.prev.element, i - 1);
            }
            if (i != count - 1) {
                Assert.assertEquals(node.next.element, i + 1);
            }
        }
        System.out.println("pass");

        // reverse list
        linkedList.reverse();

        // two ways for reverse detection
        Assert.assertEquals(forward(linkedList), backward.toString());
        Assert.assertEquals(backward(linkedList), forward.toString());

        System.out.print("count " + count + " after  reverse: ");
        for (int i = 0; i < count; i++) {
            Node node = linkedList.getNode(i);
            System.out.print(node.element + " ");
            Assert.assertEquals(node.element, count - i - 1);
            if (i != 0) {
                Assert.assertEquals(node.prev.element, count - i);
            }
            if (i != count - 1) {
                Assert.assertEquals(node.next.element, count - i - 2);
            }
        }
        System.out.println("pass");
    }

    private String forward(DoublyLinkedList list) {
        Node node = list.getHead();
        StringBuilder temp = new StringBuilder();
        while (node != null) {
            temp.append(node.element);
            node = node.next;
        }
        return temp.toString();
    }

    private String backward(DoublyLinkedList list) {
        Node node = list.getTail();
        StringBuilder temp = new StringBuilder();
        while (node != null) {
            temp.append(node.element);
            node = node.prev;
        }
        return temp.toString();
    }
}
