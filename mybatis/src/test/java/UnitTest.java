import com.gino.mybatis.bean.UserBean;
import com.gino.mybatis.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Timestamp;

/**
 * @author gino
 * Created on 2018/5/11
 */
@RunWith(JUnit4.class)
public class UnitTest {
    UserDao userDao = new UserDao();

    @Test
    public void testCRUD(){
        UserBean user = new UserBean(null, "test", 30, "place", new Timestamp(System.currentTimeMillis()));

        int id = userDao.addUser(user);
        userDao.getUserList("test");
        user = userDao.getUserByID(id);
        user.setAddress("new place");
        userDao.updateUser(user);
        userDao.getUserByID(id);
        userDao.deleteUser(id);

    }
}
