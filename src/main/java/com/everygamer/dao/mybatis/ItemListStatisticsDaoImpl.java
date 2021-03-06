package com.everygamer.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.dao.ItemListStatisDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("ItemListStatisDao")
public class ItemListStatisticsDaoImpl implements ItemListStatisDao {
    @Autowired
    private ItemListStatisDao dao;

    @Override
    public BaseItem isExist(String name, Integer itemType, Integer manu, String exData) {
        return dao.isExist(name, itemType, manu, exData);
    }

    @Override
    public int updateStatis(int id, Integer num, BigDecimal price) {
        int cRows = dao.updateStatis(id, num, price);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: updateStatis");
        }
        return cRows;
    }

    @Override
    public int addStatis(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData) {
        int cRows = dao.addStatis(name, itemType, manu, model, num, price, exData);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addStatis");
        }
        return cRows;
    }

    @Override
    public int mergeExData(int itemType, Object key, Object value) {
        return dao.mergeExData(itemType, key, value);
    }

    @Override
    public int deleteExDataKey(int itemType, Object key) {
        return dao.deleteExDataKey(itemType, key);
    }

    @Override
    public int splitItem(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData) {
        int cRows = dao.splitItem(name, itemType, manu, num, price, exData);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: splitItem");
        }
        return cRows;
    }
}
