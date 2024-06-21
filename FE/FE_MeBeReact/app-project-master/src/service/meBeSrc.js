import { https } from "./config"

export const meBeSrc = {
    getProduct: () => {
        return https.get(`/product/list`);
    },

    getProductDetail: (productId) => {
        // console.log(productId)
        return https.get(`/product/${productId}`);
    },

    getProductBySubCategory: (subCategoryId) => {
        return https.get(`/product/list_subcate=${subCategoryId}`);
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
