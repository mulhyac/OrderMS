package com.everygamer.dao.mybatis;

import com.everygamer.bean.OrderItem;
import com.everygamer.dao.OrderListDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderListDao")
public class OrderListDaoImpl implements OrderListDao {
    @Autowired
    private OrderListDao dao;

    @Override
    public OrderItem getOrderById(int id) {
        return dao.getOrderById(id);
    }

    @Override
    public int addOrder(OrderItem orderItem) {
        int cRows = dao.addOrder(orderItem);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addOrder");
        }
        return 1;
    }

    @Override
    public int updateState(int id, Integer state) {
        int cRows = dao.updateState(id, state);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: updateState");
        }
        return cRows;
    }

    @Override
    public List<OrderItem> listOrder(String userName, String phone, Integer state, Integer startTime, Integer endTime) {
        if (userName != null) {
            userName = "%" + userName + "%";
        }

        if (phone != null) {
            phone = "%" + phone + "%";
        }
        return dao.listOrder(userName, phone, state, startTime, endTime);
    }
}
