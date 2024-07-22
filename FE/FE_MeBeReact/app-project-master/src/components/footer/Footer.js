import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import './Footer.css'

export default function Footer() {
    return (
        <footer>
            <div className="container">
                <div className="row">
                    <div className="col-md-6">
                        <h3>GIỚI THIỆU</h3>
                        <p>
                            Chúng tôi là một nhóm 3 thuộc lớp JS1802
                        </p>
                    </div>
                    <div className="col-md-6">
                        <h3>LIÊN HỆ VỚI CHÚNG TÔI</h3>
                        <p>
                            <strong>Address:</strong> Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh 700000, Vietnam
                        </p>
                        <p>
                            <strong>Phone:</strong> 02873005588
                        </p>
                    </div>
                </div>
            </div>
        </footer>
    );
}
