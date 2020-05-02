package in.co.springmvc.validator;

import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.form.CollegeForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CollegeValidator implements Validator {

	public boolean supports(Class college) {
		return CollegeDTO.class.isAssignableFrom(college);
	}

	public void validate(Object target, Errors errors) {

		CollegeForm college = (CollegeForm) target;

		if (DataValidator.isNull(college.getName())) {
			errors.rejectValue("name", "NotEmpty.form.name");
		}

		else if (!DataValidator.isNull(college.getName())) {
			if (!DataValidator.isCollegeName(college.getName())) {
				errors.rejectValue("name", "college.spec");
			}
		}

		if (DataValidator.isNull(college.getAddress())) {

			errors.rejectValue("address", "NotEmpty.form.address");
		}

		if (DataValidator.isNull(college.getState())) {

			errors.rejectValue("state", "NotEmpty.form.state");

		}

		else if (!DataValidator.isNull(college.getState())) {

			if (!DataValidator.isState(college.getState())) {
				errors.rejectValue("state", "state.spec");

			}

		}

		if (DataValidator.isNull(college.getCity())) {
			errors.rejectValue("city", "NotEmpty.form.city");
		}

		else if (!DataValidator.isNull(college.getCity())) {
			if (DataValidator.isNotAlpha(college.getCity())) {
				errors.rejectValue("city", "city.alpha");
			}
		}

		if (DataValidator.isNull(college.getMobileNo())) {
			errors.rejectValue("mobileNo", "NotEmpty.form.mobileNo");
		} else if (!DataValidator.isNull(college.getMobileNo())) {
			if (DataValidator.isAlpha(college.getMobileNo())) {
				errors.rejectValue("mobileNo", "mobile.int");
			} else if (college.getMobileNo().length() != 10) {
				errors.rejectValue("mobileNo", "mobile.length");
			} else if (DataValidator.isNotMobileNo(college.getMobileNo())) {
				errors.rejectValue("mobileNo", "mobileNo.invalid");
			}
		}

	}

}
