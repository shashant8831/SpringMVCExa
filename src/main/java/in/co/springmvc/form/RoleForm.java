package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.util.Util;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains Role form elements and their declarative input validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class RoleForm extends BaseForm {

	/* @Pattern(regexp = "[a-zA-Z][a-zA-Z]+") */
	/* @NotEmpty */
	private String roleName;

	/* @NotEmpty */
	private String roleDescription;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Override
	public BaseDTO getDto() {

		RoleDTO dto = new RoleDTO();
		dto.setId(id);
		dto.setName(roleName);
		dto.setDescription(roleDescription);
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

		return dto;
	}

	@Override
	public void populate(BaseDTO bDto) {

		if (bDto == null) {
			return;
		}

		RoleDTO dto = (RoleDTO) bDto;

		id = dto.getId();
		roleName = Util.getCapital(dto.getName());
		roleDescription = Util.getCapital(dto.getDescription());
		createdBy = dto.getCreatedBy();
		modifiedBy = dto.getModifiedBy();
		createdDatetime = dto.getCreatedDatetime().getTime();
		modifiedDatetime = dto.getModifiedDatetime().getTime();

		super.populate(bDto);
	}

}
