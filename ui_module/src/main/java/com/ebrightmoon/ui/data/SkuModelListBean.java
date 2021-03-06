package com.ebrightmoon.ui.data;

import java.io.Serializable;
import java.util.List;

public class SkuModelListBean implements Serializable {

    /**
     * arrivalCycle : 2
     * buyyerCount : 6
     * cartId : 47559
     * categoryDiscountRate : 1
     * categoryId : 39
     * color : 黑色
     * costPrice : 76.83
     * fare : 100.000000
     * high : 15.199999809265137
     * length : 38.0
     * originalPrice : 107.00
     * platDiscountRate : 1
     * productCode : 01.15.11.055980
     * productName : 我是商品名称
     * productPic : https://test-fsyuncai.oss-cn-beijing.aliyuncs.com/image/大桥切割片(不锈钢绿片).png
     * salePrice : 107.00
     * salesManagerId : 0
     * skuCode : 91000002002
     * skuId : 2125
     * skuInfo : 152mm*300mm
     * skuName : 博深BOSUN 速锐经济型晶钻水钻头
     * skuWeight : 4.28
     * specifications : 152mm*300mm
     * spuId : 2
     * status : 1
     * stockNum : 9
     * unitName : 支
     * virtualStock : 10
     * warehouseId : 1
     * width : 15.199999809265137
     */
    private String id;
    private String buyyerCount;
    private String cartId;
//    private String categoryDiscountRate;
//    private String categoryId;
    private String color;

//    private String arrivalCycle;
//    private String costPrice;
//    private String fare;
//    private String high;
//    private String length;
//    private String originalPrice;
//    private String platDiscountRate;
//    private String productCode;
//    private String productName;
    private String productPic;
    private String salePrice;
//    private String salesManagerId;
    private String skuCode;
    private String skuId;
//    private String skuInfo;
    private String skuName;
//    private String skuWeight;
    private String specifications;
    private String spuId;
//    private String status;
//    private String stockNum;
    private String unitName; //商品单位  ; 个、只，箱，困
//    private String virtualStock;
    private String warehouseId;
//    private String width;

    private int activityId;  //促销活动ID
    private int warehouseRealStock;  //中心仓库存数量
    private int storeRealStock;  //门店库存数量
    private String promotionPrice;  //促销优惠金额

    private int dangerous;  //危险品标志  1:危险品  0：不是危险品
    /**
     * // 未参与
     NOT_JOIN,
     //单品
     SINGLE_PRODUCT,
     //满金额
     FULL_MONEY,
     //满数量
     FULL_NUM;
     */
    private String joinPromotionTag;//10-参与活动；20-不参与活动
    private String promotionType;  //促销类型
   private List<PayModel> promotionPayModel; //支持的支付方式


    public int getWarehouseRealStock() {
        return warehouseRealStock;
    }

    public void setWarehouseRealStock(int warehouseRealStock) {
        this.warehouseRealStock = warehouseRealStock;
    }

    public int getStoreRealStock() {
        return storeRealStock;
    }

    public void setStoreRealStock(int storeRealStock) {
        this.storeRealStock = storeRealStock;
    }

    public String getJoinPromotionTag() {
        return joinPromotionTag;
    }

    public void setJoinPromotionTag(String joinPromotionTag) {
        this.joinPromotionTag = joinPromotionTag;
    }

    public List<PayModel> getPromotionPayModel() {
        return promotionPayModel;
    }

    public void setPromotionPayModel(List<PayModel> promotionPayModel) {
        this.promotionPayModel = promotionPayModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDangerous() {
        return dangerous;
    }

    public void setDangerous(int dangerous) {
        this.dangerous = dangerous;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

//    public String getArrivalCycle() {
//        return arrivalCycle;
//    }

//    public void setArrivalCycle(String arrivalCycle) {
//        this.arrivalCycle = arrivalCycle;
//    }

    public String getBuyyerCount() {
        return buyyerCount;
    }

    public void setBuyyerCount(String buyyerCount) {
        this.buyyerCount = buyyerCount;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

//    public String getCategoryDiscountRate() {
//        return categoryDiscountRate;
//    }

//    public void setCategoryDiscountRate(String categoryDiscountRate) {
//        this.categoryDiscountRate = categoryDiscountRate;
//    }

//    public String getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(String categoryId) {
//        this.categoryId = categoryId;
//    }

    public String getColor() {
        return color == null ? "" : color;
    }

    public void setColor(String color) {
        this.color = color;
    }

//    public String getCostPrice() {
//        return costPrice;
//    }

//    public void setCostPrice(String costPrice) {
//        this.costPrice = costPrice;
//    }

//    public String getFare() {
//        return fare;
//    }

//    public void setFare(String fare) {
//        this.fare = fare;
//    }

//    public String getHigh() {
//        return high;
//    }

//    public void setHigh(String high) {
//        this.high = high;
//    }

//    public String getLength() {
//        return length;
//    }

//    public void setLength(String length) {
//        this.length = length;
//    }

//    public String getOriginalPrice() {
//        return originalPrice;
//    }

//    public void setOriginalPrice(String originalPrice) {
//        this.originalPrice = originalPrice;
//    }

//    public String getPlatDiscountRate() {
//        return platDiscountRate;
//    }

//    public void setPlatDiscountRate(String platDiscountRate) {
//        this.platDiscountRate = platDiscountRate;
//    }

//    public String getProductCode() {
//        return productCode;
//    }

//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }

//    public String getProductName() {
//        return productName;
//    }

//    public void setProductName(String productName) {
//        this.productName = productName;
//    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

//    public String getSalesManagerId() {
//        return salesManagerId;
//    }

//    public void setSalesManagerId(String salesManagerId) {
//        this.salesManagerId = salesManagerId;
//    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

//    public String getSkuInfo() {
//        return skuInfo;
//    }

//    public void setSkuInfo(String skuInfo) {
//        this.skuInfo = skuInfo;
//    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

//    public String getSkuWeight() {
//        return skuWeight;
//    }

//    public void setSkuWeight(String skuWeight) {
//        this.skuWeight = skuWeight;
//    }

    public String getSpecifications() {
        return specifications == null ? "" : specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

//    public String getStatus() {
//        return status;
//    }

//    public void setStatus(String status) {
//        this.status = status;
//    }

//    public String getStockNum() {
//        return stockNum;
//    }

//    public void setStockNum(String stockNum) {
//        this.stockNum = stockNum;
//    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

//    public String getVirtualStock() {
//        return virtualStock;
//    }

//    public void setVirtualStock(String virtualStock) {
//        this.virtualStock = virtualStock;
//    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

//    public String getWidth() {
//        return width;
//    }

//    public void setWidth(String width) {
//        this.width = width;
//    }

    public static class PayModel  implements  Serializable{
        /**
         * payName : 在线支付
         * payType : 1001
         */

        private String payName;
        private String payType;

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }
    }
}
