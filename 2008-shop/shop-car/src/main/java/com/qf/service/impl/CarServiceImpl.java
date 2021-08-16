package com.qf.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.domain.CarGoods;
import com.qf.entity.Car;
import com.qf.entity.Goods;
import com.qf.mapper.CarMapper;
import com.qf.service.ICarService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper,Car> implements ICarService {
    @Override
    public List<CarGoods> getMySQLUserCarList(Integer id) {
        return baseMapper.getMySQLUserCarList(id);
    }

    @Override
    public CarGoods getCarGoodsById(Object gid) {
        return baseMapper.getCarGoodsById(gid);
    }
}
