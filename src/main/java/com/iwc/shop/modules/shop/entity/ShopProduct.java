/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.shop.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.persistence.DataEntity;
import com.iwc.shop.modules.sys.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品Entity
 * @author Tony Wong
 * @version 2015-04-06
 */
public class ShopProduct extends DataEntity<ShopProduct> {
	
	private static final long serialVersionUID = 1L;

	private ShopCategory category; // 分类
	private List<ShopProductAttribute> attributeList;
	private User user;
	private String name;	// 名称
	private String image;
	private String imageSmall;
	private String imageMedium;
	private String imageLarge;
	private String featured;
	private String featuredImage;
	private String shortDescription;
	private String description;
	private String metaKeywords;
	private String metaDescription;
	private String sort;
	private String isAudit;
	private Float price;
	private Float featuredPrice;
	private String featuredPosition; //checkbox in form, 推荐位，以英文逗号分隔（如： ,1,2,）, 存于数据字典中的类型为: product_featured_position
	private String featuredPositionSort; //推荐位排序
	private String appFeaturedHome;
	private Integer appFeaturedHomeSort;
	private String appFeaturedImage;
	private String appFeaturedTopic;
	private Integer appFeaturedTopicSort;
	private String appLongImage1;
    private String appLongImage2;
    private String appLongImage3;
    private String appLongImage4;
    private String appLongImage5;
    private String type;
	private String status; //状态，0:隐藏，1:显示

	//status
	public static final String STATUS_INACTIVE = "0";
	public static final String STATUS_ACTIVE = "1";

	// featuredPosition
	public static final String FEATURED_POSITION_HOME_DAY = "1"; //首页-每日特价, 存于数据字典中
	public static final String FEATURED_POSITION_HOME_SPECIAL = "2"; //首页-每日特卖, 存于数据字典中
	public static final String FEATURED_POSITION_HOME_LIST = "3"; //首页-推荐列表, 存于数据字典中

	// appFeaturedHome
	public static final String APP_FEATURED_HOME_YES = "1"; //存于数据字典中

    //type 商品类型 在后台字典管理添加
    public static final String TYPE_DRINK = "1"; //饮品
    public static final String TYPE_FOOD = "2"; //小吃
    
	public ShopProduct() {
		super();
		featuredPositionSort = "999";
		appFeaturedHomeSort = 99;
		appFeaturedTopicSort = 99;
	}

	public ShopProduct(String id) {
		super(id);
		featuredPositionSort = "999";
		appFeaturedHomeSort = 99;
		appFeaturedTopicSort = 99;
	}

	/**
	 * 只转化当前字段, 方便给json用
	 * @return
	 */
	public Map<String, Object> toSimpleObj() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", id);
		map.put("name", name);

        if (StringUtils.isNotBlank(image))
		    map.put("image", Global.URL + image);
        else
            map.put("image", "");

        if (StringUtils.isNotBlank(imageSmall))
		    map.put("imageSmall", Global.URL + imageSmall);
        else
            map.put("imageSmall", "");

        if (StringUtils.isNotBlank(imageMedium))
		    map.put("imageMedium", Global.URL + imageMedium);
        else
            map.put("imageMedium", "");

        map.put("shortDescription", shortDescription);
        map.put("price", price);
        map.put("featuredPrice", featuredPrice);
        map.put("type", type);
        map.put("status", status);

        if (StringUtils.isNotBlank(appLongImage1))
            map.put("appLongImage1", Global.URL + appLongImage1);
        else
            map.put("appLongImage1", "");

        if (StringUtils.isNotBlank(appLongImage2))
            map.put("appLongImage2", Global.URL + appLongImage2);
        else
            map.put("appLongImage2", "");

        if (StringUtils.isNotBlank(appLongImage3))
            map.put("appLongImage3", Global.URL + appLongImage3);
        else
            map.put("appLongImage3", "");

        if (StringUtils.isNotBlank(appLongImage3))
            map.put("appLongImage4", Global.URL + appLongImage4);
        else
            map.put("appLongImage4", "");

        if (StringUtils.isNotBlank(appLongImage5))
            map.put("appLongImage5", Global.URL + appLongImage5);
        else
            map.put("appLongImage5", "");

		return map;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getAppFeaturedImage() {
		return appFeaturedImage;
	}

	public void setAppFeaturedImage(String appFeaturedImage) {
		this.appFeaturedImage = appFeaturedImage;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getFeaturedPrice() {
		return featuredPrice;
	}

	public void setFeaturedPrice(Float featuredPrice) {
		this.featuredPrice = featuredPrice;
	}

	public String getFeaturedPositionSort() {
		return featuredPositionSort;
	}

	public void setFeaturedPositionSort(String featuredPositionSort) {
		this.featuredPositionSort = featuredPositionSort;
	}

	public String getFeaturedPosition() {
		return featuredPosition;
	}

	public void setFeaturedPosition(String featuredPosition) {
		this.featuredPosition = featuredPosition;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ShopCategory getCategory() {
		return category;
	}

	public void setCategory(ShopCategory category) {
		this.category = category;
	}

	public String getAppFeaturedTopic() {
		return appFeaturedTopic;
	}

	public void setAppFeaturedTopic(String appFeaturedTopic) {
		this.appFeaturedTopic = appFeaturedTopic;
	}

	public Integer getAppFeaturedTopicSort() {
		return appFeaturedTopicSort;
	}

	public void setAppFeaturedTopicSort(Integer appFeaturedTopicSort) {
		this.appFeaturedTopicSort = appFeaturedTopicSort;
	}

	public List<ShopProductAttribute> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<ShopProductAttribute> attributeList) {
		this.attributeList = attributeList;
	}

	public String getAppLongImage1() {
		return appLongImage1;
	}

	public void setAppLongImage1(String appLongImage1) {
		this.appLongImage1 = appLongImage1;
	}

	public String getAppLongImage2() {
		return appLongImage2;
	}

	public void setAppLongImage2(String appLongImage2) {
		this.appLongImage2 = appLongImage2;
	}

	public String getAppLongImage3() {
		return appLongImage3;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAppLongImage3(String appLongImage3) {
		this.appLongImage3 = appLongImage3;
	}

    public String getAppLongImage4() {
        return appLongImage4;
    }

    public void setAppLongImage4(String appLongImage4) {
        this.appLongImage4 = appLongImage4;
    }

    public String getAppLongImage5() {
        return appLongImage5;
    }

    public void setAppLongImage5(String appLongImage5) {
        this.appLongImage5 = appLongImage5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /*====== 以下是供给表单提交用，不是实体项，看views/modules/shop/admin/product/form.jsp ======*/
	/**
	 * 把featuredPosition字符串转为List给表单checkbox使用
	 * 在form表单会调用
	 */
	public List<String> getFeaturedPositionList() {
		List<String> list = Lists.newArrayList();
		if (StringUtils.isNotBlank(featuredPosition)) {
			for (String s : StringUtils.split(featuredPosition, ",")) {
				list.add(s);
			}
		}
		return list;
	}

	/**
	 * 把featuredPosition在表单的checkbox值转化为featuredPosition字符串
	 * 在form表单会调用
	 */
	public void setFeaturedPositionList(List<String> list) {
		if (list != null && !list.isEmpty()) {
			featuredPosition = "," + StringUtils.join(list, ",") + ",";
		}
	}

	/**
	 * 把attributeList转为attrItemIdList给表单checkbox使用
	 * 在form表单会调用
	 */
	public List<String> getAttrItemIdList() {
		List<String> list = Lists.newArrayList();
		if (attributeList != null) {
			for (ShopProductAttribute attr : attributeList) {
				list.add(attr.getItem().getId());
			}
		}
		return list;
	}

	/**
	 * 把attrItemIdList转为attributeList给实体
	 * 在form表单会调用
	 */
	public void setAttrItemIdList(List<String> attrItemIdList) {
		List<ShopProductAttribute> tmpAttrList = Lists.newArrayList();
		if (attrItemIdList != null && !attrItemIdList.isEmpty()) {
			for (String attrItemId : attrItemIdList) {
				ShopProductAttribute attr = new ShopProductAttribute();
				attr.setItem(new ShopProductAttributeItem(attrItemId));
				attr.setProduct(this);
				tmpAttrList.add(attr);
			}
		}
		attributeList = tmpAttrList;
	}
}


