package in.co.springmvc.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.springmvc.dto.StudentDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.StudentServiceInt;

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
public class StudentServiceTest {

	@Autowired
	StudentServiceInt service;

	public static StudentDTO dto = new StudentDTO();
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Ignore
	public void testAdd() throws ParseException {
		dto.setCollegeId(7L);
		dto.setFirstName("Vibhour");
		dto.setLastName("Bhatt");
		dto.setDob(sdf.parse("15/8/1992"));
		dto.setMobileNo("9456675888");
		dto.setEmail("vibhour@gmail.com");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk;
		try {
			pk = service.add(dto);
			StudentDTO added_DTO = service.findByPK(pk);
			if (added_DTO == null) {
				System.out.println("ADD METHOD FAILS");
			} else {
				System.out.println("RECORD ADDED AT " + dto.getId());
			}

		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testUpdate() throws ParseException {
		dto = service.findByPK(7L);
		dto.setCollegeId(5L);
		dto.setFirstName("Ankit");
		dto.setLastName("Somwanshi");
		dto.setDob(sdf.parse("14/09/1993"));
		dto.setMobileNo("9754899863");
		dto.setEmail("somwanshi@gmail.com");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			service.update(dto);
			StudentDTO updated_DTO = service.findByPK(7L);
			if (!"ROBIN".equals(updated_DTO.getFirstName())) {
				System.out.println("UPDATE METHOD FAILS");

			} else {
				System.out.println("RECORD UPDATED AT " + dto.getId());
			}

		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testDelete() {
		long pk = 7L;
		dto.setId(pk);

		service.delete(pk);
		StudentDTO deleted_DTO = service.findByPK(pk);
		if (deleted_DTO != null) {
			System.out.println("METHOD DELETE FAILS");
		} else {
			System.out.println("RECORD DELETED AT " + dto.getId());
		}

	}

	@Ignore
	public void testFindByPk() {
		long pk = 6L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tCOLLEGE_ID \tCOLLEGE_NAME \tFIRST_NAME \tLAST_NAME \tDOB \t\tMOBILE_NO \t EMAIL");

			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getCollegeId());
			System.out.print("\t\t" + dto.getCollegeName());
			System.out.print("\t\t" + dto.getFirstName());
			System.out.print("\t\t" + dto.getLastName());
			System.out.print("\t\t" + sdf.format(dto.getDob()));
			System.out.print("\t" + dto.getMobileNo());
			System.out.print("\t" + dto.getEmail());

		}

	}

	@Test
	public void testFindByLogin() {
		dto = service.findByLogin("Kanungo@gmail.com");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tCOLLEGE_ID \tCOLLEGE_NAME \tFIRST_NAME \tLAST_NAME \tDOB \t\tMOBILE_NO \t EMAIL");

			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getCollegeId());
			System.out.print("\t\t" + dto.getCollegeName());
			System.out.print("\t\t" + dto.getFirstName());
			System.out.print("\t\t" + dto.getLastName());
			System.out.print("\t\t" + sdf.format(dto.getDob()));
			System.out.print("\t" + dto.getMobileNo());
			System.out.print("\t" + dto.getEmail());
		}

	}

}
