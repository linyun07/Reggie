package com.linyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyun.mapper.AddressBookMapper;
import com.linyun.pojo.AddressBook;
import com.linyun.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * 使用了mybatis来实现功能
 * @author linyun
 * @date 2023/04/04/20:58
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
