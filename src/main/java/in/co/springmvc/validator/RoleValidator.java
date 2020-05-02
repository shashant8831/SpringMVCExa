package in.co.springmvc.validator;

import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.form.RoleForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RoleValidator implements Validator {

	public boolean supports(Class role) {
		return RoleDTO.class.isAssignableFrom(role);
	}

	public void validate(Object target, Errors errors) {

		RoleForm role = (RoleForm) target;

		if (DataValidator.isNull(role.getRoleName())) {
			errors.rejectValue("roleName", "NotEmpty.form.roleName");

		}

		else if (!DataValidator.isNull(role.getRoleName())) {

			if (DataValidator.isNotAlpha(role.getRoleName())) {

				errors.rejectValue("roleName", "roleName.alpha");

			}
		}

		if (DataValidator.isNull(role.getRoleDescription())) {
			errors.rejectValue("roleDescription",
					"NotEmpty.form.roleDescription");

		}

		if (!DataValidator.isNull(role.getRoleDescription())) {

			if (DataValidator.isNotAlpha(role.getRoleDescription())) {

				errors.rejectValue("roleDescription", "roleDescription.alpha");

			}
		}

	}
}
