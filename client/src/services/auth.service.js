import axios from "axios";
import { API_URL } from "../helpers/api-helpers";

const BASE_URL = API_URL + "authenticate/";

const login = async (username, password) => {
  const response = await axios.post(BASE_URL + "signin", {
    username,
    password,
  });
  if (response.data.accessToken) {
    localStorage.setItem("user", JSON.stringify(response.data));
  }
  return response.data;
};

const logout = () => {
  localStorage.removeItem("user");
};

const authService = {
  //   register,
  login,
  logout,
};

export default authService;
