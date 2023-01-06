package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.config.MybatisPlusConfig;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌列表
 */
@Api(tags = "品牌")
@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {

    @Autowired
    BaseTrademarkService trademarkService;

    /**
     * 品牌分页查询
     */
    @GetMapping("/{pn}/{size}")
    public Result getPageBaseTrademarkList(@PathVariable("pn") Long pn,
                                       @PathVariable("size") Long size) {


        Page<BaseTrademark> page = new Page<>(pn, size);
        trademarkService.page(page, null);

        return Result.ok(page);
    }

    /**
     * 品牌新增
     */
    @PostMapping("/save")
    public Result save(@RequestBody BaseTrademark trademark) {

            trademarkService.save(trademark);
            return Result.ok();


    }

    /**
     * 品牌回显
     */
    @GetMapping("/get/{tradeId}")
    public Result getTradeId(@PathVariable("tradeId") Long tradeId) {
        BaseTrademark baseTrademark = trademarkService.getById(tradeId);
        return Result.ok(baseTrademark);
    }
    @PutMapping("/update")
    public Result updateTradeMark(@RequestBody BaseTrademark trademark){
        trademarkService.updateById(trademark);
        return Result.ok();
    }
    /**
     * 删除品牌
     */
    @DeleteMapping("/remove/{tradeId}")
    public Result deleteTradeMark(@PathVariable("tradeId") Long tradeId) {
        trademarkService.removeById(tradeId);
        return Result.ok();
    }

    /**
     * product/baseTrademark/getTrademarkList
     * 获取品牌列表
     * */

    @GetMapping("/getTrademarkList")
    public Result getTrademarkList(){
        List<BaseTrademark> baseTrademarks = trademarkService.list();
        return Result.ok(baseTrademarks);
    }
}
