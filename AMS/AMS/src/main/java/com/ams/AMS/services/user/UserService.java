package com.ams.AMS.services.user;

import com.ams.AMS.entities.Department.Department;
import com.ams.AMS.entities.User.User;
import com.ams.AMS.entities.roles.Roles;
import com.ams.AMS.repository.department.DepartmentRepository;
import com.ams.AMS.repository.role.RoleRepository;
import com.ams.AMS.repository.user.UserRepository;
import com.ams.AMS.util.imageUtil.ImageUtil;
import com.ams.AMS.util.jwtUtil.JwtUtil;
import com.ams.AMS.vo.userVo.UserVo;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.util.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public Response saveUser(UserVo userVo){
        Response response = new Response();
        User user = new User();
        try{
            if (userVo == null) {
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            if(userVo.getId() == null){
                User byEmail = userRepository.findByEmail(userVo.getEmail());
                if(byEmail != null){
                    response.setResponse(DAOResponse.EMAIL_ALREADY_EXISTS);
                    return response;
                }

                Roles roleName = roleRepository.findByRoleName(userVo.getRoleName());
                if (roleName == null) {
                    response.setResponse(DAOResponse.ROLE_NOT_FOUND);
                    return response;
                }
                Department departmentName = departmentRepository.findByDepartmentName(userVo.getDepartmentName());
                if(departmentName == null){
                    response.setResponse(DAOResponse.DEPARTMENT_NOT_FOUND);
                    return response;
                }

                user.setEmail(userVo.getEmail());
                user.setPassword(passwordEncoder.encode(userVo.getPassword()));
//                user.setPassword(userVo.getPassword());
                user.setIsActive(userVo.getIsActive());
                user.setFirstName(userVo.getFirstName());
                user.setLastName(userVo.getLastName());
                user.setAddress(userVo.getAddress());
                user.setPhone(userVo.getPhone());
                user.setDesignation(userVo.getDesignation());
                user.setDate_of_joining(userVo.getDateOfJoining());
                user.setCreatedAt(new Date(System.currentTimeMillis()));

                if(userVo.getImageStr() != null){
                    String image = ImageUtil.saveBase64Image(userVo.getImageStr());
                    user.setImageUrl(image);
                }
                user.setImageName(userVo.getImageName());
                user.setDepartment(departmentName);
                user.setRoles(roleName);

                userRepository.save(user);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("user", UserVo.setResponse(user));
            }else {
                User userById = userRepository.findUserById(userVo.getId());
                if(userById != null) {
                    Department departmentById = departmentRepository.findDepartmentById(userVo.getDepartmentId());
                    if(departmentById == null){
                        response.setResponse(DAOResponse.DEPARTMENT_NOT_FOUND);
                        return response;
                    }
                    userById.setFirstName(userVo.getFirstName());
                    userById.setLastName(userVo.getLastName());
                    userById.setAddress(userVo.getAddress());
                    userById.setPhone(userVo.getPhone());
                    userById.setDesignation(userVo.getDesignation());
                    userById.setDate_of_joining(userVo.getDateOfJoining());
                    if (userVo.getImageStr() != null) {
                        String image = ImageUtil.saveBase64Image(userVo.getImageStr());
                        userById.setImageUrl(image);
                    }
                    userById.setImageName(userVo.getImageName());
                    userById.setIsActive(userVo.getIsActive());
                    userById.setDepartment(departmentById);
                    userById.setModifiedAt(new Date(System.currentTimeMillis()));
                    User save = userRepository.save(userById);
                    response.setResponse(DAOResponse.SUCCESS);
                    response.setData("user", UserVo.setResponse(save));
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Error in saving user");
        }
        return response;
    }

    public Response login(UserVo userVo) {
        Response response = new Response();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userVo.getEmail(), userVo.getPassword())
            );

            User user = userRepository.findByEmail(userVo.getEmail());

            // For simplicity, assuming one role per user
            String role = Optional.ofNullable(user.getRoles())
                    .map(Roles::getRoleName)
                    .orElse("ROLE_USER");

            String token = jwtUtil.generateToken(user.getEmail(), user.getId().toString(), role);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("name", user.getFirstName());
            responseData.put("role", role);
            responseData.put("userId", user.getId());

            response.setResponse(DAOResponse.SUCCESS);
            response.setResponseData(responseData);
        }catch (Exception e) {
            logger.severe("Login failed: " + e.getMessage());
            response.setResponse(DAOResponse.INVALID_CREDENTIALS);
            return response;
        }
        return response;
    }

    public Response getAllUsers(Boolean isActive, Long pageNo, Long pageSize) {
        Response response = new Response();
        pageNo = (pageNo == null || pageNo < 0) ? 0 : pageNo;
        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize), sort);
        Page<User> page;

        try{
            if(Boolean.TRUE.equals(isActive)){
                page = userRepository.findUserByIsActiveTrue(pageable);
            }else {
                page = userRepository.findAll(pageable);
            }
            List<UserVo> users = page.getContent().stream().map(UserVo::setResponse).toList();

            Map<String, Object> map = new HashMap<>();
            map.put("data", users);
            map.put("currentPage", page.getNumber());
            map.put("totalItems", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("users", map);
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Error in fetching all users");
        }
        return response;
    }

    public Response getUserById(Long userId) {
        Response response = new Response();
        try{
            if(userId == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            User userById = userRepository.findUserById(userId);
            if(userById == null){
                response.setResponse(DAOResponse.USER_NOT_FOUND);
                return response;
            }
            response.setResponse(DAOResponse.SUCCESS);
            response.setData("user", UserVo.setResponse(userById));
        }catch(Exception e){
            e.printStackTrace();
            logger.severe("Error in fetching user by id");
        }
        return response;
    }
}
