package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author admin
* @description 针对表【base_attr_value(属性值表)】的数据库操作Service实现
* @createDate 2022-11-27 19:51:46
*/
@Service
public class BaseAttrValueServiceImpl extends ServiceImpl<BaseAttrValueMapper, BaseAttrValue>
    implements BaseAttrValueService{

    @Autowired
    BaseAttrValueMapper valueMapper;
    @Autowired
    BaseAttrInfoMapper attrInfoMapper;
    @Override
    public void updateAttrNameAndValue(BaseAttrInfo attrInfo) {
        //修改属性名和值

            //修改属性名
            attrInfoMapper.updateById(attrInfo);

            //修改属性值
            //1收集所有值的传过来的id
        List<BaseAttrValue> newList = attrInfo.getAttrValueList();
        List<Long> newIds = new ArrayList<>();
        if(newList != null && newList.size() > 0){

            newIds = newList.stream().map(value -> value.getId()).collect(Collectors.toList());
        }
        LambdaQueryWrapper<BaseAttrValue> query = new LambdaQueryWrapper<>();
        query.eq(BaseAttrValue::getAttrId,attrInfo.getId());

        List<BaseAttrValue> oldList = valueMapper.selectList(query);

        List<Long> collectIds= new ArrayList<>();
        if(oldList.size() > 0){
            for (BaseAttrValue baseAttrValue : oldList) {
                if(!newIds.contains(baseAttrValue.getId())){
                    collectIds.add(baseAttrValue.getId());
                }
            }
        }
        if(collectIds.size() >0){
            for (Long collectId : collectIds) {
                valueMapper.deleteById(collectId);
            }
        }


        //2 先删除不在这个list里面的id delete from  base_attr_value where attr_id = and id not in ();



        for (BaseAttrValue baseAttrValue : newList) {
            if(baseAttrValue.getId() != null){
                //修改
                valueMapper.updateById(baseAttrValue);
            }else {
                //新增
                baseAttrValue.setAttrId(attrInfo.getId());
                valueMapper.insert(baseAttrValue);

            }
        }



    }


}




