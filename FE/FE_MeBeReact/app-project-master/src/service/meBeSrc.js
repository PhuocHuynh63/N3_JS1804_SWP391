import { https } from "./config"

export const meBeSrc = {
    getInventory: () => {
        return https.get(`/inventory/list`);
    },

    getProduct: () => {
        return https.get(`/product/list`);
    },

    getProductDetail: (productId) => {
        // console.log(productId)
        return https.get(`/product/${productId}`);
    },

    getProductBySubCategory: (slug) => {
        return https.get(`/product/list_subcate=${slug}`);
    },

    getListCategory: () => {
        return https.get(`/category/list`);
    },

    getCategoryByName: (name) => {
        return https.get(`/category/name=${name}`);
    },

    getCategoryBySlug: (slug) => {
        return https.get(`/category/slug=${slug}`);
    },

    getListSubCategory: () => {
        return https.get(`/sub_category/list_all`);
    },

    getProductLastest: () => {
        return https.get(`/product/list/creat_at_desc`);
    },

    getUserById: (user_id) => {
        return https.get(`/user/${user_id}`);
    },

    getUserByUserName: (user_name) => {
        return https.get(`/user/username=${user_name}`);
    },

    updateUserProfile: (user_id, data) => {
        return https.put(`/user/update/${user_id}`, data);
    },

    createOrder: (data) => {
        return https.post(`/order/create`, data);
    },
}
