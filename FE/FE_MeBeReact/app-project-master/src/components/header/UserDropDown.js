import { Dropdown, Space } from 'antd';
import './UserDropDown.css'
import { NavLink } from 'react-router-dom';

const UserDropdown = ({ user, logoutBtn }) => {

  const menuItems = [
    {
      label: logoutBtn,
      key: "1",
    },
    {
      label: <NavLink to={"/account/profile"}>Tài khoản của tôi</NavLink>,
      key: "2",
    },
    {
      label: <NavLink to={"/account/tracking"}>Đơn mua</NavLink>,
      key: "3",
    },
  ];

  // Thêm mục Admin nếu người dùng có vai trò 'admin' hoặc 'staff'
  if (user && (user.role === 'admin' || user.role === 'staff')) {
    menuItems.push({
      label: <NavLink to={"/admin"}>Admin</NavLink>,
      key: "4",
    });
  }

  return (
    <Dropdown
      menu={{
        items: menuItems,
      }}
      trigger={['click']}
    >
      <a onClick={(e) => e.preventDefault()}>
        <Space>
          <img src='../images/avt.png' className='avt' />
        </Space>
      </a>
    </Dropdown>
  )
}


export default UserDropdown;