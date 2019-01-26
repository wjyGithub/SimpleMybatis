package mybatis.session;

import mybatis.config.Configuration;
import mybatis.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * 1.加载配置信息到内存
 * 2.SqlSession的工厂类,负责生成sqlSession
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 9:44
 */
public class SqlSessionFaction {

    /**
     * 配置信息是全局唯一的
     */
    private Configuration conf = new Configuration();

    /**
     * 记录mapper xml文件的存放的位置
     */
    public static final String MAPPER_CONFIG_LOCATION = "mappers";

    /**
     * 记录数据库连接信息文件存放位置
     */
    public static final String DB_CONFIG_FILE = "db.properties";

    public SqlSessionFaction(){

        //加载db.properties配置文件
        loadDbInfo();
        //加载mappers/*.xml文件
        loadMappersInfo();
    }

    /**
     * 加载数据库的配置信息
     */
    private void loadDbInfo() {
        InputStream dbIn = SqlSessionFaction.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties p = new Properties();

        try {
            //将配置信息写入Properties对象
            p.load(dbIn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        conf.setJdbcDriver(p.get("jdbc.driver").toString());
        conf.setJdbcPassword(p.get("jdbc.password").toString());
        conf.setJdbcUrl(p.get("jdbc.url").toString());
        conf.setJdbcUsername(p.get("jdbc.username").toString());
    }

    /**
     * 加载指定文件夹下的所有mapper.xml文件
     */
    private void loadMappersInfo() {
        URL resources = null;

        resources = SqlSessionFaction.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        File mappers = new File(resources.getFile()); //获取指定文件夹信息
        if(mappers.isDirectory()) {
            File[] listFiles = mappers.listFiles();
            //遍历文件夹下所有的mapper.xml,解析信息后，注册到conf对象中
            for(File file :listFiles) {
                loadMapperInfo(file);
            }
        }
    }

    /**
     * 加载指定的mapper.xml文件
     * @param file
     */
    private void loadMapperInfo(File file) {
        SAXReader reader = new SAXReader();
        //通过read方法读取一个文件 转成Document对象
        Document document = null;

        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点元素对象<mapper>
        Element root = document.getRootElement();
        //获取命名空间
        String namespace = root.attribute("namespace").getData().toString();
        //获取select子节点列表
        List<Element> selects = root.elements("select");
        //遍历select节点，将信息记录到MappedStatement对象，并登记到configuration中
        for(Element element : selects) {
            MappedStatement mappedStatement = new MappedStatement();
            String id = element.attribute("id").getData().toString();
            String resultType = element.attribute("resultType").getData().toString();
            String sql = element.getData().toString();
            String sourceId = namespace + "." + id;

            mappedStatement.setSourceId(sourceId);
            mappedStatement.setNamespace(namespace);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);

            //注册到configuration对象中
            conf.getMappedStatements().put(sourceId,mappedStatement);
        }
    }

    /**
     * 工厂模式,生成sqlSession
     * @return
     */
    public SqlSession openSession() {
        return new DefaultSqlSession(conf);
    }


}












































