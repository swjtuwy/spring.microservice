package com.test.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.test.domain.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

public class TestUserMapper {

    // https://gitee.com/saytime/mybatis_demo/tree/master/src

    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    UserMapper userMapper = null;

    @Before
    public void before(){
        // mybatis 配置文件地址
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加载配置文件，并构建sqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 获取sqlSession对象
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void after(){
        if(sqlSession != null){
            // 注意这里的commit，否则增删改操作提交不成功
            sqlSession.commit();
            sqlSession.close();
        }
    }

    /**
     * 查找所有用户
     */
    @org.junit.Test
    public void testSelectAll(){
        // 第一种方式：动态代理获取UserMapper的代理类
        List<User> users = userMapper.selectAll();
        if(users != null && users.size() > 0){
            for (User user : users) {
                System.out.println(user);
            }
        }

        // 第二种方式
        User user = (User) sqlSession.selectOne("com.test.domain.mapper.UserMapper.selectById", 2L);
        System.out.println(user);
    }
}
