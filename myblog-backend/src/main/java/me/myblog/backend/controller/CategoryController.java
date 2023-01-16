package me.myblog.backend.controller;

import cn.hutool.http.ContentType;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletResponse;
import me.myblog.framework.constants.SystemConstants;
import me.myblog.framework.domain.Response;
import me.myblog.framework.domain.entity.Category;
import me.myblog.framework.domain.vo.CategoryVo;
import me.myblog.framework.domain.vo.ExcelCategoryVo;
import me.myblog.framework.domain.vo.PageVo;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.service.CategoryService;
import me.myblog.framework.utils.BeanCopyUtils;
import me.myblog.framework.utils.WebUtils;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2022-11-15 15:13:06
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public Response<List<CategoryVo>> getAllCategories() {
        // TODO 封装进service
        List<Category> categories = categoryService.lambdaQuery()
                .eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL)
                .list();
        List<CategoryVo> data = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return Response.ok(data);
    }

    @PreAuthorize("@permissionService.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) {
        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            // 获取需要导出的数据
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> data = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            // 把数据写入到excel中
            EasyExcel.write(response.getOutputStream(), CategoryVo.class)
                    .autoCloseStream(false)
                    .sheet("分类导出")
                    .doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
            // 如果出现异常也要响应json
            response.reset();
            Response<Object> error = Response.error(HttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(error));
        }
    }

    @GetMapping("/page/{pageNumber}/{pageSize}")
    public Response<PageVo> page(@PathVariable("pageNumber") Integer pageNumber,
                                 @PathVariable("pageSize") Integer pageSize,
                                 @RequestParam("name") String name,
                                 @RequestParam("status") String status) {
        List<Category> categories = categoryService.lambdaQuery()
                .like(StringUtils.hasText(name), Category::getName, name)
                .eq(StringUtils.hasText(status), Category::getStatus, status)
                .page(new Page<>(pageNumber, pageSize))
                .getRecords();
        PageVo data = new PageVo(categories, categories.size());
        return Response.ok(data);
    }

    @PostMapping
    public Response<Object> postCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Response.ok();
    }

    @GetMapping("/{id}")
    public Response<Category> getCategoryById(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getById(id);
        return Response.ok(category);
    }

    @PutMapping
    public Response<Object> putCategoryById(@RequestBody Category category) {
        categoryService.updateById(category);
        return Response.ok();
    }

    @DeleteMapping("/{id}")
    public Response<Object> remove(@PathVariable(value = "id") Long id) {
        categoryService.removeById(id);
        return Response.ok();
    }

}

