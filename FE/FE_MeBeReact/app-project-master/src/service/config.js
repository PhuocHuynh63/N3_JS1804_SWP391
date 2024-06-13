import axios from "axios";
import { localService } from "./localService";

export const BASE_URL = "http://localhost:8080";

const TokenApp = "PP39rVlhPwxIt0Fl40ZmcG8TdBFQrbXVsVhdF6JWoKM=";


export const configHeader = () => {
    return {
        TokenApp: TokenApp,
        Authorization: "Bearer " + localService.get()?.accessToken,
        // ? là optional chaining
    }
}

export const https = axios.create({
    baseURL: BASE_URL,
    headers: configHeader(),
})