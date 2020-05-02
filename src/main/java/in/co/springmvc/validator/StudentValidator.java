package in.co.springmvc.validator;

import in.co.springmvc.dto.StudentDTO;
import in.co.springmvc.form.StudentForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StudentValidator implements Validator {

	public boolean supports(Class student) {
		return StudentDTO.class.isAssignableFrom(student);
	}

	public void validate(Object target, Errors errors) {

		StudentForm student = (StudentForm) target;

		if (DataValidator.isNull(student.getFirstName())) {
			errors.rejectValue("firstName", "NotEmpty.form.firstName");
		}

		else if (!DataValidator.isNull(student.getFirstName())) {

			if (DataValidator.isNotAlpha(student.getFirstName())) {
				errors.rejectValue("firstName", "firstName.alpha");

			}

		}

		if (DataValidator.isNull(student.getLastName())) {
			errors.rejectValue("lastName", "NotEmpty.form.lastName");
		}

		else if (!DataValidator.isNull(student.getLastName())) {

			if (DataValidator.isNotAlpha(student.getLastName())) {
				errors.rejectValue("lastName", "lastName.alpha");

			} else if (!DataValidator.isAlphabetsOnly(student.getLastName())) {
				errors.rejectValue("lastName", "lastName.alphabets");
			}

		}

		if (DataValidator.isNull(student.getEmail())) {
			errors.rejectValue("email", "NotEmpty.form.email");
		} else if (!DataValidator.isNull(student.getEmail())) {

			if (!DataValidator.isEmail(student.getEmail())) {
				errors.rejectValue("email", "Email.invalid");

			}

		}

		if (student.getDob() != null) {
			if (!DataValidator.isValidDate((student.getDob()))) {

				errors.rejectValue("dob", "dob.pastLimit");

			} else if (DataValidator.isUnderAge(student.getDob())) {

				errors.rejectValue("dob", "dob.underAgeLimit");

			} else if (DataValidator.isOverAge(student.getDob())) {

				errors.rejectValue("dob", "dob.overAgeLimit");

			}
		}

		if (DataValidator.isNull(student.getMobileNo())) {
			errors.rejectValue("mobileNo", "NotEmpty.form.mobileNo");
		}

		else if (!DataValidator.isNull(student.getMobileNo())) {

			if (DataValidator.isAlpha(student.getMobileNo())) {
				errors.rejectValue("mobileNo", "mobile.int");
			}

			else if (student.getMobileNo().length() != 10) {

				errors.rejectValue("mobileNo", "mobile.length");
			} else if (DataValidator.isNotMobileNo(student.getMobileNo())) {

				errors.rejectValue("mobileNo", "mobileNo.invalid");

			}
		}

	}

}
