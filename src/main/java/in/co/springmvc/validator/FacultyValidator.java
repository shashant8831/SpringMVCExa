package in.co.springmvc.validator;

import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.form.FacultyForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FacultyValidator implements Validator {

	public boolean supports(Class faculty) {
		return FacultyDTO.class.isAssignableFrom(faculty);
	}

	public void validate(Object target, Errors errors) {

		FacultyForm faculty = (FacultyForm) target;

		if (DataValidator.isNull(faculty.getFirstName())) {
			errors.rejectValue("firstName", "NotEmpty.form.firstName");
		}

		else if (!DataValidator.isNull(faculty.getFirstName())) {

			if (DataValidator.isNotAlpha(faculty.getFirstName())) {
				errors.rejectValue("firstName", "firstName.alpha");

			}

		}

		if (DataValidator.isNull(faculty.getLastName())) {
			errors.rejectValue("lastName", "NotEmpty.form.lastName");
		}

		else if (!DataValidator.isNull(faculty.getLastName())) {

			if (DataValidator.isNotAlpha(faculty.getLastName())) {
				errors.rejectValue("lastName", "lastName.alpha");

			} else if (!DataValidator.isAlphabetsOnly(faculty.getLastName())) {
				errors.rejectValue("lastName", "lastName.alphabets");
			}

		}

		if (faculty.getDob() != null) {
			if (!DataValidator.isValidDate((faculty.getDob()))) {

				errors.rejectValue("dob", "dob.pastLimit");

			} else if (DataValidator.isUnderAge(faculty.getDob())) {

				errors.rejectValue("dob", "dob.underAgeLimit");

			} else if (DataValidator.isOverAge(faculty.getDob())) {

				errors.rejectValue("dob", "dob.overAgeLimit");

			}
		}

		if (DataValidator.isNull(faculty.getMobileNo())) {
			errors.rejectValue("mobileNo", "NotEmpty.form.mobileNo");
		} else if (!DataValidator.isNull(faculty.getMobileNo())) {

			if (DataValidator.isAlpha(faculty.getMobileNo())) {
				errors.rejectValue("mobileNo", "mobile.int");
			}

			else if (faculty.getMobileNo().length() != 10) {

				errors.rejectValue("mobileNo", "mobile.length");
			} else if (DataValidator.isNotMobileNo(faculty.getMobileNo())) {

				errors.rejectValue("mobileNo", "mobileNo.invalid");

			}
		}
		if (DataValidator.isNull(faculty.getEmail())) {
			errors.rejectValue("email", "NotEmpty.form.email");
		}

		else if (!DataValidator.isNull(faculty.getEmail())) {

			if (!DataValidator.isEmail(faculty.getEmail())) {
				errors.rejectValue("email", "Email.invalid");

			}

		}

		if (!DataValidator.isNull(faculty.getPrimarySubject())) {

			if (faculty.getPrimarySubject().equals(
					faculty.getSecondarySubject())) {

				errors.rejectValue("primarySubject", "message.sameSubject");

			}
		}
	}

}
