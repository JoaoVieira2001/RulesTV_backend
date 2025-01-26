package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.PermissionRole;
import com.RulesTV.RulesTV.entity.Role;
import com.RulesTV.RulesTV.repositories.RoleRepository;
import com.RulesTV.RulesTV.rest.DTO.PaymentDTO;
import com.RulesTV.RulesTV.rest.DTO.PermissionRoleDTO;
import com.RulesTV.RulesTV.rest.DTO.RoleDTO;
import com.RulesTV.RulesTV.rest.DTO.AccountDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }


    @GetMapping("/all")
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> {
            RoleDTO dto = new RoleDTO(
                    role.getId(),
                    role.getName(),
                    role.getTypeRole().name()
            );
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable int id) {
        return roleRepository.findById(id)
                .map(role -> {
                    // Create RoleDTO using the constructor
                    RoleDTO dto = new RoleDTO(
                            role.getId(),
                            role.getName(),
                            role.getTypeRole().name()
                    );

                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



    @PostMapping("/post")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role savedRole = roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable int id, @RequestBody Role updatedRole) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setName(updatedRole.getName());
                    existingRole.setTypeRole(updatedRole.getTypeRole());
                    existingRole.setAccountsList(updatedRole.getAccountsList());
                    existingRole.setPermissionsRoleList(updatedRole.getPermissionsRoleList());
                    roleRepository.save(existingRole);
                    return ResponseEntity.ok(existingRole);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return ResponseEntity.ok("The role with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role with ID " + id + " not found.");
        }
    }
}
