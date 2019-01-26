package mybatis.session;

import java.util.List;

/**
 * mybatis暴露给外部的接口，实现增删改查的能力
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 9:45
 */
public interface SqlSession {

    /**
     * 根据传入的条件查询单一结果
     * @param statement 方法对应的sql语句，namespace + id
     * @param parameter 要传入到sql语句中的查询参数
     * @param <T>
     * @return 返回指定的结果对象
     */
    <T> T selectOne(String statement,Object parameter);

    /**
     * 根据条件查询,返回泛型集合
     * @param statement
     * @param parameter
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 返回给定type的代理对象
     * @param type
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> type);
}
