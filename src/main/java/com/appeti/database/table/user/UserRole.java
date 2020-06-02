package com.appeti.database.table.user;

public class UserRole {
	private long roleId;
	private String name;
	private String permissionsXml; // key value pairs
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermissionsXml() {
		return permissionsXml;
	}
	public void setPermissionsXml(String permissionsXml) {
		this.permissionsXml = permissionsXml;
	}
	
}
