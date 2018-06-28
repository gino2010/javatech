import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author gino
 * Created on 2018/6/5
 */
@RunWith(JUnit4.class)
public class UnitTest {
    class Value {
        int val;
    }

    @Test
    public void testJava() {
        Value v1 = new Value();

        v1.val = 9;
        Value v2 = new Value();
        v2 = v1;
        v2.val = 10;
        System.out.println(v1.val);
        System.out.println(v2.val);
    }

    public void 打印() {
        System.out.println("中文方法");
    }

    @Test
    public void testString() {
        StringBuilder stringBuilder = new StringBuilder("abc defg hijk");
        stringBuilder.reverse();
        System.out.println(stringBuilder);

        int index = 0;
        while (true) {
            index = stringBuilder.indexOf(" ", index + 1);
            System.out.println(index);
            if (index == -1) {
                break;
            }
        }
    }
}
