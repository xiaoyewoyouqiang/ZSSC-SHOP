package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {

    Integer addOrder(@Param("order") Order order, @Param("tableIndex") Integer tableIndex);

    void batchDelOrderDetail(@Param("odList") List<OrderDetail> orderDetailList, @Param("tableIndex") Integer tabIndex);
}
