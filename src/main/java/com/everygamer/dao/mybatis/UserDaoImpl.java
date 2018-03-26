package com.everygamer.dao.mybatis;

import com.everygamer.bean.security.AdminUser;
import com.everygamer.dao.UserDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("UserDao")
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
    UserDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public int addUser(String uname, String passwd) {
        int cRows = dao.addUser(uname, passwd);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addUser");
        }
        return cRows;
    }

    @Override
    public int updateLoginTime(long uid) {
        int cRows = dao.updateLoginTime(uid);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: updateLoginTime");
        }
        return cRows;
    }

    @Override
    public int setPasswd(long id, String passwd) {
        int cRows = dao.setPasswd(id, passwd);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: setPasswd");
        }
        return cRows;
    }

    @Override
    public int delUser(long uid) {
        int cRows = dao.delUser(uid);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: delUser");
        }
        return cRows;
    }

    @Override
    public AdminUser isExist(String uname) {
        return dao.isExist(uname);
    }

    @Override
    public AdminUser getUser(long id) {
        return dao.getUser(id);
    }

//    @Override
//    public AdminUser getLogin(String uname, String passwd) {
//        return dao.getLogin(uname, passwd);
//    }

    @Override
    public List<AdminUser> searchUser(String kw) {
        kw = "%" + kw.replaceAll(" ", "%") + "%";
        return dao.searchUser(kw);
    }

    @Override
    public void init() {
        dao = getSqlSession().getMapper(UserDao.class);
    }
}