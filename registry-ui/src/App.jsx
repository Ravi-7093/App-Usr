import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Navbar from "./components/common/Navbar";
import LoginPage from "./components/auth/LoginPage";
import RegistrationPage from "./components/auth/RegistrationPage";
import FooterComponent from "./components/common/Footer";
import UserService from "./components/service/UserService";
import UpdateUser from "./components/users/UpdateUser";
import UserManagementPage from "./components/users/UserManagement";
import ProfilePage from "./components/users/ProfilePage";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const checkAuthStatus = async () => {
      const authenticated = await UserService.isAuthenticated();
      const admin = await UserService.isAdmin();
      setIsAuthenticated(authenticated);
      setIsAdmin(admin);
    };

    checkAuthStatus();
  }, []);

  const handleLogin = (token, role) => {
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
    console.log(localStorage.getItem("token"));
    setIsAuthenticated(true);
    setIsAdmin(role === "ADMIN");
  };

  const handleLogout = () => {
    UserService.logout();
    setIsAuthenticated(false);
    setIsAdmin(false);
  };
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar
          isAuthenticated={isAuthenticated}
          isAdmin={isAdmin}
          handleLogout={handleLogout}
        />
        <div className="content">
          <Routes>
            <Route
              exact
              path="/"
              element={<LoginPage handleLogin={handleLogin} />}
            />
            <Route
              exact
              path="/login"
              element={<LoginPage handleLogin={handleLogin} />}
            />
            {isAuthenticated && (
              <Route path="/profile" element={<ProfilePage />} />
            )}
            {/* Check if user is authenticated and admin before rendering admin-only routes */}
            {isAuthenticated && isAdmin && (
              <>
                <Route path="/register" element={<RegistrationPage />} />
                <Route
                  path="/admin/user-management"
                  element={<UserManagementPage />}
                />
                <Route path="/update-user/:userId" element={<UpdateUser />} />
              </>
            )}
            <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
        </div>
        <FooterComponent />
      </div>
    </BrowserRouter>
  );
}

export default App;
