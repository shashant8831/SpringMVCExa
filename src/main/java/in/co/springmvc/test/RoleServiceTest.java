package in.co.springmvc.test;

import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.RoleServiceInt;

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
public class RoleServiceTest {

	@Autowired
	RoleServiceInt service;

	@Ignore
	public void testAdd() throws DuplicateRecordException {
		long record = 0L;
		RoleDTO dto = new RoleDTO();
		dto.setName("Faculty");
		dto.setDescription("Faculty");
		dto.setCreatedBy("admin@gmail.com");
		dto.setModifiedBy("admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		record = service.add(dto);

		if (record == 0) {
			System.out.println("Method Add Role Fails");
		} else {
			System.out.println("Role Sucessfully Added At " + dto.getId());
		}

	}

	@Ignore
	public void testUpdate() {
		long pk = 6L;
		RoleDTO dto = new RoleDTO();
		dto = service.findByPK(pk);
		dto.setName("Human Resource");
		dto.setDescription("Human Resourse");
		dto.setCreatedBy("admin@gmail.com");
		dto.setModifiedBy("admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			service.update(dto);
			RoleDTO updated_record = service.findByPK(pk);
			if (!" Tesla ".equals(updated_record)) {
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
		RoleDTO dto = new RoleDTO();
		long pk = 2L;
		dto.setId(pk);
		service.delete(pk);
		RoleDTO deleted_DTO = service.findByPK(pk);
		if (deleted_DTO != null) {
			System.out.println("Delete Role Fails");
		} else {
			System.out.println("Role Deleted At " + dto.getId());
		}

	}

	@Test
	public void testFindByPk() {
		RoleDTO dto = new RoleDTO();
		long pk = 3L;
		dto = service.findByPK(pk);

		if (dto == null) {
			System.out.println("No Record Found");
		} else {
			System.out.println("Record Found At " + dto.getId());
		}
		System.out.println("ID \tNAME \tDESCRIPTION");
		System.out.print("\n" + dto.getId());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getDescription());
	}

	@Ignore
	public void testFindByName() {

		RoleDTO dto = new RoleDTO();
		String roleName = "Admin";
		dto = service.findByName(roleName);
		if (dto == null) {
			System.out.println("No Record Found");
		} else {
			System.out.println("Record Found At: " + dto.getName());
		}
		System.out.println("ID \tNAME \tDESCRIPTION");
		System.out.print("\n" + dto.getId());
		System.out.print("\t" + dto.getName());
		System.out.print("\t" + dto.getDescription());

	}

	@Ignore
	public void testSearch() {
		RoleDTO dto = new RoleDTO();
		List list = new ArrayList();
		long pk = 2L;
		dto.setId(pk);
		list = service.search(dto, 1, 10);

		if (list.size() < 0) {
			System.out.println("No Record Found");
		} else {
			System.out.println("ID \tNAME \tDESCRIPTION");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (RoleDTO) it.next();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getName());
				System.out.print("\t" + dto.getDescription());

			}
		}
	}

}
