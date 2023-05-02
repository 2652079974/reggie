package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.AddressBook;
import org.springframework.stereotype.Component;

/**
 * @author Maplerain
 * @date 2023/4/21 13:00
 **/
@Mapper
@Component
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
