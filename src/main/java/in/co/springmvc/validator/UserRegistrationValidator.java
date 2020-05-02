package in.co.springmvc.validator;

import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.form.UserRegistrationForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserRegistrationValidator implements Validator {

	public boolean supports(Class user) {
		return UserDTO.class.isAssignableFrom(user);
	}

	public void validate(Object target, Errors errors) {

		UserRegistrationForm userform = (UserRegistrationForm) target;

		System.out.println("LENGTH" + userform.getFirstName().trim().length());

		if (DataValidator.isNull(userform.getFirstName())) {

			errors.rejectValue("firstName", "NotEmpty.form.firstName");

		}

		else if (!DataValidator.isNull(userform.getFirstName())) {

			if (DataValidator.isNotAlpha(userform.getFirstName())) {
				errors.rejectValue("firstName", "firstName.alpha");

			}

		}

		if (DataValidator.isNull(userform.getLastName())) {
			errors.rejectValue("lastName", "NotEmpty.form.lastName");
		}

		else if (!DataValidator.isNull(userform.getLastName())) {

			if (DataValidator.isNotAlpha(userform.getLastName())) {
				errors.rejectValue("lastName", "lastName.alpha");

			} else if (!DataValidator.isAlphabetsOnly(userform.getLastName())) {
				errors.rejectValue("lastName", "lastName.alphabets");
			}

		}

		if (userform.getDob() != null) {
			if (!DataValidator.isValidDate((userform.getDob()))) {

				errors.rejectValue("dob", "dob.pastLimit");

			} else if (DataValidator.isUnderAge(userform.getDob())) {

				errors.rejectValue("dob", "dob.underAgeLimit");

			} else if (DataValidator.isOverAge(userform.getDob())) {

				errors.rejectValue("dob", "dob.overAgeLimit");

			}
		}

		if (DataValidator.isNull(userform.getMobileNo())) {
			errors.rejectValue("mobileNo", "NotEmpty.form.mobileNo");
		}

		else if (!DataValidator.isNull(userform.getMobileNo())) {

			if (DataValidator.isAlpha(userform.getMobileNo())) {
				errors.rejectValue("mobileNo", "mobile.int");
			}

			else if (userform.getMobileNo().length() != 10) {

				errors.rejectValue("mobileNo", "mobile.length");
			} else if (DataValidator.isNotMobileNo(userform.getMobileNo())) {

				errors.rejectValue("mobileNo", "mobileNo.invalid");

			}
		}

		if (DataValidator.isNull(userform.getLogin())) {
			errors.rejectValue("login", "NotEmpty.form.login");
		}

		else if (!DataValidator.isNull(userform.getLogin())) {

			if (!DataValidator.isEmail(userform.getLogin())) {
				errors.rejectValue("login", "Email.invalid");

			}

		}
		if (!DataValidator.isNull(userform.getPassword())) {

			if (((userform.getPassword()).length()) <= 5
					|| ((userform.getPassword()).length()) > 11) {

				errors.rejectValue("password", "password.length");

			}
		}
		if (!userform.getPassword().equals(userform.getConfirmpassword())
				&& !"".equals(userform.getConfirmpassword())) {

			errors.rejectValue("confirmpassword", "confirmPassword.invalidd");

		}

	}
}
