package com.ebrightmoon.ui.data;

import java.io.Serializable;
import java.util.List;

/**
 * Time: 2019/6/13
 * Author:wyy
 * Description:
 */
public class ProductBean implements Serializable {
    List<SkuModelListBean> products;

    public List<SkuModelListBean> getProducts() {
        return products;
    }

    public void setProducts(List<SkuModelListBean> products) {
        this.products = products;
    }
}
