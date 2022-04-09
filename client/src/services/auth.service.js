import axios from "axios";
import { BASE_URL } from "./http-common";
import Role from "../helpers/role";
import { USE_MOCK_SERVICE } from "../helpers/constants";
const API_URL = BASE_URL + "authenticate/";

// const USE_MOCK_SERVICE = true;

const login = async (username, password) => {
  if (USE_MOCK_SERVICE) {
    let user = {
      accessToken: "...",
      birthDate: "2001-06-25",
      email: "pauliucedy@gmail.com",
      firstName: "Eduard",
      id: 1,
      lastName: "Pauliuc",
      role: Role[username] ?? Role.Student,
      tokenType: "Bearer",
      username: "eudard",
    };

    await new Promise((resolve) => setTimeout(resolve, 2000));

    localStorage.setItem("user", JSON.stringify(user));

    return user;
  }

  const response = await axios.post(API_URL + "signin", {
    username,
    password,
  });
  if (response.data.accessToken) {
    localStorage.setItem("user", JSON.stringify(response.data));
  }
  console.log("Logged in: ", response.data.username);
  return response.data;
};

const logout = () => {
  localStorage.removeItem("user");
};

const authService = {
  login,
  logout,
};

export default authService;
