package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.AddressBookMapper;
import org.example.pojo.AddressBook;
import org.example.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author Maplerain
 * @date 2023/4/21 13:02
 **/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
