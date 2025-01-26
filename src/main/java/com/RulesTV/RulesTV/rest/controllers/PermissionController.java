package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.entity.Permission;
import com.RulesTV.RulesTV.repositories.PermissionRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.PermissionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository){
        this.permissionRepository = permissionRepository;
    }

    @GetMapping("/all")
    public List<PermissionDTO> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream().map(permission -> {
            PermissionDTO dto = new PermissionDTO(permission.getName());
            dto.setName(permission.getName());
            return dto;
        }).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable int id){
        return permissionRepository.findById(id)
                .map(permission -> {
                    PermissionDTO dto = new PermissionDTO(permission.getName());
                    dto.setName(permission.getName());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        Permission savedPermission = permissionRepository.save(permission);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPermission);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable int id, @RequestBody Permission updatedPermission) {
        return permissionRepository.findById(id)
                .map(existingPermission -> {
                    existingPermission.setName(updatedPermission.getName());
                    existingPermission.setPermissionRoleList(updatedPermission.getPermissionRoleList());
                    permissionRepository.save(existingPermission);
                    return ResponseEntity.ok(existingPermission);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable int id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
