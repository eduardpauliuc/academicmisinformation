import http from "./http-common";

const API_URL = "admin/";

const getAllUsers = () => {
  return http.get(API_URL);
};

const createUser = (username, password, email, role) => {
  return http.post(API_URL + "add/", { username, password, email, role });
};

const deleteUser = (userID) => {
  return http.delete(API_URL + "delete/" + userID);
};

const AdminService = {
  getAllUsers,
  createUser,
  deleteUser,
};

export default AdminService;
