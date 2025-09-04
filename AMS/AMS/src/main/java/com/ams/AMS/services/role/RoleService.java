package com.ams.AMS.services.role;

import com.ams.AMS.entities.roles.Roles;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.repository.role.RoleRepository;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.rolesVo.RolesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    private static final Logger logger = Logger.getLogger(RoleService.class.getName());


    public Response addRole(RolesVo roleVo){
        Response response = new Response();
        Roles roles = new Roles();
        try{
            if(roleVo.getId() == null){
                roles.setRoleName(roleVo.getRoleName());
                roles.setDescription(roleVo.getRoleDescription());
                roles.setIsActive(roleVo.getIsActive());
                roles.setCreatedAt(new Date(System.currentTimeMillis()));

                roles = roleRepository.save(roles);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("roles", RolesVo.getRolesVo(roles));
            }else{
                Roles role = roleRepository.findRoleById(roleVo.getId());
                if(role == null){
                    response.setResponse(DAOResponse.ROLE_NOT_FOUND);
                    return response;
                }
                role.setRoleName(roleVo.getRoleName());
                role.setDescription(roleVo.getRoleDescription());
                role.setIsActive(roleVo.getIsActive());
                role.setModifiedAt(new Date(System.currentTimeMillis()));
                roles = roleRepository.save(role);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("roles", RolesVo.getRolesVo(roles));
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in addRole: " + e.getMessage());

        }
        return response;
    }

    public Response getRoleById(Long id){
        Response response = new Response();
        try{
            Roles role = roleRepository.findRoleById(id);
            if(role == null){
                response.setResponse(DAOResponse.ROLE_NOT_FOUND);
                return response;
            }
            response.setResponse(DAOResponse.SUCCESS);
            response.setData("role", RolesVo.getRolesVo(role));
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in getRoleById: " + e.getMessage());
        }
        return response;
    }

    public Response getAllRoles(Boolean isActive, Long pageNo, Long pageSize){
        Response response = new Response();
        try{
            pageNo = pageNo == null ? 0 : pageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(pageNo), Math.toIntExact(pageSize), sort);
            Page<Roles> page;
            if(Boolean.TRUE.equals(isActive)){
                page = roleRepository.findRoleByIsActiveTrue(pageable);
            }else{
                page = roleRepository.findAll(pageable);
            }
            List<RolesVo> list = page.getContent().stream().map(RolesVo::getRolesVo).toList();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("data", list);
            map.put("total", page.getTotalElements());
            map.put("totalPages", page.getTotalPages());
            map.put("currentPage", page.getNumber());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("users", map);
        }catch (Exception e){
            e.printStackTrace();
            logger.severe("Error in getAllRoles: " + e.getMessage());
        }
        return response;
    }

}
