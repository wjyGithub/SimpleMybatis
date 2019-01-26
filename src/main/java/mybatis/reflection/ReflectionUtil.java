package mybatis.reflection;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 11:10
 */
public class ReflectionUtil {

    /**
     * 为指定的bean的propName属性的值设置value
     * @param bean  目标对象
     * @param propName 对象的属性名
     * @param value 值
     */
    public static void setPropToBean(Object bean,String propName,Object value) {
        Field f;
        try {
            f = bean.getClass().getDeclaredField(propName);
            f.setAccessible(true);
            f.set(bean,value);
        } catch( Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从resultSet中读取一行数据，并填充到指定的实体bean
     * @param entity 将填充的实体bean
     * @param resultSet 从数据库加载的数据
     * @throws SQLException
     */
    public static void setPropToBeanFromResultSet(Object entity, ResultSet resultSet) throws SQLException {

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for(int i=0; i < declaredFields.length; i++) {
            //字符串数据类型
            if(declaredFields[i].getType().getSimpleName().equals("String")) {
                setPropToBean(entity,declaredFields[i].getName(),resultSet.getString(declaredFields[i].getName()));
            }else if(declaredFields[i].getType().getSimpleName().equals("Integer")) {
                setPropToBean(entity,declaredFields[i].getName(),resultSet.getInt(declaredFields[i].getName()));
            }else if(declaredFields[i].getType().getSimpleName().equals("Long")) {
                setPropToBean(entity,declaredFields[i].getName(),resultSet.getLong(declaredFields[i].getName()));
            }
        }
    }
}
