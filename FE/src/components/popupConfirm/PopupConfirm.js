import React from "react";
import { Button, Modal } from "react-bootstrap";

const PopupConfirm = ({ show, onHide, message, action, textConfirm }) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Body className="text-center">
                <div className="error-icon">
                    <img src="https://res.cloudinary.com/dwyzqcunj/image/upload/v1720285160/z5609014329084_512e5972609f4ebe3de9c854c3777e8b_miybmq.gif" alt="success" />
                </div>
                <h4 className="mt-3">Chú ý!</h4>
                <p>{message}</p>
                <Button variant="primary" onClick={onHide}>Đóng!</Button>
                <Button variant="primary" onClick={action}>{textConfirm}</Button>
            </Modal.Body>
        </Modal>
    );
}

export default PopupConfirm;