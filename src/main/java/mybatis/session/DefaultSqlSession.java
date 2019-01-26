package mybatis.session;

import mybatis.binding.MappedProxy;
import mybatis.config.Configuration;
import mybatis.config.MappedStatement;
import mybatis.excutor.DefaultExcutor;
import mybatis.excutor.Excutor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 10:23
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration conf;

    private Excutor excutor;

    public DefaultSqlSession(Configuration conf) {
        this.conf = conf;
        excutor = new DefaultExcutor(conf);
    }

    public <T> T selectOne(String statement, Object parameter) {
        List<T> selectList = this.selectList(statement, parameter);
        if(selectList == null || selectList.size() == 0) {
            return null;
        }
        if(selectList.size() == 1) {
            return selectList.get(0);
        }
        throw new RuntimeException("too many result");
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = conf.getMappedStatements().get(statement);
        return excutor.query(ms,parameter);
    }

    public <T> T getMapper(Class<T> type) {
        MappedProxy mp = new MappedProxy(this);
        //参数1:指定需要使用的类加载器
        //参数2:指定生成的代理对象需要实现的接口有哪些
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},mp);
    }
}
