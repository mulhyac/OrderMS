package com.everygamer.dao;

import com.everygamer.bean.Logistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogisticsDao {
    Logistics getLogisticsByName(String name);

    List<Logistics> getAllLogistics();

    int addLogistics(String name);

    int updateLogistics(Logistics logistics);

    int delLogistics(int id);
}
