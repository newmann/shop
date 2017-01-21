/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web.admin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.cms.entity.Category;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductService;
import com.iwc.shop.modules.shop.utils.ShopCategoryUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 商品分类Controller
 * @author Tony Wong
 * @version 2015-04-07
 */
@Controller
@RequestMapping("/${adminPath}/shop/category")
public class AdminCategoryController extends BaseController {

	@Autowired
	private ShopCategoryService categoryService;

	@ModelAttribute("category")
	public ShopCategory get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return categoryService.get(id);
		}
		else {
			return new ShopCategory();
		}
	}

	/**
	 * 分类列表
	 */
	@RequiresPermissions("shop:category:view")
	@RequestMapping(value = {"/list", ""})
	public String list(Model model) {
		List<ShopCategory> list = categoryService.findFirstList();

		model.addAttribute("list", list);
		return "modules/shop/admin/category/list";
	}

	/**
	 * 分类表单
	 */
	@RequiresPermissions("shop:category:edit")
	@RequestMapping(value = "/form")
	public String form(@ModelAttribute("category") ShopCategory category, Model model) {
		if (category.getParent() == null || category.getParent().getId() == null){
			category.setParent(new ShopCategory(ShopCategory.ROOT_ID));
		}

		ShopCategory parent = categoryService.get(category.getParent().getId());
		category.setParent(parent);

		model.addAttribute("category", category);
		return "modules/shop/admin/category/form";
	}

	/**
	 * 保存分类
	 */
	@RequiresPermissions("shop:category:edit")
	@RequestMapping(value = "save")
	public String save(@ModelAttribute("category") ShopCategory category, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		categoryService.save(category);
		addMessage(redirectAttributes, "保存分类'" + category.getName() + "'成功");
		return "redirect:" + adminPath + "/shop/category/";
	}

	/**
	 * 删除分类
	 */
	@RequiresPermissions("shop:category:edit")
	@RequestMapping(value = "/delete")
	public String delete(@ModelAttribute("category") ShopCategory category, RedirectAttributes redirectAttributes) {
		if (ShopCategory.isRoot(category.getId())) {
			addMessage(redirectAttributes, "删除分类失败, 不允许删除顶级分类或编号为空");
		}
		else {
			categoryService.delete(category);
			addMessage(redirectAttributes, "删除分类成功");
		}
		return "redirect:" + adminPath + "/shop/category/";
	}

	/**
	 * 批量修改分类排序
	 */
	@RequiresPermissions("shop:category:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		int len = ids.length;
		ShopCategory[] categories = new ShopCategory[len];
		for (int i = 0; i < len; i++) {
			categories[i] = categoryService.get(ids[i]);
			categories[i].setSort(sorts[i]);
			categoryService.save(categories[i]);
		}
		addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:" + adminPath + "/shop/category/";
	}

	/**
	 * 分类树JSON
	 */
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ShopCategory> list = categoryService.findFirstList();
		for (int i=0; i<list.size(); i++){
			ShopCategory e = list.get(i);
			if (extId == null || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",") == -1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent() != null ? e.getParent().getId() : 0);
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
