
package in.co.springmvc.validator;

import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.form.UserForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	public boolean supports(Class user) {
		return UserDTO.class.isAssignableFrom(user);
	}

	public void validate(Object target, Errors errors) {

		UserForm user = (UserForm) target;

		if (DataValidator.isNull(user.getFirstName())) {

			errors.rejectValue("firstName", "NotEmpty.form.firstName");

		}

		else if (!DataValidator.isNull(user.getFirstName())) {

			if (DataValidator.isNotAlpha(user.getFirstName())) {
				errors.rejectValue("firstName", "firstName.alpha");

			}

		}

		if (DataValidator.isNull(user.getLastName())) {

			errors.rejectValue("lastName", "NotEmpty.form.lastName");

		}

		else if (!DataValidator.isNull(user.getLastName())) {

			if (DataValidator.isNotAlpha(user.getLastName())) {
				errors.rejectValue("lastName", "lastName.alpha");

			} else if (!DataValidator.isAlphabetsOnly(user.getLastName())) {
				errors.rejectValue("lastName", "lastName.alphabets");
			}

		}

		if (user.getDob() != null) {
			if (!DataValidator.isValidDate((user.getDob()))) {

				errors.rejectValue("dob", "dob.pastLimit");

			} else if (DataValidator.isUnderAge(user.getDob())) {

				errors.rejectValue("dob", "dob.underAgeLimit");

			} else if (DataValidator.isOverAge(user.getDob())) {

				errors.rejectValue("dob", "dob.overAgeLimit");

			}
		}

		if (DataValidator.isNull(user.getMobileNo())) {

			errors.rejectValue("mobileNo", "NotEmpty.form.mobileNo");

		}

		else if (!DataValidator.isNull(user.getMobileNo())) {

			if (DataValidator.isAlpha(user.getMobileNo())) {
				errors.rejectValue("mobileNo", "mobile.int");
			}

			else if (user.getMobileNo().length() != 10) {

				errors.rejectValue("mobileNo", "mobile.length");
			} else if (DataValidator.isNotMobileNo(user.getMobileNo())) {

				errors.rejectValue("mobileNo", "mobileNo.invalid");

			}
		}

		if (DataValidator.isNull(user.getLogin())) {

			errors.rejectValue("login", "NotEmpty.form.login");

		}

		else if (!DataValidator.isNull(user.getLogin())) {

			if (!DataValidator.isEmail(user.getLogin())) {
				errors.rejectValue("login", "Email.invalid");

			}

		}

		if (!DataValidator.isNull(user.getPassword())) {

			if (user.getPassword().length() <= 5
					|| user.getPassword().length() > 10) {

				errors.rejectValue("password", "password.length");

			}

		}
	}

}
