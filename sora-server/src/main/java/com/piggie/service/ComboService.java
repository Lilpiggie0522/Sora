package com.piggie.service;

import com.piggie.dto.SetmealDTO;
import com.piggie.dto.SetmealPageQueryDTO;
import com.piggie.entity.Setmeal;
import com.piggie.result.PageResult;
import com.piggie.vo.SetmealVO;

import java.util.List;

/**
 * ClassName: ComboService
 * Package: com.piggie.service
 * Description:
 *
 * @Author Piggie
 * @Create 10/02/2024 3:52 pm
 * @Version 1.0
 */
public interface ComboService {

    void save(SetmealDTO comboDto);

    PageResult pageQuery(SetmealPageQueryDTO comboPageDto);

    SetmealVO getComboById(Long id);

    void update(SetmealDTO setmealDTO);

    void setComboStatus(Long id, Integer status);

    void deleteByIds(List<Long> ids);
}
