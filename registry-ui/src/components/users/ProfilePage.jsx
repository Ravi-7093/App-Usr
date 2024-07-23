import { useEffect, useState } from "react";
import UserService from "../service/UserService";
import { Link } from "react-router-dom";

const ProfilePage = () => {
  const [profileInfo, setProfileInfo] = useState({});

  useEffect(() => {
    fetchProfileInfo();
  }, []);

  //when component mounts fetch user profile
  const fetchProfileInfo = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("Token not found in localStorage");
        return;
      }
      const response = await UserService.getUserProfile(token);
      console.log(response, "this is the response");
      setProfileInfo(response.registryUsers);
    } catch (error) {
      console.error("Error fetching profile information:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col justify-center py-12 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <h2 className="mt-6 text-center text-3xl font-extrabold text-gray-900">
          User Profile
        </h2>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
          <div className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700">
                Name
              </label>
              <div className="mt-1">
                <p className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm text-gray-700 sm:text-sm">
                  {profileInfo.name}
                </p>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                Email
              </label>
              <div className="mt-1">
                <p className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm text-gray-700 sm:text-sm">
                  {profileInfo.email}
                </p>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">
                City
              </label>
              <div className="mt-1">
                <p className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm text-gray-700 sm:text-sm">
                  {profileInfo.city}
                </p>
              </div>
            </div>
            {profileInfo.role === "ADMIN" && (
              <button className="bg-indigo-600 text-white hover:bg-indigo-500 px-2 py-2 rounded-lg">
                <Link to={`/update-user/${profileInfo.id}`}>
                  Update Profile
                </Link>
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
