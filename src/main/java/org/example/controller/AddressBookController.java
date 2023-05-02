package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.Result;
import org.example.pojo.AddressBook;
import org.example.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Maplerain
 * @date 2023/4/21 13:03
 **/
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return Result.success(addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        queryWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(queryWrapper);

        addressBook.setIsDefault(1);

        addressBookService.updateById(addressBook);
        return Result.success(addressBook);
    }

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result get (@PathVariable Long id){
        AddressBook user = addressBookService.getById(id);
        if(user!=null){
            return Result.success(id);
        }else{
            return Result.error("查询失败！用户不存在！");
        }
    }

    @GetMapping("/default")
    public Result getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault,1);

        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if(addressBook == null){
            return Result.error("查询失败！地址信息不存在！");
        }else{
            return Result.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     *
     * @param addressBook
     * @return
     */
    @GetMapping("/list")
    public Result list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(addressBook.getUserId()!=null,AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        return Result.success(addressBookService.list(queryWrapper));
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){

        log.info("ids={}",ids);
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(AddressBook::getId,ids).eq(AddressBook::getUserId,BaseContext.getCurrentId());
        if (addressBookService.count(queryWrapper)!=ids.size()) {
            return Result.error("删除失败！");
        }
        addressBookService.removeBatchByIds(ids);
        return Result.success("删除成功！");
    }

}
