import React from "react";
import { Button, Modal } from "react-bootstrap";

const PopupError = ({ show, onHide, message }) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Body className="text-center">
                <div className="error-icon">
                    <img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1720256949/z5607751146663_d034ee3d84a21e12c99d7e3a8d90c1df_csloch.gif" alt="success" />
                </div>
                <h4 className="mt-3">Lỗi!</h4>
                <p>{message}</p>
                <Button variant="primary" onClick={onHide}>Đóng!</Button>
            </Modal.Body>
        </Modal>
    );
}

export default PopupError;