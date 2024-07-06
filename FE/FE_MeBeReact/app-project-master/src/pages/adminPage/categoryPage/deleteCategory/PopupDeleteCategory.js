import React, { useState } from 'react';
import { Modal } from 'antd';
import PopupConfirm from '../../../../components/popupConfirm/PopupConfirm';
import { meBeSrc } from '../../../../service/meBeSrc';
import Successful from '../../../../components/popupSuccessful/Successful';
import PopupError from '../../../../components/popupError/PopupError';

const PopupDeleteCategory = ({ show, handleClose, categoryId, length }) => {
    const [showModalConfirm, setShowModalConfirm] = useState(false);
    const [showModalSuccess, setShowModalSuccess] = useState(false);
    const [showModalError, setShowModalError] = useState(false);

    /**
     * 
     */
    const checkLength = () => {
        if (length > 0) {
            setShowModalError(true);
            setTimeout(() => setShowModalError(false), 3000);
        } else {
            handleDelete()
        }
    }


    /**
     * Delete category
     */
    const handleDelete = () => {
        meBeSrc.deleteCategory(categoryId)
            .then((res) => {
                setShowModalConfirm(false);
                setShowModalSuccess(true);
                setTimeout(() => {
                    setShowModalSuccess(false);
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
        <>
            <PopupConfirm show={show} onHide={handleClose} message={"Bạn có chắc chắn xóa không?"} action={() => handleDelete()} textConfirm={"Xóa"} />
            <Successful show={showModalSuccess} onHide={() => setShowModalSuccess(false)} message={"Xoá thành công"} />
            <PopupError show={showModalError} onHide={() => setShowModalError(false)} message={""} />
        </>
    );
};

export default PopupDeleteCategory;
