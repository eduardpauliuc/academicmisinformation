import authHeader from "./auth-header";
import http from "./http-common";

const API_URL = "admin/";

const getAllUsers = () => {
  return http.get(API_URL, { headers: authHeader() });
};

const createUser = () => {};
