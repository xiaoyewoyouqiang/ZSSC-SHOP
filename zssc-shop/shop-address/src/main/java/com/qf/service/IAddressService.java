package com.qf.service;

import com.baomidou.mybatisplus.service.IService;
import com.qf.entity.Address;

public interface IAddressService extends IService<Address> {
    void addAddress(Address address);
}
