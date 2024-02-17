package com.piggie.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.piggie.dto.SetmealDTO;
import com.piggie.dto.SetmealPageQueryDTO;
import com.piggie.entity.Setmeal;
import com.piggie.entity.SetmealDish;
import com.piggie.mapper.ComboDishMapper;
import com.piggie.mapper.ComboMapper;
import com.piggie.result.PageResult;
import com.piggie.service.ComboService;
import com.piggie.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ComboServiceImpl
 * Package: com.piggie.service.impl
 * Description:
 *
 * @Author Piggie
 * @Create 10/02/2024 3:53 pm
 * @Version 1.0
 */
@Service
public class ComboServiceImpl implements ComboService {
    @Autowired
    private ComboMapper comboMapper;

    @Autowired
    private ComboDishMapper comboDishMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SetmealDTO comboDto) {
        Setmeal combo = new Setmeal();
        BeanUtils.copyProperties(comboDto, combo);
        comboMapper.save(combo);
        List<SetmealDish> comboDishes = comboDto.getSetmealDishes();
        for (SetmealDish comboDish : comboDishes) {
            comboDish.setSetmealId(combo.getId());
        }
        comboDishMapper.saveBatch(comboDishes);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO comboPageDto) {
        PageHelper.startPage(comboPageDto.getPage(), comboPageDto.getPageSize());
        Page<Setmeal> page = comboMapper.pageQuery(comboPageDto);
        List<Setmeal> result = page.getResult();
        long total = page.getTotal();
        return new PageResult(total, result);
    }

    @Override
    public SetmealVO getComboById(Long id) {
        SetmealVO comboVo = comboMapper.getComboVoById(id);
        List<SetmealDish> dishes = comboDishMapper.getDishByComboId(id);
        comboVo.setSetmealDishes(dishes);
        return comboVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SetmealDTO setmealDTO) {
        Setmeal combo = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, combo);
        comboMapper.update(combo);
        comboDishMapper.deleteByComboId(setmealDTO.getId());
        List<SetmealDish> comboDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish comboDish : comboDishes) {
            comboDish.setSetmealId(combo.getId());
        }
        comboDishMapper.saveBatch(comboDishes);
    }

    @Override
    public void setComboStatus(Long id, Integer status) {
        Setmeal combo = Setmeal.builder().id(id).status(status).build();
        comboMapper.setComboStatus(combo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        comboMapper.deleteByIds(ids);
        comboDishMapper.deleteByComboIds(ids);
    }
}
