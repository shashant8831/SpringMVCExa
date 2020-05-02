package in.co.springmvc.test;

import in.co.springmvc.dto.FacultyDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.FacultyServiceInt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
public class FacultyServiceTest {

	@Autowired
	FacultyServiceInt service;

	public static FacultyDTO dto = new FacultyDTO();
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Ignore
	public void testAdd() throws ParseException {
		dto.setCollegeId(5L);
		dto.setCourseId(5L);
		dto.setFirstName("Baba");
		dto.setLastName("Baba");
		dto.setDob(sdf.parse("20/5/1990"));
		dto.setQualification("MTECH");
		dto.setEmail("Baba@gmail.com");
		dto.setMobileNo("9826545896");
		dto.setAddress("INDORE");
		dto.setPrimarySubject("Physics");
		dto.setSecondarySubject("Chemistry");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

		long pk;
		try {
			pk = service.add(dto);
			FacultyDTO added_DTO = service.findByPK(pk);
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
		long pk = 6L;
		dto = service.findByPK(pk);
		dto.setCollegeId(6L);
		dto.setCourseId(3L);
		dto.setFirstName("Abhijaat");
		dto.setLastName("Bhosale");
		dto.setDob(sdf.parse("28/06/1985"));
		dto.setQualification("MS");
		dto.setEmail("bhosale@gmail.com");
		dto.setMobileNo("9826645698");
		dto.setAddress("INDORE");
		dto.setPrimarySubject("Physics");
		dto.setSecondarySubject("Chemistry");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			service.update(dto);
			FacultyDTO updated_DTO = service.findByPK(pk);
			if (!"ROBIN".equals(updated_DTO.getFirstName())) {
				System.out.println("RECORD UPDATED AT " + dto.getId());

			} else {
				System.out.println("UPDATE METHOD FAILS");
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testDelete() {
		long pk = 6L;
		dto.setId(pk);

		service.delete(pk);
		FacultyDTO deleted_DTO = service.findByPK(pk);
		if (deleted_DTO != null) {
			System.out.println("DELETE METHOD FAILS");
		} else {
			System.out.println("RECORD DELETED AT " + dto.getId());
		}

	}

	@Ignore
	public void testFindByPk() {
		dto = service.findByPK(2L);
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tCOLLEGE_ID \tCOLLEGE_NAME \tCOURSE_ID \tCOURSE_NAME \tFIRST_NAME \tLAST_NAME \tDOB \t\tQUALIFICATION \t\tEMAIL \t\t\tMOBILE_NO \tADDRESS");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getCollegeId());
			System.out.print("\t\t" + dto.getCollegeName());
			System.out.print("\t\t" + dto.getCourseId());
			System.out.print("\t\t" + dto.getCourseName());
			System.out.print("\t\t" + dto.getFirstName());
			System.out.print("\t\t" + dto.getLastName());
			System.out.print("\t\t" + sdf.format((dto.getDob())));
			System.out.print("\t\t" + dto.getQualification());
			System.out.print("\t\t" + dto.getEmail());
			System.out.print("\t\t" + dto.getMobileNo());
			System.out.print("\t" + dto.getAddress());
		}

	}

	@Test
	public void testFindByEmail() {
		dto = service.findByLogin("titu@gmail.com");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tCOLLEGE_ID \tCOLLEGE_NAME \tCOURSE_ID \tCOURSE_NAME \tFIRST_NAME \tLAST_NAME \tDOB \t\tQUALIFICATION \t\tEMAIL \t\t\tMOBILE_NO \tADDRESS");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getCollegeId());
			System.out.print("\t\t" + dto.getCollegeName());
			System.out.print("\t\t" + dto.getCourseId());
			System.out.print("\t\t" + dto.getCourseName());
			System.out.print("\t\t" + dto.getFirstName());
			System.out.print("\t\t" + dto.getLastName());
			System.out.print("\t\t" + sdf.format((dto.getDob())));
			System.out.print("\t\t" + dto.getQualification());
			System.out.print("\t\t" + dto.getEmail());
			System.out.print("\t\t" + dto.getMobileNo());
			System.out.print("\t" + dto.getAddress());
		}

	}

	@Ignore
	public void testSearch() {
		FacultyDTO dto = new FacultyDTO();
		List list = new ArrayList();
		long pk = 2L;
		dto.setId(pk);
		list = service.search(dto, 1, 10);

		if (list.size() < 0) {
			System.out.println("No Record Found");
		} else {
			System.out
					.println("ID \tCOLLEGE_ID \tCOLLEGE_NAME \tCOURSE_ID \tCOURSE_NAME \tFIRST_NAME \tLAST_NAME \tDOB \t\tQUALIFICATION \t\tEMAIL \t\t\tMOBILE_NO \tADDRESS");

			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (FacultyDTO) it.next();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getCollegeId());
				System.out.print("\t\t" + dto.getCollegeName());
				System.out.print("\t\t" + dto.getCourseId());
				System.out.print("\t\t" + dto.getCourseName());
				System.out.print("\t\t" + dto.getFirstName());
				System.out.print("\t\t" + dto.getLastName());
				System.out.print("\t\t" + sdf.format((dto.getDob())));
				System.out.print("\t\t" + dto.getQualification());
				System.out.print("\t\t" + dto.getEmail());
				System.out.print("\t\t" + dto.getMobileNo());
				System.out.print("\t" + dto.getAddress());
			}
		}
	}

}
