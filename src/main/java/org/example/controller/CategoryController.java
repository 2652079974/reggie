package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Maplerain
 * @date 2023/4/16 12:41
 *
 * 分类管理
 **/
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.success("新增分类成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result page(int page,int pageSize){
        // 分页构造器
        Page<Category> pageInfo = new Page(page,pageSize);
        // 查询语句构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 降序排序
        queryWrapper.orderByDesc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);

        return Result.success(pageInfo);
    }

    /**
     * 根据 id 删除分类
     *
     * @param
     * @return
     */
    @DeleteMapping
    public Result delete(Long ids){
        categoryService.remove(ids);
        return Result.success();
    }

    /**
     * 修改分类信息
     *
     * @param category
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.success("修改分类信息成功！");
    }

    /**
     * 获取菜品分类列表
     *
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result list(String type){
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(!StringUtils.isNullOrEmpty(type),Category::getType,type);

        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return Result.success(list);
    }



}
