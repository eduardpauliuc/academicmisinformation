import axios from "axios";
import authHeader from "./auth-header";
import { API_URL } from "../helpers/api-helpers";

const BASE_URL = API_URL + "admin/";

const getAllUsers = () => {
  return axios.get(BASE_URL, { headers: authHeader() });
};

const createUser = () => {
  
}
