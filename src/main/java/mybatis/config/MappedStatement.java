package mybatis.config;

/**
 * 保存mapper.xml的sql信息
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 9:41
 */
public class MappedStatement {

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 命名空间 + "." + sql的Id
     */
    private String sourceId;

    /**
     * 返回的类型
     */
    private String resultType;

    private String sql;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}































