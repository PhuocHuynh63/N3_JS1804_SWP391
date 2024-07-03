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

    getProductLastest: () => {
        return https.get(`/product/list/create_at_desc`)
    },

    getProductBySearch: (search) => {
        return https.get(`/product/search?name=${search}`);
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

    getUserById: (user_id) => {
        return https.get(`/user/${user_id}`);
    },

    getUserByUserName: (user_name) => {
        return https.get(`/user/username=${user_name}`);
    },

    userSignUp: (data) => {
        return https.post(`/user/signup`, data)
    },

    changePassword: (user_id, data) => {
        return https.put(`/user/change_password/${user_id}`, null, { params: data });
    },

    updateUserProfile: (user_id, data) => {
        return https.put(`/user/update/${user_id}`, data);
    },

    getTrackingOrder: (userId) => {
        return https.get(`/user/tracking/${userId}`);
    },

    getListOrder: () => {
        return https.get(`/order/list`);
    },

    createOrder: (data) => {
        return https.post(`/order/create_cod`, data);
    },

    createOrderVnPay: (data) => {
        return https.post(`/order/create_vnpay`, data);
    },

    putCancelOrder: (order_id, data) => {
        return https.put(`/order/cancel/orId=${order_id}`, data);
    },

    putStatusOrder: (data) => {
        return https.put(`/order/status`, data);
    },

    createVNPay: (data) => {
        return https.post(`api/payment/create`, data);
    },

    forgotPassword: (email) => {
        return https.post(`/forgot_password/email?email=${email}`);
    }

}
