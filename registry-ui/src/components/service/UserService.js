/* eslint-disable no-useless-catch */
import axios from "axios";

class UserService {
  static BASE_URL = "http://127.0.0.1:5454";

  // Authenticates user with email and password
  static async login(email, password) {
    try {
      const response = await axios.post(`${UserService.BASE_URL}/auth/login`, {
        email,
        password,
      });
      return response.data;
    } catch (err) {
      console.log(err.message);
      throw err;
    }
  }

  // Registers a new user with provided data and token
  static async register(userData, token) {
    try {
      const response = await axios.post(
        `${UserService.BASE_URL}/auth/register`,
        userData,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  // Retrieves all users (admin function)

  static async getAllUsers(token) {
    try {
      const response = await axios.get(
        `${UserService.BASE_URL}/admin/get-all-users`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      console.log(err.message);
      throw err;
    }
  }

  // Fetches the profile of the  user

  static async getUserProfile(token) {
    try {
      const response = await axios.get(
        `${UserService.BASE_URL}/adminuser/get-profile`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }
  // Retrieves a specific user by their ID

  static async getUserById(userId, token) {
    try {
      const response = await axios.get(
        `${UserService.BASE_URL}/admin/get-users/${userId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  // Deletes a user by their ID (admin function)

  static async deleteUserById(userId, token) {
    try {
      const response = await axios.delete(
        `${UserService.BASE_URL}/admin/delete/${userId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async UpdateUser(userId, userData, token) {
    try {
      const response = await axios.put(
        `${UserService.BASE_URL}/admin/update/${userId}`,
        userData,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static logout() {
    console.log("Inside log out");

    localStorage.removeItem("token");
    localStorage.removeItem("role");
    console.log(localStorage.getItem("token"));
  }

  static isAuthenticated() {
    const tokn = localStorage.getItem("token");
    return !!tokn;
  }

  static isAdmin() {
    const role = localStorage.getItem("role");
    return role == "ADMIN";
  }

  static isUser() {
    const role = localStorage.getItem("role");
    return role == "USER";
  }

  static adminOnly() {
    return this.isAuthenticated() && this.isAdmin();
  }
}
export default UserService;
