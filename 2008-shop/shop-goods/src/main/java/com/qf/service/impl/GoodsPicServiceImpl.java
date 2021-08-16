package com.qf.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qf.entity.Goods;
import com.qf.entity.GoodsPic;
import com.qf.mapper.GoodsPicMapper;
import com.qf.service.IGoodsPicService;
import org.springframework.stereotype.Service;

@Service
public class GoodsPicServiceImpl extends ServiceImpl<GoodsPicMapper,GoodsPic> implements IGoodsPicService {
}
