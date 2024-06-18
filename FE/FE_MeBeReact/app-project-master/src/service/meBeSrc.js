import { https } from "./config"

export const meBeSrc = {
    getProduct: () => {
        return https.get(`/product`);
    },

    getProductDetail: (productId) => {
        // console.log(productId)
        return https.get(`/product/${productId}`);
    },

    getListCategory: () => {
        return https.get(`/category/list`);
    },

    getCategoryByName: (name) => {
        return https.get(`/category/${name}`);
    },

    getListSubCategory: () => {
        return https.get(`/sub_category/list_all`);
    },

}
