package com.ams.AMS.services.department;

import com.ams.AMS.entities.Department.Department;
import com.ams.AMS.exceptions.DAOResponse;
import com.ams.AMS.repository.department.DepartmentRepository;
import com.ams.AMS.util.response.Response;
import com.ams.AMS.vo.departmentVo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class DepartmentService {
    public static final Logger logger = Logger.getLogger(DepartmentService.class.getName());

    @Autowired
    private DepartmentRepository departmentRepository;

    public Response addDepartment(DepartmentVo departmentVo) {
        Response response = new Response();
        try {
            if(departmentVo.getDepartmentName() == null || departmentVo.getDepartmentName().isEmpty()){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            Department department = new Department();
            if(departmentVo.getId() == null){
                department.setDepartmentName(departmentVo.getDepartmentName());
                department.setDescription(departmentVo.getDescription());
                Department save = departmentRepository.save(department);
                DepartmentVo departmentVo1 = DepartmentVo.setResponse(save);
                response.setResponse(DAOResponse.SUCCESS);
                response.setData("department",departmentVo1);
            }else{
                Department departmentById = departmentRepository.findDepartmentById(departmentVo.getId());
                if(departmentById != null){
                    department.setDepartmentName(departmentById.getDepartmentName());
                    department.setDescription(departmentById.getDescription());
                    Department save = departmentRepository.save(department);
                    DepartmentVo departmentVo1 = DepartmentVo.setResponse(save);
                    response.setResponse(DAOResponse.SUCCESS);
                    response.setData("department",departmentVo1);
                }else {
                    response.setResponse(DAOResponse.RECORD_NOT_FOUND);
                    logger.warning("Department with name " + departmentVo.getDepartmentName() + " not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error adding/updating the department: " + e.getMessage());
        }
        return response;
    }

    public Response getAllDepartments(Long PageNo, Long pageSize) {
        Response response = new Response();
        try {
            PageNo = PageNo == null ? 0 : PageNo;
            pageSize = pageSize == null ? 10 : pageSize;
            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            Pageable pageable = PageRequest.of(Math.toIntExact(PageNo), Math.toIntExact(pageSize), sort);
            Page<Department> page;

            page = departmentRepository.findAll(pageable);
            if (page.isEmpty()) {
                response.setResponse(DAOResponse.NO_DATA_FOUND);
                return response;
            }
            List<DepartmentVo> list = page.getContent().stream().map(DepartmentVo::setResponse).toList();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("data", list);
            map.put("currentPage", page.getNumber());
            map.put("totalPages", page.getTotalPages());
            map.put("totalItems", page.getTotalElements());
            map.put("pageSize", page.getSize());

            response.setResponse(DAOResponse.SUCCESS);
            response.setData("departments", map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error retrieving departments: " + e.getMessage());
        }
        return response;
    }

    public Response getDepartmentById(Long id){
        Response response = new Response();
        try {
            if(id == null){
                response.setResponse(DAOResponse.INVALID_REQUEST);
                return response;
            }
            Department department = departmentRepository.findDepartmentById(id);
            if(department == null){
                response.setResponse(DAOResponse.DEPARTMENT_NOT_FOUND);
                return response;
            }
            DepartmentVo departmentVo = DepartmentVo.setResponse(department);
            response.setResponse(DAOResponse.SUCCESS);
            response.setData("department", departmentVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error retrieving department by ID: " + e.getMessage());
        }
        return response;
    }
}
