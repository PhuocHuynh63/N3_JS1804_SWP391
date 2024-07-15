import { localService } from '../../service/localService';
import { userService } from '../../service/userService';
import { USER_LOGIN } from '../constant/UserConstant';


export const setLoginAction = (value) => {
    return {
        type: USER_LOGIN,
        payload: value,
    }
}

export const setLoginActionService = (value, onCompleted) => {
    return (dispatch) => {
        userService.postLogin(value)
            .then((response) => {
                console.log(response);
                dispatch({
                    type: USER_LOGIN,
                    payload: response.data.content,
                });
                localService.set(response.data.content);
                onCompleted();
            }).catch((err) => {
                console.log(err);
            });
    }
}
