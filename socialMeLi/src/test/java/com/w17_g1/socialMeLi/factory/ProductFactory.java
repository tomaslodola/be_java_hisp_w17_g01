package com.w17_g1.socialMeLi.factory;

import com.w17_g1.socialMeLi.model.Product;

public class ProductFactory {

    public static Product getProduct(Integer productId){
        return new Product(productId,"Nombre","Tipo","Marca","Color","Notas");
    }

}
