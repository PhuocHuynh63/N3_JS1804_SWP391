import { Dropdown, Space } from 'antd';
import './UserDropDown.css'
import { NavLink } from 'react-router-dom';


const UserDropdown = ({ user, logoutBtn }) => (


  <Dropdown
    menu={{
      items: [{
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
      ],
    }}
    trigger={['click']}
  >
    <a onClick={(e) => e.preventDefault()}>
      <Space>
        <img src='../images/avt.png' className='avt' />
      </Space>
    </a>
  </Dropdown>
);
export default UserDropdown;