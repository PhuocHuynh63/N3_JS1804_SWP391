import React from "react";
import "./TrackingPage.css";
import { NavLink } from "react-router-dom";

export default function TrackingPage() {
    // const [product, setProduct] = 

    return (
        <div className="tracking-container">
            <div className="tracking-header">
                <span>Tất cả</span>
                <span>Chờ thanh toán</span>
                <span>Vận chuyển</span>
                <span>Chờ giao hàng</span>
                <span>Hoàn thành</span>
                <span>Đã hủy</span>
            </div>

            <div className="tracking-body">
                <div className="tracking-body_item">
                    {/* <NavLink to={`/product/${}`}> */}
                    <div className="left">
                        <img src="https://www.wheystore.vn/images/products/2023/11/17/large/1_1700184620.jpg.webp" alt="item" />
                        <div className="content">
                            <p>Amix Gold Isolate Whey Protein 5lbs</p>
                            <p>Số lượng: x1</p>
                        </div>
                    </div>
                    <div className="right">
                        <p>200.000đ</p>
                    </div>
                    {/* </NavLink> */}
                </div>

                <div className="tracking-body_bot">
                    <div className="total">
                        <span>Thành tiền: </span>
                        <span>500.000đ</span>
                    </div>
                    <div className="btn">
                        <NavLink to={"/account/tracking/success"}>Giao hàng thành công</NavLink>
                        <button className="btn-buy_again">Mua lại</button>
                    </div>
                </div>
            </div>

        </div >
    )
}