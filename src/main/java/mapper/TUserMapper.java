package mapper;

import entiy.TUser;

import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/26 11:26
 */
public interface TUserMapper {

    /**
     * 通过主键查询用户信息
     * @return
     */
    TUser selectByPrimarykey(Long id);

    /**
     * 查询所有用户信息
     * @return
     */
    List<TUser> selectAll();

}
