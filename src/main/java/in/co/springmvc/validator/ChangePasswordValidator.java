package in.co.springmvc.validator;

import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.form.ChangePasswordForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordValidator implements Validator {

	public boolean supports(Class user) {
		return UserDTO.class.isAssignableFrom(user);
	}

	public void validate(Object target, Errors errors) {
		ChangePasswordForm user = (ChangePasswordForm) target;

		if (!DataValidator.isNull(user.getOldPassword())) {

			if (user.getOldPassword().equals(user.getNewPassword())) {

				errors.rejectValue("newPassword", "oldPassword.error");

			}
		}

		if (!DataValidator.isNull(user.getNewPassword())) {

			if (user.getNewPassword().length() <= 5
					|| user.getNewPassword().length() > 10) {

				errors.rejectValue("newPassword", "newPassword.length");

			}

		}

		if (!user.getNewPassword().equals(user.getConfirmPassword())
				&& !"".equals(user.getConfirmPassword())) {

			errors.rejectValue("confirmPassword", "confirmPassword.invalid");

		}

	}

}
