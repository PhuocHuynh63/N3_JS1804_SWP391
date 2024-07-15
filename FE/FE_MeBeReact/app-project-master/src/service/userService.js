import axios from "axios";
import { BASE_URL, configHeader } from "./config";

export const userService = {
    postLogin: async (loginForm) => {
        try {
            const response = await axios.post(`${BASE_URL}/login`, null, {
                params: loginForm,  // Using params to match @RequestParam in backend
                headers: configHeader()
            });
            return response.data;
        } catch (error) {
            console.error('Error logging in:', error);
            throw error;
        }
    },
    postSignUp: async (signUpForm) => {
        try {
            const response = await axios.post(`${BASE_URL}/register`, signUpForm, {
                headers: configHeader()
            });
            return response.data;
        } catch (error) {
            console.error('Error signing up:', error);
            throw error;
        }
    }
};
