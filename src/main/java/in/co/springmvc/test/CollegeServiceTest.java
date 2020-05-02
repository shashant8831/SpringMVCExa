package in.co.springmvc.test;

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

import in.co.springmvc.dto.CollegeDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.CollegeServiceInt;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
public class CollegeServiceTest {

	@Autowired
	CollegeServiceInt service;

	public static CollegeDTO dto = new CollegeDTO();

	@Ignore
	public void testAdd() {

		dto.setName("TRUBA");
		dto.setAddress("Indore");
		dto.setCity("Indore");
		dto.setPhoneNo("07312869896");
		dto.setState("Madhya Pardesh");
		dto.setCreatedBy("admin@gmail.com");
		dto.setModifiedBy("admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			long pk = service.add(dto);
			CollegeDTO added_DTO = service.findByPK(pk);
			if (added_DTO == null) {
				System.out.println("ADD COLLEGE FAILS");
			} else {
				System.out.println("RECORD ADDED AT " + dto.getId());
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	@Ignore
	public void testUpdate() {
		long pk = 8L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("NO RECORD FOUND AT " + dto.getId());
		} else {
			dto.setName("Acropollis Institute");
			dto.setAddress("Indore");
			dto.setCity("Indore");
			dto.setPhoneNo("07312869896");
			dto.setState("Madhya Pardesh");
			dto.setCreatedBy("admin@gmail.com");
			dto.setModifiedBy("admin@gmail.com");
			dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
			dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
			try {
				service.update(dto);
				CollegeDTO updated_DTO = service.findByPK(pk);
				if (!" Tesla ".equals(updated_DTO)) {
					System.out
							.println("RECORD UPDATED AT INDEX " + dto.getId());
				} else {
					System.out.println("RECORD UPDATE FAILED");
				}
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Ignore
	public void testDelete() {

		long pk = 8L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("NO RECORD FOUND ");
		} else {
			dto.setId(pk);
			service.delete(pk);
			CollegeDTO deletedDTO = service.findByPK(pk);
			if (deletedDTO != null) {
				System.out.println("METHOD DELETE FAILS");
			} else {
				System.out.println("RECORD DELETED AT " + dto.getId());
			}
		}

	}

	@Ignore
	public void testFindByPk() {

		long pk = 4L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out.println("ID \tNAME \tADDRESS \tSTATE \tCITY \tCONTACT");

			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getAddress());
			System.out.print("\t" + dto.getState());
			System.out.print("\t" + dto.getCity());
			System.out.print("\t" + dto.getPhoneNo());

		}

	}

	@Ignore
	public void testFindByName() {
		dto = service.findByName("IIST");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out.println("ID \tNAME \tADDRESS \tSTATE \tCITY \tCONTACT");

			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getName());
			System.out.print("\t" + dto.getAddress());
			System.out.print("\t" + dto.getState());
			System.out.print("\t" + dto.getCity());
			System.out.print("\t" + dto.getPhoneNo());

		}
	}

	@Test
	public void testSearch() {
		List list = new ArrayList();
		long pk = 2L;
		dto.setId(pk);
		list = service.search(dto, 1, 10);
		if (list.size() == 0) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out.println("ID \tNAME \tADDRESS \tSTATE \tCITY \tCONTACT");

			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (CollegeDTO) it.next();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getName());
				System.out.print("\t" + dto.getAddress());
				System.out.print("\t" + dto.getState());
				System.out.print("\t" + dto.getCity());
				System.out.print("\t" + dto.getPhoneNo());

			}

		}

	}

}
