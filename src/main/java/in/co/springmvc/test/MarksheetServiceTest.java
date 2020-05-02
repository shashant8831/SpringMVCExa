package in.co.springmvc.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.springmvc.dto.MarksheetDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.MarksheetServiceInt;

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
public class MarksheetServiceTest {

	@Autowired
	MarksheetServiceInt service;

	public static MarksheetDTO dto = new MarksheetDTO();

	@Ignore
	public void testAdd() {

		dto.setRollNo("0818cs15");
		dto.setStudentId(6L);
		dto.setPhysics(90);
		dto.setMaths(85);
		dto.setChemistry(90);
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

		try {
			long pk = service.add(dto);
			MarksheetDTO added_DTO = service.findByPK(pk);
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
	public void testUpdate() {
		dto = service.findByPK(6L);
		dto.setStudentId(6L);
		dto.setPhysics(90);
		dto.setMaths(90);
		dto.setChemistry(90);
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			service.update(dto);
			MarksheetDTO updated_DTO = service.findByPK(6L);
			if (!"rk choudhary".equals(updated_DTO.getName())) {
				System.out.println("Test Update fail");
			} else {
				System.out.println("RECORD UPDATED AT " + dto.getId());
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
		MarksheetDTO deleted_DTO = service.findByPK(pk);
		if (deleted_DTO != null) {
			System.out.println("DELETE METHOD FAILS");
		} else {
			System.out.println("RECORD DELETED AT " + dto.getId());
		}

	}

	@Ignore
	public void testFindByRoll() {
		dto = service.findByRollNo("0818cs10");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tSTUDENT_ID \tROLL_NO  \tNAME \t\tPHYSICS  CHEMISTRY \tMATHS");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t\t" + dto.getRollNo());
			System.out.print("\t" + dto.getName());
			System.out.print("\t\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.print("\t" + dto.getMaths());
		}

	}

	@Ignore
	public void testFindByPk() {
		long pk = 4L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tSTUDENT_ID \tROLL_NO  \tNAME \t\tPHYSICS  CHEMISTRY \tMATHS");
			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getStudentId());
			System.out.print("\t\t" + dto.getRollNo());
			System.out.print("\t" + dto.getName());
			System.out.print("\t\t" + dto.getPhysics());
			System.out.print("\t" + dto.getChemistry());
			System.out.print("\t" + dto.getMaths());
		}
	}

	@Ignore
	public void testSearch() {
		List list = new ArrayList();
		dto.setRollNo("0818cs15");
		list = service.search(dto, 0, 10);
		if (list.size() <= 0) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tSTUDENT_ID \tROLL_NO  \tNAME \t\tPHYSICS  CHEMISTRY \tMATHS");

			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (MarksheetDTO) it.next();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getStudentId());
				System.out.print("\t\t" + dto.getRollNo());
				System.out.print("\t" + dto.getName());
				System.out.print("\t\t" + dto.getPhysics());
				System.out.print("\t" + dto.getChemistry());
				System.out.print("\t" + dto.getMaths());
			}
		}

	}

	@Test
	public void testMeritList() {
		List list = new ArrayList();
		list = service.getMeritList(0, 10);
		if (list.size() <= 0) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tSTUDENT_ID \tROLL_NO  \tNAME \t\tPHYSICS  CHEMISTRY \tMATHS \tTOTAL");

			Iterator it = list.iterator();
			while (it.hasNext()) {

				dto = (MarksheetDTO) it.next();
				int total = dto.getPhysics() + dto.getMaths()
						+ dto.getChemistry();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getStudentId());
				System.out.print("\t\t" + dto.getRollNo());
				System.out.print("\t" + dto.getName());
				System.out.print("\t\t" + dto.getPhysics());
				System.out.print("\t" + dto.getChemistry());
				System.out.print("\t" + dto.getMaths());
				System.out.print("\t" + total);
			}
		}

	}
}
