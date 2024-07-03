import { Dropdown, Space } from 'antd';
import './UserDropDown.css'
import { NavLink } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import { meBeSrc } from '../../service/meBeSrc';

const UserDropdown = ({ user, logoutBtn }) => {

  const [userToken, setUserToken] = useState({});

  useEffect(() => {
    const token = localStorage.getItem('USER_INFO');
    if (token) {
      const decoded = jwtDecode(token);
      const username = decoded.sub;
      meBeSrc.getUserByUserName(username)
        .then((res) => {
          const userData = { ...res.data, };
          setUserToken(userData);
        })
        .catch((err) => {
          console.log("Error fetching user", err);
        });
    }
  }, []);

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

  if (userToken && (userToken.role === 'admin' || userToken.role === 'staff')) {
    menuItems.push({
      label: <NavLink to={"/admin"}>Quản trị viên</NavLink>,
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