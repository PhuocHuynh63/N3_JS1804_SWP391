import { Dropdown, Space } from 'antd';
import './UserDropDown.css'


const UserDropdown = ({ user, logoutBtn }) => (


  <Dropdown
    menu={{
      items: [{
        label: logoutBtn,
        key: "1",
      },
      {
        label: <span>Edit account</span>,
        key: "2",
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