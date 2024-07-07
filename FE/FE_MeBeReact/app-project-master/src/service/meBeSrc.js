import { https } from "./config"

export const meBeSrc = {
    getInventory: () => {
        return https.get(`/inventory/list`);
    },

    getProduct: () => {
        return https.get(`/product/list`);
    },

    getProductById: (productId) => {
        // console.log(productId)
        return https.get(`/product/${productId}`);
    },

    getProductBySubCategory: (slug) => {
        return https.get(`/product/list_subcate=${slug}`);
    },

    getSubCategoryById: (subCateId) => {
        return https.get(`/sub_category/sub_cateId=${subCateId}`);
    },

    putSubCatory: (subCateId, data) => {
        return https.put(`/sub_category/update_sub_cate/subId=${subCateId}`, data);
    },

    deleteSubCategory: (subcategoryId) => {
        return https.delete(`/sub_category/delete/subId=${subcategoryId}`);
    },

    getProductLastest: () => {
        return https.get(`/product/list/create_at_desc`)
    },

    getProductBySearch: (search) => {
        return https.get(`/product/search?name=${search}`);
    },

    putProduct: (productId, data) => {
        return https.put(`/product/update_product=${productId}`, data);
    },

    postProduct: (data) => {
        return https.post(`/product/create_product`, data);
    },

    deleteProduct: (productId) => {
        return https.delete(`/product/delete_real/product_id=${productId}`);
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

    getCategoryById: (cateId) => {
        return https.get(`/category/cateId=${cateId}`);
    },

    putCategory: (cateId, data) => {
        return https.put(`/category/update_cate/cateId=${cateId}`, data);
    },

    postCategory: (data) => {
        return https.post(`/category/create_cate`, data);
    },

    deleteCategory: (cateId) => {
        return https.delete(`/category/delete_cate/cateId=${cateId}`);
    },

    getListSubCategory: () => {
        return https.get(`/sub_category/list_all`);
    },

    postSubCategory: (data) => {
        return https.post(`/sub_category/create_sub_cate`, data);
    },

    getListUser: () => {
        return https.get(`/user/list`);
    },

    getUserById: (user_id) => {
        return https.get(`/user/${user_id}`);
    },

    getUserByUserName: (user_name) => {
        return https.get(`/user/username=${user_name}`);
    },

    getSearchUserByName: (name) => {
        return https.get(`/user/search_name?name=${name}`);
    },

    userSignUp: (data) => {
        return https.post(`/user/signup`, data)
    },

    changePassword: (user_id, data) => {
        return https.put(`/user/change_password/uId=${user_id}`, null, { params: data });
    },

    updateUserProfile: (user_id, data) => {
        return https.put(`/user/update/${user_id}`, data);
    },

    getTrackingOrder: (userId) => {
        return https.get(`/user/tracking/${userId}`);
    },

    putUserForAdmin: (user_id, data) => {
        return https.put(`/user/update_admin/uId=${user_id}`, data);
    },

    putUserStatus: (user_id, status) => {
        return https.put(`/user/set_status/uId=${user_id}?status=${status}`);
    },

    getListOrder: () => {
        return https.get(`/order/list`);
    },

    getSearchOrderByEmailOrPhone: (email, phoneNumber) => {
        return https.get(`/order/search_order?email=${email || ''}&phoneNumber=${phoneNumber || ''}`);
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

    getOrderDetail: (orderId) => {
        return https.get(`/order_details/list/orderId=${orderId}`);
    },

    createVNPay: (data) => {
        return https.post(`api/payment/create`, data);
    },

    forgotPassword: (email) => {
        return https.post(`/forgot_password/email?email=${email}`);
    },

    getAddressByUserId: (user_id) => {
        return https.get(`/address/list/${user_id}`);
    },

    createAddress: (user_id, data) => {
        return https.post(`/address/create/${user_id}`, data);
    },

    updateAddress: (address_id, data) => {
        return https.put(`/address/update/${address_id}`, data);
    },

    deleteAddress: (address_id) => {
        return https.delete(`/address/delete/${address_id}`);
    },

    setDefaultAddress: (address_id, defaultValue) => {
        return https.put(`/address/update/default/${address_id}?defaultValue=${defaultValue}`);
    },

    setAvatar: (userId, data) => {
        return https.put(`/user/update_avatar/${userId}`, data);
    },

    getUserByEmail: (email) => {
        return https.get(`/user/check_email?email=${email}`);
    },

    updateGuestToUser: (userId, data) => {
        return https.put(`/user/signup_guest/${userId}`, data);
    },

    sendOtp:(email) =>{
        return https.post(`/user/send_otp_mail?email=${email}`);
    },

    checkOtp:(otp) => {
        return https.post(`/user/check_otp?otp=${otp}`);
    },

}
