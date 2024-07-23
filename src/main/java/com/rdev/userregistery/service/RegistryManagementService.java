package com.rdev.userregistery.service;


import com.rdev.userregistery.dto.ReqResp;
import com.rdev.userregistery.entity.RegistryUsers;
import com.rdev.userregistery.repository.RegistryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RegistryManagementService {

    @Autowired
    private RegistryRepo registryRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registers a new user
    public ReqResp register(ReqResp registrationRequest){
        ReqResp resp = new ReqResp();

        try{
            RegistryUsers user = new RegistryUsers();
            user.setEmail(registrationRequest.getEmail());
            user.setCity(registrationRequest.getCity());
            user.setRole(registrationRequest.getRole());
            user.setName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            RegistryUsers userResult = registryRepo.save(user);

            if(userResult.getId()>0){
                resp.setRegistryUsers((userResult));
                resp.setMessage("User is Saved Succesfully.");
                resp.setStatusCode(200);
            }
        }
        catch(Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());

        }
        return resp;


    }
    // Logs in a user
    public ReqResp login(ReqResp loginRequest){
        ReqResp response = new ReqResp();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword()));
            var user = registryRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),user);
            response.setStatusCode(200);
            response.setRole(user.getRole());
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Message Logged In");

        }

        catch(Exception e){

            response.setStatusCode(500);
            response.setError(e.getMessage());
        }


        return response;
    }

    // Refreshes the JWT token
    public ReqResp refreshToken(ReqResp refreshTokenRequest){
        ReqResp response = new ReqResp();
        try{
            String registryEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            RegistryUsers users = registryRepo.findByEmail(registryEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    // Retrieves all users (admin only)
    public ReqResp getAllUsers(){
        ReqResp resp = new ReqResp();
        try {
            List<RegistryUsers> result = registryRepo.findAll();
            if (!result.isEmpty()) {
                resp.setRegistryUsersList(result);
                resp.setStatusCode(200);
                resp.setMessage("Successful");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("No users found");
            }
        }
        catch (Exception e){
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());

        }
        return resp;

    }

    //Get user by ID (admin only)
    public ReqResp getUserById(Integer id){
        ReqResp resp = new ReqResp();
        try{
            RegistryUsers registryUser = registryRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
            resp.setRegistryUsers(registryUser);
            resp.setStatusCode(200);
            resp.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    //Delete User by ID
    public ReqResp deleteUserById(Integer id){
        ReqResp resp = new ReqResp();
        try{
            Optional<RegistryUsers> usroptional = registryRepo.findById(id);
            if(usroptional.isPresent()){
                registryRepo.deleteById(id);
                resp.setStatusCode(200);
                resp.setMessage("User deleted successfully");
            }
            else{
                resp.setStatusCode(404);
                resp.setMessage("User not found for deletion");
            }
        }
        catch (Exception e){
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return resp;

    }

    //Update User By Id
    public ReqResp UpdateUser(Integer id, RegistryUsers updatedUser){
        ReqResp resp = new ReqResp();
        try{
            Optional<RegistryUsers> usroptional = registryRepo.findById(id);
            RegistryUsers existingUser = usroptional.get();
            if(usroptional.isPresent()) {
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());


                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                registryRepo.save(existingUser);
                resp.setRegistryUsers(existingUser);
                resp.setStatusCode(200);
                resp.setMessage("User updated successfully");
            }
            else{
                resp.setStatusCode(404);
                resp.setMessage("User not found for update");
            }
        }
        catch (Exception e){
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return resp;

    }

    //Retrive user Info
    public ReqResp getMyInfo(String email){
        ReqResp reqRes = new ReqResp();
        try {
            Optional<RegistryUsers> userOptional = registryRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setRegistryUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }


}





