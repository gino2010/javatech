import com.gino.jvm.model.Subject;
import com.gino.jvm.model.SubjectImpl;
import com.gino.jvm.proxy.SpringProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.aop.framework.ProxyFactory;

/**
 * unit test
 *
 * @author gino
 * Created on 2018/5/10
 */
@RunWith(JUnit4.class)
public class UnitTest {
    @Test
    public void springProxy(){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new SubjectImpl());
        proxyFactory.addAdvice(new SpringProxy());

        Subject subject = (Subject)proxyFactory.getProxy();
        subject.hello("spring aop");
    }
}
