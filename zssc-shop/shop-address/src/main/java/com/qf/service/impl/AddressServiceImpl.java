package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.Address;
import com.qf.mapper.AddressMapper;
import com.qf.service.IAddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper,Address> implements IAddressService {
    @Override
    public void addAddress(Address address) {
        baseMapper.addAddress(address);
    }
}
