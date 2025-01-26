package com.RulesTV.RulesTV.rest.DTO;

public class PermissionRoleDTO {

    private int id;
    private String permissionName;
    private String roleName;
    private String roleType;

    public PermissionRoleDTO(int id, int permissionId, String permissionName, int roleId, String roleName, String roleType) {
        this.id = id;
        this.permissionName = permissionName;
        this.roleName = roleName;
        this.roleType = roleType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
