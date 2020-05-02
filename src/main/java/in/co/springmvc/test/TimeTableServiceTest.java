package in.co.springmvc.test;
/*package in.co.sunrays.proj0.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.sunrays.proj0.dto.FacultyDTO;
import in.co.sunrays.proj0.dto.TimeTableDTO;
import in.co.sunrays.proj0.exception.ApplicationException;
import in.co.sunrays.proj0.exception.DatabaseException;
import in.co.sunrays.proj0.exception.DuplicateRecordException;
import in.co.sunrays.proj0.service.TimeTableServiceInt;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
public class TimeTableServiceTest {

	@Autowired
	TimeTableServiceInt service;

	public static TimeTableDTO dto = new TimeTableDTO();
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

	@Test
	public void testAdd() throws ParseException, DuplicateRecordException,
			DatabaseException {

		dto.setCourseId(1L);
		dto.setSubject("Maths");
		dto.setExaminationDate(sdf.parse("16/08/2016"));
		dto.setTime("10:00 AM-1:00 AM");
		dto.setDay("Wensday");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			long pk = service.add(dto);
			TimeTableDTO added_DTO = service.findByPK(pk);
			if (added_DTO == null) {
				System.out.println("METHOD ADD FAILS");
			} else {
				System.out.println("RECORD ADDED AT " + dto.getId());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
			System.out.println("DUPLICATE RECORD");
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println("DATABASE");
		}

	}

	@Ignore
	public void testUpdate() throws ParseException {

		long pk = 2L;
		try {
			dto = service.findByPK(pk);
			dto.setCourseId(1L);
			dto.setSubject("Maths");
			dto.setExaminationDate(sdf.parse("17/5/2016"));
			dto.setTime("10:00 AM-1:00 AM");
			dto.setDay("Wensday");
			dto.setCreatedBy("super_admin@gmail.com");
			dto.setModifiedBy("super_admin@gmail.com");
			dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
			dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
			service.update(dto);
			TimeTableDTO updated_DTO = service.findByPK(pk);
			if (!"ROBIN".equals(updated_DTO.getCourse())) {
				System.out.println("RECORD UPDATED AT " + dto.getId());

			} else {
				System.out.println("UPDATE METHOD FAILS");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testDelete() {
		long pk = 3L;
		dto.setId(pk);

		service.delete(pk);
		TimeTableDTO deleted_DTO = service.findByPK(pk);
		if (deleted_DTO != null) {
			System.out.println("DELETE METHOD FAILS");
		} else {
			System.out.println("RECORD DELETED AT " + dto.getId());
		}

	}

	@Ignore
	public void testFindByExaminationDate() throws 
			ParseException {
		dto = service.findByExaminationDate(sdf1.parse("2016/5/15"), "BE");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tCOURSE_NAME \tSUBJECT \tEXAMINATION_DATE \tDAY \tTIME");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getCourse());
			System.out.print("\t" + dto.getSubject());
			System.out.print("\t" + dto.getExaminationDate());
			System.out.print("\t" + dto.getDay());
			System.out.print("\t" + dto.getTime());
		}

	}

	@Ignore
	public void testFindByCourse() {

		dto = service.findByCourse("BE", "Maths");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tCOURSE_NAME \tSUBJECT \tEXAMINATION_DATE \tDAY \tTIME");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getCourse());
			System.out.print("\t" + dto.getSubject());
			System.out.print("\t" + dto.getExaminationDate());
			System.out.print("\t" + dto.getDay());
			System.out.print("\t" + dto.getTime());
		}

	}
}
*/