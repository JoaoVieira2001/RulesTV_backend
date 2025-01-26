package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.PermissionRole;
import com.RulesTV.RulesTV.repositories.PermissionRoleRepository;
import com.RulesTV.RulesTV.rest.DTO.PermissionRoleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permission_role")
public class PermissionRoleController {

    private final PermissionRoleRepository permissionRoleRepository;

    public PermissionRoleController(PermissionRoleRepository permissionRoleRepository){
        this.permissionRoleRepository = permissionRoleRepository;
    }

    @GetMapping("/all")
    public List<PermissionRoleDTO> getAllPermissionRoles() {
        List<PermissionRole> permissionRoles = permissionRoleRepository.findAll();

        return permissionRoles.stream().map(permissionRole -> {
            PermissionRoleDTO dto = new PermissionRoleDTO(permissionRole.getId(),permissionRole.getPermission().getId(),permissionRole.getPermission().getName(),permissionRole.getRole().getId(),permissionRole.getRole().getName(),permissionRole.getRole().getTypeRole().name());
            dto.setId(permissionRole.getId());
            dto.setPermissionName(permissionRole.getPermission().getName());
            dto.setRoleName(permissionRole.getRole().getName());
            dto.setRoleType(permissionRole.getRole().getTypeRole().name());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionRoleDTO> getPermissionRoleById(@PathVariable int id) {
        return permissionRoleRepository.findById(id)
                .map(permissionRole -> {
                    PermissionRoleDTO dto = new PermissionRoleDTO(permissionRole.getId(),permissionRole.getPermission().getId(),permissionRole.getPermission().getName(),permissionRole.getRole().getId(),permissionRole.getRole().getName(),permissionRole.getRole().getTypeRole().name());
                    dto.setId(permissionRole.getId());
                    dto.setPermissionName(permissionRole.getPermission().getName());
                    dto.setRoleName(permissionRole.getRole().getName());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<PermissionRole> createPermissionRole(@RequestBody PermissionRole permissionRole){
        PermissionRole savedPermissionRole = permissionRoleRepository.save(permissionRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPermissionRole);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<PermissionRole> updatePermissionRole(@PathVariable int id, @RequestBody PermissionRole updatedPermissionRole) {
        return permissionRoleRepository.findById(id)
                .map(existingPermissionRole -> {
                    existingPermissionRole.setPermission(updatedPermissionRole.getPermission());
                    existingPermissionRole.setRole(updatedPermissionRole.getRole());
                    permissionRoleRepository.save(existingPermissionRole);
                    return ResponseEntity.ok(existingPermissionRole);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePermissionRole(@PathVariable int id) {
        if (permissionRoleRepository.existsById(id)) {
            permissionRoleRepository.deleteById(id);
            return ResponseEntity.ok("The PermissionRole with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PermissionRole with ID " + id + " not found.");
        }
    }
}
