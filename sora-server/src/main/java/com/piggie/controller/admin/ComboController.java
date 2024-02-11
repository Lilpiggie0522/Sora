package com.piggie.controller.admin;

import com.piggie.dto.SetmealDTO;
import com.piggie.dto.SetmealPageQueryDTO;
import com.piggie.result.PageResult;
import com.piggie.result.Result;
import com.piggie.service.ComboService;
import com.piggie.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ComboController
 * Package: com.piggie.controller.admin
 * Description:
 *
 * @Author Piggie
 * @Create 10/02/2024 3:51 pm
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("admin/setmeal")
public class ComboController {
    @Autowired
    private ComboService comboService;
    @PostMapping
    public Result save(@RequestBody SetmealDTO comboDto) {
        log.info("combo looks like this {}", comboDto);
        comboService.save(comboDto);
        return Result.success();
    }
    @ApiOperation("get combo according to id")
    @GetMapping("/{id}")
    public Result<SetmealVO> getComboById(@PathVariable Long id) {
        SetmealVO comboVo = comboService.getComboById(id);
        return Result.success(comboVo);
    }

    @ApiOperation("combo page query")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO comboPageDto) {
        PageResult pageResult = comboService.pageQuery(comboPageDto);
        return Result.success(pageResult);
    }

    @ApiOperation("update combo")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("combo dto is {}", setmealDTO);
        comboService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("set status of combo")
    public Result setComboStatus(@RequestParam Long id, @PathVariable Integer status) {
        log.info("set status: id is {}, status to {}", id, status);
        comboService.setComboStatus(id, status);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("delete combos by batch")
    public Result deleteCombosByIds(@RequestParam List<Long> ids) {
        log.info("ids are {}", ids);
        comboService.deleteByIds(ids);
        return Result.success();
    }
}
