import { Link } from "react-router-dom";

// eslint-disable-next-line react/prop-types
const Navbar = ({ isAuthenticated, isAdmin, handleLogout }) => {
  return (
    <div>
      <nav className="bg-white shadow border-b border-black">
        <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div className="flex h-16 justify-between items-center">
            <div className="flex items-center space-x-4"></div>
            <div className="flex items-center space-x-4">
              {isAuthenticated && (
                <>
                  <Link
                    to="/profile"
                    className="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    Profile
                  </Link>
                  {isAdmin && (
                    <Link
                      to="/admin/user-management"
                      className="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                    >
                      User Management
                    </Link>
                  )}
                  <Link
                    to="/"
                    onClick={handleLogout}
                    className="inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                  >
                    Logout
                  </Link>
                </>
              )}
            </div>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
