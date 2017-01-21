/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.TreeEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品分类Entity
 * @author Tony Wong
 * @version 2015-04-06
 */
public class ShopCategory extends TreeEntity<ShopCategory> {

	private static final long serialVersionUID = 1L;

	private String image; 	// 分类图片
	private String imageSmall;
	private String imageMedium;
	private String imageLarge;
	private String featured;
	private String featuredImage;
	private String appFeaturedHome;
	private Integer appFeaturedHomeSort;
	private String href; 	// 超链接地址，优先级“高”
	private String hrefTarget; 	// 超链接打开的目标窗口，新窗口打开，请填写：“_blank”, 目标（_blank、_self、_parent、_top）
	private String shortDescription; 	// 简短描述，填写有助于搜索引擎优化
	private String metaKeywords; 	// 关键字，填写有助于搜索引擎优化
	private String metaDescription; // 关键描述，填写有助于搜索引擎优化
	private String isAudit;	// 是否需要审核

	private List<ShopCategory> childList = Lists.newArrayList(); 	// 拥有子分类列表

	// 顶级分类ID
	public static final String ROOT_ID = "1";

	// appFeaturedHome
	public static final String APP_FEATURED_HOME_NO = "0";
	public static final String APP_FEATURED_HOME_YES = "1";

	// hrefTarget
	public static final String HREF_TARGET_DEFAULT = "_blank";

	public ShopCategory(){
		super();
		sort = 999;
		appFeaturedHomeSort = 99;
		isAudit = Global.NO;
	}

	public ShopCategory(String id){
		super(id);
		sort = 999;
		appFeaturedHomeSort = 99;
		isAudit = Global.NO;
	}

    public void preInsert(){
        super.preInsert();
        if (hrefTarget == null)
            hrefTarget = HREF_TARGET_DEFAULT;
    }

    /**
     * 只转化当前字段, 方便给json用
     * @return
     */
    public Map<String, Object> toSimpleObj() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", id);
        map.put("name", name);
        return map;
    }
	
//	@JsonBackReference
//	@NotNull
	public ShopCategory getParent() {
		return parent;
	}

	public void setParent(ShopCategory parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Length(min=0, max=255)
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Length(min=1, max=1)
	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageMedium() {
		return imageMedium;
	}

	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}

	public String getImageLarge() {
		return imageLarge;
	}

	public void setImageLarge(String imageLarge) {
		this.imageLarge = imageLarge;
	}

	public String getFeatured() {
		return featured;
	}

	public void setFeatured(String featured) {
		this.featured = featured;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}

	public String getAppFeaturedHome() {
		return appFeaturedHome;
	}

	public void setAppFeaturedHome(String appFeaturedHome) {
		this.appFeaturedHome = appFeaturedHome;
	}

	public Integer getAppFeaturedHomeSort() {
		return appFeaturedHomeSort;
	}

	public void setAppFeaturedHomeSort(Integer appFeaturedHomeSort) {
		this.appFeaturedHomeSort = appFeaturedHomeSort;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public List<ShopCategory> getChildList() {
		return childList;
	}

	public void setChildList(List<ShopCategory> childList) {
		this.childList = childList;
	}



	/*** other functions ***/

	public static void sortList(List<ShopCategory> list, List<ShopCategory> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			ShopCategory e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					ShopCategory child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}

	public String getIds() {
		return (this.getParentIds() !=null ? this.getParentIds().replaceAll(",", " ") : "")
				+ (this.getId() != null ? this.getId() : "");
	}

	/**
	 * 是否顶级分类？
	 */
	public boolean isRoot(){
		return isRoot(this.id);
	}

	/**
	 * 是否顶级分类？
	 */
	public static boolean isRoot(String id){
		return id != null && id.equals(ROOT_ID);
	}
}