package com.qf.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qf.aop.annocation.LoginUser;
import com.qf.entity.Address;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addressController")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping("/getAddressListByUid")
    public List<Address> getAddressListByUid(Integer uid) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("uid", uid);
        return addressService.selectList(entityWrapper);
    }

    @RequestMapping("/addAddress")
    @LoginUser
    public ResultEntity addAddress(User user, Address address) {
        address.setUid(user.getId());
        addressService.addAddress(address);
        return ResultEntity.success();
    }

    @RequestMapping("/getAddressById")
    public Address getAddressById(Integer addressId) {
        return addressService.selectById(addressId);
    }
}
