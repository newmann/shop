/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.web.admin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.persistence.Page;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.common.web.BaseController;
import com.iwc.shop.modules.cms.entity.Article;
import com.iwc.shop.modules.shop.entity.ShopCategory;
import com.iwc.shop.modules.shop.entity.ShopProduct;
import com.iwc.shop.modules.shop.entity.ShopProductAttribute;
import com.iwc.shop.modules.shop.service.ShopCategoryService;
import com.iwc.shop.modules.shop.service.ShopProductAttributeService;
import com.iwc.shop.modules.shop.service.ShopProductService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 商品Controller
 * @author Tony Wong
 * @version 2015-04-07
 */
@Controller
@RequestMapping("/${adminPath}/shop/product")
public class AdminProductController extends BaseController {

	@Autowired
	private ShopCategoryService categoryService;

	@Autowired
	private ShopProductService productService;

	@Autowired
	private ShopProductAttributeService attrService;

	@ModelAttribute("product")
	public ShopProduct get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return productService.get(id);
		}
		else {
			return new ShopProduct();
		}
	}

	/**
	 * 商品首页
	 */
	@RequiresPermissions("shop:product:view")
	 @RequestMapping(value = "")
	 public String index() {
		return "modules/shop/admin/product/index";
	}

	/**
	 * iframe productMenuFrame 分类页
	 */
	@RequiresPermissions("shop:product:view")
	@RequestMapping(value = "/categoryTree")
	public String categoryTree(Model model) {
		model.addAttribute("categoryList", categoryService.findFirstList());
		return "modules/shop/admin/product/categoryTree";
	}

	/**
	 * 商品列表
	 */
	@RequiresPermissions("shop:product:view")
	@RequestMapping(value = "/list")
	public String list(@ModelAttribute("product") ShopProduct product,
					   HttpServletRequest request, HttpServletResponse response, Model model) {
		product.getSqlMap().put("withJoins", "true");
		if (product.getCategory() != null && product.getCategory().getId() != null && product.getCategory().getId() != "")
			product.setCategory(categoryService.get(product.getCategory().getId()));

		Page<ShopProduct> page = new Page<ShopProduct>(request, response);
		page.setOrderBy("a.category_id, a.sort");

		page = productService.findPage(page, product);
		model.addAttribute("page", page);
		return "modules/shop/admin/product/list";
	}

	/**
	 * 商品表单
	 */
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "/form")
	public String form(@ModelAttribute("product") ShopProduct product) {
		if (product.getCategory() != null && StringUtils.isNotBlank(product.getCategory().getId()))
			product.setCategory(categoryService.get(product.getCategory().getId()));
		return "modules/shop/admin/product/form";
	}

	/**
	 * 保存商品
	 */
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "save")
	public String save(@ModelAttribute("product") ShopProduct product, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, product)){
			return form(product);
		}
		productService.save(product);

		// delete and save product attributes
		attrService.deleteByProductId(product.getId());
		for (ShopProductAttribute attr : product.getAttributeList()) {
			attrService.save(attr);
		}

		String categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
		addMessage(redirectAttributes, "保存'" + product.getName() + "'成功");
		return "redirect:" + adminPath + "/shop/product/list?repage&category.id=" + categoryId;
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
}
