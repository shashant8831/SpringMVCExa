package in.co.springmvc.test;

import in.co.springmvc.dto.CourseDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.CourseServiceInt;

import java.sql.Timestamp;
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
public class CourseServiceTest {

	@Autowired
	CourseServiceInt service;

	public static CourseDTO dto = new CourseDTO();

	@Ignore
	public void testAdd() {
		long record = 0L;
		dto.setName("BARCH");
		dto.setDescription("Bachelor Of Architecture");
		dto.setDuration("4");
		dto.setCreatedBy("admin@gmail.com");
		dto.setModifiedBy("admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			record = service.add(dto);
			if (record == 0) {
				System.out.println("Method Add Course Fails");
			} else {
				System.out
						.println("Course Sucessfully Added At " + dto.getId());
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testUpdate() {
		long pk = 7L;
		dto = service.findByPK(pk);
		dto.setName("MBA");
		dto.setDescription("Master Of Business Administration");
		dto.setDuration("5");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			service.update(dto);
			CourseDTO updated_Record = service.findByPK(pk);
			if (!" Tesla ".equals(updated_Record)) {
				System.out.println("RECORD UPDATED AT INDEX " + dto.getId());
			} else {
				System.out.println("RECORD UPDATE FAILED");
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
		CourseDTO Deleted_Course = service.findByPK(pk);
		if (Deleted_Course != null) {
			System.out.println("DELETE METHOD FAILS");

		} else {
			System.out.println("RECORD DELETED AT " + dto.getId());
		}

	}

	@Ignore
	public void testFindByPk() {
		long pk = 5L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out.println("ID \tCOURSE_NAME \tDESCRIPTION \t\t\tDURATION");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t\t" + dto.getDescription());
			System.out.print("\t\t" + dto.getDuration());

		}

	}

	@Ignore 
	public void testFindByName() {

		dto = service.findByName("ME");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out.println("ID \tCOURSE_NAME \tDESCRIPTION \t\t\tDURATION");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t\t" + dto.getDescription());
			System.out.print("\t\t" + dto.getDuration());

		}

	}

	@Test
	public void testSearch() {
		List list = new ArrayList();
		dto.setId(1L);
		dto.setName("BE");
		list = service.search(dto, 0, 10);
		if (list.size() <= 0) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out.println("ID \tCOURSE_NAME \tDESCRIPTION \t\t\tDURATION");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (CourseDTO) it.next();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getName());
				System.out.print("\t\t" + dto.getDescription());
				System.out.print("\t\t" + dto.getDuration());
			}

		}
	}
}