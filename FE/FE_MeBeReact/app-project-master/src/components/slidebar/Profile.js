import { NavLink } from "react-router-dom";
import './Profile.css';

export default function Profile() {
    return (
        <div className="profile-small_slidebar-container">
            <div className="profile-small_slidebar header">
                <div className="profile-small_slidebar header__avatar">
                    <img src="https://scontent.fsgn8-3.fna.fbcdn.net/v/t39.30808-1/422906381_1458609631752987_4674670501739376302_n.jpg?stp=dst-jpg_s200x200&_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_ohc=lrHyEAI0zVEQ7kNvgG7SMPx&_nc_ht=scontent.fsgn8-3.fna&oh=00_AYAWZHQ-lY2WQ39pzUfBkFPtKgK3ieL6GPQnSRkj1mlEUg&oe=667DD089" alt="avatar" />
                </div>
                <div className="profile-small_slidebar header__info">
                    <div className="profile-small_slidebar header__info-name">Huỳnh Minh Phước</div>
                    <div className="profile-small_slidebar header__info-change">
                        <NavLink to={"/account/profile"}>
                            <i className="fa-solid fa-pen"></i>
                            Sửa hồ sơ
                        </NavLink>
                    </div>
                </div>
            </div>

            <div className="profile-small_slidebar body">
                <div className="profile-small_slidebar body__item">
                    <div className="profile-small_slidebar body__item-title">
                        <i class="fa-regular fa-user"></i>
                        Tài khoản của tôi
                    </div>
                    <div className="profile-small_slidebar body__item-content">
                        <NavLink to={"/account/profile"}>
                            <span className="content__profile">Hồ sơ</span>
                        </NavLink>
                        <NavLink to={"/account/address"}>
                            <span className="content__profile">Địa chỉ</span>
                        </NavLink>
                        <NavLink to={"/account/changePassword"}>
                            <span className="content__profile">Đổi mật khẩu</span>
                        </NavLink>
                        <NavLink to={"/account/tracking"}>
                            <span className="content__profile">Đơn mua</span>
                        </NavLink>
                    </div>
                </div>
            </div>
        </div>
    )
}