package mybatis.excutor;

import mybatis.config.MappedStatement;

import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 10:31
 */
public interface Excutor {
    /**
     *
     * @param ms
     * @param parameter
     * @param <E>
     * @return
     */
    <E> List<E> query(MappedStatement ms,Object parameter);
}


























