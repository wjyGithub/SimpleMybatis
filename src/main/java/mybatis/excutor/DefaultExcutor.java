package mybatis.excutor;

import mybatis.config.Configuration;
import mybatis.config.MappedStatement;
import mybatis.reflection.ReflectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 10:31
 */
public class DefaultExcutor implements Excutor {

    private Configuration conf;

    public DefaultExcutor(Configuration conf) {
        this.conf = conf;
    }

    public <E> List<E> query(MappedStatement ms, Object parameter) {
        List<E> ret = new ArrayList<E>();

        try {
            Class.forName(conf.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(conf.getJdbcUrl(),conf.getJdbcUsername(),conf.getJdbcPassword());
            preparedStatement = connection.prepareStatement(ms.getSql());
            //处理sql语句中的占位符
            parameterize(preparedStatement,parameter);
            //执行查询操作获取ResultSet
            resultSet = preparedStatement.executeQuery();

            //将结果集通过反射技术,填充到list中
            handlerResultSet(resultSet,ret,ms.getResultType());

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }catch (SQLException e) {

            }
        }
        return ret;
    }

    /**
     * 对preparestatement中占位符进行处理
     * @param preparedStatement
     * @param parameter
     */
    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException{
        if(parameter instanceof Integer) {
            preparedStatement.setInt(1,(Integer) parameter);
        }else if(parameter instanceof Long) {
            preparedStatement.setLong(1,(Long) parameter);
        }else if(parameter instanceof String) {
            preparedStatement.setString(1,(String) parameter);
        }
    }


    /**
     * 读取resultset中的数据，并转换成目标对象
     * @param resultSet
     * @param ret
     * @param className
     * @param <E>
     */
    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String className) {
        Class<E> clazz = null;

        try {
            //通过反射获取类对象
            clazz = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while (resultSet.next()) {

                Object entity = clazz.newInstance();
                ReflectionUtil.setPropToBeanFromResultSet(entity,resultSet);
                ret.add((E) entity);
            }
        }catch (Exception e) {

        }
    }


}
