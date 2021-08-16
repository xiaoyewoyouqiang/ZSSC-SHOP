package com.qf.service;

import com.baomidou.mybatisplus.service.IService;
import com.qf.domain.CarGoods;
import com.qf.entity.Car;
import com.qf.entity.Goods;

import java.util.List;

public interface ICarService extends IService<Car>{
    List<CarGoods> getMySQLUserCarList(Integer id);

    CarGoods getCarGoodsById(Object gid);
}
