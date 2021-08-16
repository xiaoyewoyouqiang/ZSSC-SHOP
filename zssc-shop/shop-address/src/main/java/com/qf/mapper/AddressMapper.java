package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Address;

public interface AddressMapper extends BaseMapper<Address> {
    void addAddress(Address address);
}
