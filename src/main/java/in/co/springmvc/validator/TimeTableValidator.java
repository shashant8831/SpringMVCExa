package in.co.springmvc.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.springmvc.dto.TimeTableDTO;
import in.co.springmvc.form.TimeTableForm;
import in.co.springmvc.util.DataValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TimeTableValidator implements Validator {

	public boolean supports(Class timetable) {
		return TimeTableDTO.class.isAssignableFrom(timetable);
	}

	public void validate(Object target, Errors errors) {
		TimeTableForm timetable = (TimeTableForm) target;

		String date = timetable.getExaminationDate();
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		Date dt1;
		try {
			dt1 = format1.parse(date);
			DateFormat format2 = new SimpleDateFormat("EEEE");
			String finalDay = format2.format(dt1);
			timetable.setDay(finalDay);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!DataValidator.isNull(timetable.getExaminationDate())) {
			if (!DataValidator.isPastDate(timetable.getExaminationDate())) {

				errors.rejectValue("examinationDate", "examinationDate.past");

			}
		}

		 if (!DataValidator.isNull(timetable.getExaminationDate())) {
			String date1 = timetable.getExaminationDate();
			SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
			Date dt2;
			try {
				dt2 = format2.parse(date1);
				DateFormat format3 = new SimpleDateFormat("EEEE");
				String finalDay = format3.format(dt2);

				if (finalDay.equalsIgnoreCase("SUNDAY")) {
					errors.rejectValue("examinationDate",
							"examinationDate.sunday");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}

}
