package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.domain.CarGoods;
import com.qf.entity.Car;

import java.util.List;
import java.util.Map;

public interface CarMapper extends BaseMapper<Car>{

    List<CarGoods> getMySQLUserCarList(Integer id);

    CarGoods getCarGoodsById(Object gid);
}
