import React, { useState } from 'react';
import './PopupAddCategory.css';
import { Modal } from 'antd';

const PopupAddCategory = ({ show, handleClose }) => {

    /**
     * Form data
     */
    const [formData, setFormData] = useState({
        name: '',
        slug: '',
    });
    //-----End-----//


    /**
     * Handle change input
     * @param {*} e 
     */
    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.id]: e.target.value,
        });
    };
    //-----End-----//


    /**
     * Handle form submission
     * @param {*} e 
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        console.log({
            name: formData.name,
            slug: formData.slug,
        });
    };
    //-----End-----//


    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
            <div className="admin-category-add">
                <h1>Thêm danh mục mới</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group name">
                        <label htmlFor="name">Tên danh mục</label>
                        <input
                            type="text"
                            id="name"
                            value={formData.name}
                            onChange={handleChange}
                            placeholder='Ví dụ: Ăm dặm'
                        />
                    </div>


                    <div className="form-group slug">
                        <label htmlFor="slug">Đường dẫn</label>
                        <input
                            type="text"
                            id="slug"
                            value={formData.slug}
                            onChange={handleChange}
                            placeholder='am-dam'
                        />
                    </div>

                    <div className="form-group_btn">
                        <button className="btn-close_update" type="button" onClick={handleClose}>Đóng</button>
                        <button className="btn-update" type="submit">Lưu</button>
                    </div>
                </form>
            </div>
        </Modal>
    );
};

export default PopupAddCategory;
