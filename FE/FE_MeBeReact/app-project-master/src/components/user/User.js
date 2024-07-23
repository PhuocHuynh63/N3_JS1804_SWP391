import { useEffect, useState } from "react";
import { meBeSrc } from "../../service/meBeSrc";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

const User = () => {
    const [user, setUser] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('USER_INFO');
        if (token) {
            const decoded = jwtDecode(token);
            const username = decoded.sub;
            meBeSrc.getUserByUserName(username)
                .then((res) => {
                    const userData = { ...res.data, };
                    setUser(userData);
                    console.log("User data", userData);
                    if (!user || userData.role === "member") navigate('/');
                })
                .catch((err) => {
                    console.log("Error fetching user", err);
                });
        } else {
            navigate('/');
        }
    }, [navigate]);

    if (!user) {
        return null
    }

}

export default User;