package mybatis.binding;

import mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 10:41
 */
public class MappedProxy implements InvocationHandler {

    private SqlSession session;

    public MappedProxy(SqlSession session) {
        this.session = session;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?> returnType = method.getReturnType();
        //判断returnType是不是Collection的子类或者Collection本身
        if(Collection.class.isAssignableFrom(returnType)) {
            return session.selectList(method.getDeclaringClass().getName()+ "." + method.getName(),args == null ? null: args[0]);
        }

        return session.selectOne(method.getDeclaringClass().getName() + "." + method.getName(),args == null ? null : args[0]);
    }
}




























