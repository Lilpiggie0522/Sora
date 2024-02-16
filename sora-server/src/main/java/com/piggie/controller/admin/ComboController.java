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
import org.springframework.cache.annotation.CacheEvict;
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

    /**
     * Create new combo
     * @param comboDto
     * @return
     */
    @PostMapping
    @ApiOperation("saving new combo")
    @CacheEvict(cacheNames = "comboCache", key = "#comboDto.categoryId") // comboCache::13
    public Result save(@RequestBody SetmealDTO comboDto) {
        log.info("combo looks like this {}", comboDto);
        comboService.save(comboDto);
        return Result.success();
    }

    /**
     * get combo using combo id
     * @param id
     * @return
     */
    @ApiOperation("get combo according to id")
    @GetMapping("/{id}")
    public Result<SetmealVO> getComboById(@PathVariable Long id) {
        SetmealVO comboVo = comboService.getComboById(id);
        return Result.success(comboVo);
    }

    /**
     * combo page query
     * @param comboPageDto
     * @return
     */
    @ApiOperation("combo page query")
    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO comboPageDto) {
        PageResult pageResult = comboService.pageQuery(comboPageDto);
        return Result.success(pageResult);
    }

    /**
     * update combo using comboId
     * @param setmealDTO
     * @return
     */
    @ApiOperation("update combo")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("combo dto is {}", setmealDTO);
        comboService.update(setmealDTO);
        return Result.success();
    }

    /**
     * change status of combo
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("set status of combo")
    @CacheEvict(cacheNames = "comboCache", allEntries = true)
    public Result setComboStatus(@RequestParam Long id, @PathVariable Integer status) {
        log.info("set status: id is {}, status to {}", id, status);
        comboService.setComboStatus(id, status);
        return Result.success();
    }

    /**
     * delete combos using list of combo ids
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("delete combos by batch")
    @CacheEvict(cacheNames = "comboCache", allEntries = true)
    public Result deleteCombosByIds(@RequestParam List<Long> ids) {
        log.info("ids are {}", ids);
        comboService.deleteByIds(ids);
        return Result.success();
    }
}
