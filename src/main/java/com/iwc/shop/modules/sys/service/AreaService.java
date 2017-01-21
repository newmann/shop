/**
 * Copyright &copy; 2012-2014 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwc.shop.common.service.TreeService;
import com.iwc.shop.modules.sys.entity.Area;
import com.iwc.shop.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwc.shop.modules.sys.dao.AreaDao;

/**
 * 区域Service
 * @author Tony Wong
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	/**
	 * 获取单条数据, 新的接口gets，因为gets已经存在
	 * @param id
	 * @return
	 */
	public Area gets(String id) {
		return dao.gets(id);
	}

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	public List<Area> findByParentId(String parentId) {
		Area area = new Area();
		Area parent = new Area(parentId);
		area.setParent(parent);
		return dao.findLists(area);
	}

    /**
     * Get area selector titles and items
     * @param area 被选择目标区域
     * @return Map<String, List> = {
     *     titles = List,
     *     items = List
     * }
     */
    public Map<String,Object> getAreaSelector(Area area) {
        Map<String,Object> areaSelector = Maps.newHashMap();
        List<String> titles = Lists.newArrayList();
        List<List<Area>> items = Lists.newArrayList();

        // get items of area selector
        String[] areaIds = area.getPathIds().split("/");
        String[] areaNames = area.getPathNames().split("/");
        for (int i = 0; i < areaIds.length - 1; i++) {
            titles.add(areaNames[i + 1]);
            items.add(findByParentId(areaIds[i]));
        }

        areaSelector.put("titles", titles);
        areaSelector.put("items", items);
        return areaSelector;
    }
}
