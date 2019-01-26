package mybatis.session;


import entiy.TUser;
import mapper.TUserMapper;
import org.junit.Test;

import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 11:33
 */
public class SqlSessionTest {

    @Test
    public void test(){

        SqlSessionFaction factory = new SqlSessionFaction();
        SqlSession session = factory.openSession();
        System.out.println(session);

        TUserMapper userMapper = session.getMapper(TUserMapper.class);

        TUser user = userMapper.selectByPrimarykey(1L);
        System.out.println(user);

        System.out.println("------------------");

        List<TUser> selectAll = userMapper.selectAll();
        System.out.println(selectAll);

    }

}