import React, { useState } from 'react';
import './PopupAddCategory.css';
import { Modal } from 'antd';
import { meBeSrc } from '../../../../service/meBeSrc';
import Successful from '../../../../components/popupSuccessful/Successful';
import PopupError from '../../../../components/popupError/PopupError';

const PopupAddCategory = ({ show, handleClose }) => {
    const [showModalSuccess, setShowModalSuccess] = useState(false);
    const [showModalError, setShowModalError] = useState(false);


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
        meBeSrc.postCategory(formData)
            .then((res) => {
                setShowModalSuccess(true);
                setTimeout(() => {
                    setShowModalSuccess(false)
                    window.location.reload();
                }, 3000)
            })
            .catch((err) => {
                console.log(err);
                setShowModalError(true);
                setTimeout(() => setShowModalError(false), 3000);
            });


    };
    //-----End-----//


    return (
        <Modal visible={show} onCancel={handleClose} footer={null} width={"auto"} centered>
            <Successful show={showModalSuccess} onHide={() => setShowModalSuccess(false)} message={`Tạo danh mục ${formData.name} thành công`} />
            <PopupError show={showModalError} onHide={() => setShowModalError(false)} message={`Danh mục ${formData.name} đã tồn tại`} />
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
                            required
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
                            required
                        />
                    </div>

                    <div className="form-group_btn">
                        <button className="btn-close_add" type="button" onClick={handleClose}>Đóng</button>
                        <button className="btn-add" type="submit">Lưu</button>
                    </div>
                </form>
            </div>
        </Modal>
    );
};

export default PopupAddCategory;
