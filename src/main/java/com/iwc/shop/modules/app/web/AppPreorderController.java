/**
 * Copyright &copy; 2015 <a href="http://www.iwantclick.com">iWantClick</a>iwc.shop All rights reserved.
 */
package com.iwc.shop.modules.app.web;

import com.google.common.collect.Maps;
import com.iwc.shop.common.config.Global;
import com.iwc.shop.common.utils.IpAddrUtils;
import com.iwc.shop.common.utils.StringUtils;
import com.iwc.shop.modules.shop.entity.*;
import com.iwc.shop.modules.shop.service.*;
import com.iwc.shop.modules.shop.utils.ShopProductUtils;
import com.iwc.shop.modules.sys.entity.User;
import com.iwc.shop.modules.sys.service.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预购订单Controller
 * @author Tony Wong
 * @version 2015-07-14
 */
@Controller
@RequestMapping("/app/preorder")
public class AppPreorderController extends AppBaseController {

    @Autowired
    private PreorderService preorderService;

    @Autowired
    private PreorderItemService preorderItemService;

	@Autowired
	private PreorderItemService itemService;

	@Autowired
	private PreorderItemAttributeService attributeService;

	@Autowired
	private CartItemService cartItemService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponUserService couponUserService;

	/**
	 * 预购订单页面
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}")
	public String view(@PathVariable String id,
					   HttpServletRequest request, HttpServletResponse response, Model model) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        String userId = getUserId(request);

		Preorder preorder = preorderService.get(id);
        if (preorder == null || preorder.getUser() == null
                || StringUtils.isBlank(preorder.getUser().getId())) {
            result = false;
            message = "预购订单(ID:" + preorder.getId() + ")不存在";
            return renderString(response, result, message, data);
        }

        Map<String, Object> oPreorder = preorder.toSimpleObj();
        Map<String, Object> oAddress = addressService.getDefaultByUserId4SimpleObj(userId);
        List<Map<String, Object>> oPreorderItemList = itemService.findByPreorderId4Json(preorder);

        result = true;
        message = "";
        data.put("preorder", oPreorder);
        data.put("address", oAddress);
        data.put("preorderItemList", oPreorderItemList);
        return renderString(response, result, message, data);
	}

	/**
	 * 通过购物车(is_selected字段)创建预备订单
	 */
	@RequestMapping(value="/add", method= RequestMethod.POST)
	public String add(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

		boolean result;
        int resultCode;
		String message;
		Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> oPreorder = Maps.newHashMap();

        if(STOP_ORDER) {
            result = false;
            message = STOP_ORDER_LABEL;
            resultCode = ResultCode.StopOrder;
            return renderString(response, result, message, data);
        }

        String userId = getUserId(request);
        User user = getUser(request);
        List<CartItem> cartItemList = cartItemService.findByUserId(userId, Global.YES);

        //find selected cart items
		if (cartItemList != null && !cartItemList.isEmpty()) {
            Float totalPrice = (float) 0.0;
            Integer totalCount = 0;

			// save preorder
			Preorder preorder = new Preorder();
            preorder.setIp(IpAddrUtils.getIpAddr(request));
			preorder.setTotalCount(totalCount);
			preorder.setTotalPrice(totalPrice);
            preorder.setOriginTotalPrice(totalPrice);
            preorder.setUser(new User(userId));

            //如果是用户最近用过了在线支付则设置，否则为null
            if (Order.isValidOpPayType(user.getLatestPayType())) {
                preorder.setPayType(user.getLatestPayType());
                preorder.setRoughPayType(Order.payTypeToRoughPayType(user.getLatestPayType()));
            }

            preorder.setCouponUserTotalPrice(Float.valueOf(0));
			preorderService.save(preorder);

			for (CartItem cartItem : cartItemList) {
				ShopProduct product = ShopProductUtils.getProduct(cartItem.getProduct().getId());
				// if product not exist, remove cartItem
				if (product == null) {
					cartItemService.delete(cartItem);
					continue;
				}

				//calculate total
                Float price = product.getPrice();
                if (product.getFeaturedPrice() != null)
                    price = product.getFeaturedPrice();
                Integer count = cartItem.getCount();
				Float subtotalPrice = price * count;

				//save PreorderItem
				PreorderItem item = new PreorderItem();
				item.setCartItem(cartItem);
				item.setProduct(product);
				item.setImage(product.getImage());
				item.setImageSmall(product.getImageSmall());
				item.setName(product.getName());
				item.setPrice(price);
				item.setSubtotalPrice(subtotalPrice);
				item.setCount(count);
				item.setPreorder(preorder);
                item.setType(product.getType());
				itemService.save(item);

				//save PreorderItem attributes
				if (cartItem.getAttributeList() != null) {
					for (CartItemAttribute attr : cartItem.getAttributeList()) {
                        Float attrPrice = attr.getAttributeItemValue().getPrice();
						PreorderItemAttribute itemAttr = new PreorderItemAttribute();
						itemAttr.setItem(item);
                        itemAttr.setAttributeItemId(attr.getAttributeItem().getId());
                        itemAttr.setAttributeItemName(attr.getAttributeItem().getName());
                        itemAttr.setAttributeItemPrintName(attr.getAttributeItem().getPrintName());
                        itemAttr.setAttributeItemSort(attr.getAttributeItem().getSort());
                        itemAttr.setAttributeItemValueId(attr.getAttributeItemValue().getId());
                        itemAttr.setAttributeItemValueName(attr.getAttributeItemValue().getName());
                        itemAttr.setAttributeItemValuePrintName(attr.getAttributeItemValue().getPrintName());
                        itemAttr.setAttributeItemValueSort(attr.getAttributeItemValue().getSort());
                        itemAttr.setAttributeItemValuePrice(attr.getAttributeItemValue().getPrice());
                        itemAttr.setAttributeItemValueIsStandard(attr.getAttributeItemValue().getIsStandard());
                        itemAttr.setAttributeIdstring(attr.getAttributeIdstring());
						attributeService.save(itemAttr);

                        price += attrPrice;
					}
				}

				// save price, subtotal again
                item.setPrice(price);
				item.setSubtotalPrice(price * count);
				itemService.save(item);

				totalCount += count;
				totalPrice += item.getSubtotalPrice();
			}

			//save totalPrice, totalCount
            preorder.setTotalPrice(totalPrice);
			preorder.setTotalCount(totalCount);
            preorder.setOriginTotalPrice(totalPrice);
            //设置最少支付总额
            if (preorder.getTotalPrice() < Order.MIN_TOTAL_PRICE) {
                preorder.setTotalPrice(Order.MIN_TOTAL_PRICE);
                preorder.setMinTotalPriceLabel(Order.MIN_TOTAL_PRICE_LABEL);
            }
            preorderService.save(preorder);

            result = true;
            resultCode = ResultCode.Success;
            message = "商品已加入预购订单";
            oPreorder.put("id", preorder.getId());
		}
        else {
            result = false;
            resultCode = ResultCode.Failure;
            message = "购物车没有选择的产品";
        }

        data.put("preorder", oPreorder);
		return renderString(response, result, resultCode, message, data);
	}

    @RequestMapping(value="/select-useful-coupon")
    public String selectUsefulCoupon(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        String userId = request.getParameter("userId");
        String preorderId = request.getParameter("preorderId");
        String couponUserId = request.getParameter("couponUserId");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        Map<String, Object> oCouponUser = Maps.newHashMap();
        Map<String, Object> oPreorder;
        Date nowDate = new Date();

        Preorder preorder = preorderService.getWithUserId(preorderId, userId);

        if (preorder == null) {
            result = false;
            message = "预购订单(ID:" + preorderId + ")不存在";
            return renderString(response, result, message, data);
        }

        CouponUser couponUser;
        //不使用优惠券
        if (StringUtils.isBlank(couponUserId)) {
            couponUser = new CouponUser();
            preorder.setCouponUser(couponUser);
            preorder.setCouponUserTotalPrice(0f);
            preorder.setTotalPrice(preorder.getOriginTotalPrice());
            preorder.setMinTotalPriceLabel(null);
        } else { //使用优惠券
            couponUser = couponUserService.get(couponUserId);

            if (couponUser == null || couponUser.getUser() == null
                    || StringUtils.isBlank(couponUser.getUser().getId())
                    || !couponUser.getUser().getId().equals(userId)) {
                result = false;
                message = "优惠券(ID:" + couponUserId + ")不存在";
                return renderString(response, result, message, data);
            }

            if (Global.YES.equals(couponUser.getHasUsed())) {
                result = false;
                message = "优惠券(ID:" + couponUserId + ")已经被使用过了";
                return renderString(response, result, message, data);
            }

            if (couponUser.getEndDate() != null && nowDate.getTime() > couponUser.getEndDate().getTime()) {
                result = false;
                message = "优惠券(ID:" + couponUserId + ")已过期";
                return renderString(response, result, message, data);
            }

            if (Order.PAY_TYPE_CASH.equals(preorder.getPayType())) {
                result = false;
                message = "现金支付不能使用优惠券";
                return renderString(response, result, message, data);
            }

            //如果是新用户注册的买一送一类型的优惠券，则计算优惠价格
            float couponUserTotalPrice = 0;
            if (Coupon.ID_REGISTER1.equals(couponUser.getCouponId())) {
                int totalCount = preorder.getTotalCount();
                if (totalCount < 2) {
                    result = false;
                    message = "必须购买两个以上的商品";
                    return renderString(response, result, message, data);
                }

                String orderBy = "a.price ASC";
                List<PreorderItem> preorderItemList = preorderItemService.findByPreorderIdNoAttr(preorderId, orderBy);
                int NeednotPayCount = totalCount / 2;
                int count = 0;
                for (PreorderItem preorderItem : preorderItemList) {
                    for (int j=0; j<preorderItem.getCount(); j++) {
                        if (count < NeednotPayCount) {
                            couponUserTotalPrice += preorderItem.getPrice();
                            count++;
                        } else {
                            break;
                        }
                    }
                }
            } else if (couponUser.getPrice() > 0) {
                couponUserTotalPrice = couponUser.getPrice();
            }
            preorder.setCouponUser(couponUser);
            if (preorder.getOriginTotalPrice() >= couponUserTotalPrice) {
                preorder.setTotalPrice(preorder.getOriginTotalPrice() - couponUserTotalPrice);
                preorder.setCouponUserTotalPrice(couponUserTotalPrice);
            } else {
                preorder.setTotalPrice(Float.valueOf(0));
                preorder.setCouponUserTotalPrice(preorder.getTotalPrice());
            }
            oCouponUser = couponUser.toSimpleObjWithCal();
        }

        //设置最少支付总额
        if (preorder.getTotalPrice() < Order.MIN_TOTAL_PRICE) {
            preorder.setTotalPrice(Order.MIN_TOTAL_PRICE);
            preorder.setMinTotalPriceLabel(Order.MIN_TOTAL_PRICE_LABEL);
        }
        preorderService.save(preorder);

        oPreorder = preorder.toSimpleObj();
        result = true;
        message = "";
        data.put("couponUser", oCouponUser);
        data.put("preorder", oPreorder);

        return renderString(response, result, message, data);
    }

    @RequestMapping(value="/list-useful-coupon")
    public String listUsefulCoupon(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        String userId = request.getParameter("userId");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();
        List<Map<String, Object>> oCouponUserList;

        List<CouponUser> couponUserList = couponUserService.findUsefulCoupon(userId);
        oCouponUserList = couponUserService.listToSimpleObjWithCal(couponUserList);

        result = true;
        message = "";
        data.put("couponUserList", oCouponUserList);
        return renderString(response, result, message, data);
    }

    @RequestMapping("/select-pay-type")
    public String selectPayType(HttpServletRequest request, HttpServletResponse response) {
        if (!isLoggedIn(request)) {
            return renderNotLoggedIn(response);
        }

        String userId = request.getParameter("userId");
        String preorderId = request.getParameter("preorderId");
        String payType = request.getParameter("payType");

        boolean result;
        String message;
        Map<String, Object> data = Maps.newHashMap();

        if (!Order.isValidPayType(payType)) {
            result = false;
            message = "支付方式(PayType:" + payType + ")不正确";
            return renderString(response, result, message, data);
        }

        Preorder preorder = preorderService.getWithUserId(preorderId, userId);

        if (preorder == null) {
            result = false;
            message = "预购订单(ID:" + preorderId + ")不存在";
            return renderString(response, result, message, data);
        }

        if (Order.PAY_TYPE_WX.equals(payType) || Order.PAY_TYPE_ALIPAY.equals(payType)) { //在线支付可以使用优惠券
            preorder.setPayType(payType);
            preorder.setRoughPayType(Order.ROUGH_PAY_TYPE_OP);
        } else if (Order.PAY_TYPE_CASH.equals(payType)){ //货到付款不能使用优惠券
            preorder.setPayType(payType);
            preorder.setRoughPayType(Order.ROUGH_PAY_TYPE_CASH);
            preorder.setTotalPrice(preorder.getOriginTotalPrice());
            preorder.setCouponUser(new CouponUser());
            preorder.setCouponUserTotalPrice(Float.valueOf(0));
        } else {
            result = false;
            message = "支付方式(PayType:" + payType + ")不存在";
            return renderString(response, result, message, data);
        }

        preorderService.save(preorder);

        Map<String, Object> oPreorder = preorder.toSimpleObj();
        result = true;
        message = "";
        data.put("preorder", oPreorder);

        return renderString(response, result, message, data);
    }
}
