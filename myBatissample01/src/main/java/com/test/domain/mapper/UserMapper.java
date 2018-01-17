package com.test.domain.mapper;

import java.util.List;

import com.test.domain.User;

public interface UserMapper {

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 根据ID查找用户信息
     *
     * @param id
     * @return
     */
    User selectById(Long id);

    /**
     * 用户总数量
     *
     * @return
     */
    int selectCount();

    /**
     * 查找所有用户列表
     *
     * @return
     */
    List<User> selectAll();
}
