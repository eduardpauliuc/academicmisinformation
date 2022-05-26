import axios from "axios";

import authHeader from "./auth-header";

export const BASE_URL = "http://localhost:8080/api/";

const instance = () =>
  axios.create({
    baseURL: BASE_URL,
    headers: {
      "Content-type": "application/json",
      ...authHeader(),
    },
  });

export default instance;
