package in.co.springmvc.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.UserServiceInt;

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
public class UserServiceTest {

	@Autowired
	UserServiceInt service;

	public static UserDTO dto = new UserDTO();
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Ignore
	public void testAdd() throws ParseException {

		dto.setFirstName("JACKIE");
		dto.setLastName("PAMNANI");
		dto.setLogin("jackie@gmail.com");
		dto.setPassword("123456");
		dto.setDob(sdf.parse("10/7/1992"));
		dto.setMobileNo("9854688965");
		dto.setRoleId(2L);
		dto.setGender("Male");
		dto.setUnSuccessfulLogin(4);
		dto.setLastLogin(new Timestamp(new Date().getTime()));
		dto.setLock("EXCLUSIVE");
		dto.setRegisteredIP("192.168.121.11");
		dto.setLastLoginIP("192.168.121.11");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			long pk = service.add(dto);
			UserDTO addded_DTO = service.findByPK(pk);
			if (addded_DTO == null) {
				System.out.println("METHOD ADD FAILS");
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
		dto.setFirstName("Abhijaat");
		dto.setLastName("joshi");
		dto.setLogin("joshi@gmail.com");
		dto.setPassword("789456");
		dto.setDob(sdf.parse("08/12/1993"));
		dto.setMobileNo("7860808080");
		dto.setRoleId(5L);
		dto.setUnSuccessfulLogin(2);
		dto.setGender("Female");
		dto.setLastLogin(new Timestamp(new Date().getTime()));
		dto.setLock("EXCLUSIVE");
		dto.setRegisteredIP("192.168.121.11");
		dto.setLastLoginIP("192.168.121.11");
		dto.setCreatedBy("super_admin@gmail.com");
		dto.setModifiedBy("super_admin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			service.update(dto);
			UserDTO updated_DTO = service.findByPK(pk);
			if (!" Tesla ".equals(updated_DTO)) {
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
		UserDTO deleted_DTo = service.findByPK(pk);
		if (deleted_DTo == null) {
			System.out.println("DELETE METHOD FAILS");
		} else {
			System.out.println("RECORD DELETED AT " + dto.getId());
		}

	}

	@Ignore
	public void testFindByPk() {
		long pk = 4L;
		dto = service.findByPK(pk);
		if (dto == null) {
			System.out.println("RECORD NOT FOUND");
		} else {
			System.out
					.println("ID \tFIRST_NAME \tLAST_NAME \tLOGIN \tPASSWORD \tDOB \tMOBILE_NO \tROLE_ID \tROLE_NAME");

			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getFirstName());
			System.out.print("\t" + dto.getLastName());
			System.out.print("\t" + dto.getLogin());
			System.out.print("\t" + dto.getPassword());
			System.out.print("\t" + sdf.format(dto.getDob()));
			System.out.print("\t" + dto.getMobileNo());
			System.out.print("\t" + dto.getRoleId());
			System.out.print("\t" + dto.getRoleName());

		}
	}

	@Ignore
	public void testFindByLogin() {
		dto = service.findByLogin("namratha@gmail.com");
		if (dto == null) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tFIRST_NAME \tLAST_NAME \tLOGIN \tPASSWORD \tDOB \tMOBILE_NO \tROLE_ID \tROLE_NAME");

			System.out.print("\n" + dto.getId());
			System.out.print("\t" + dto.getFirstName());
			System.out.print("\t" + dto.getLastName());
			System.out.print("\t" + dto.getLogin());
			System.out.print("\t" + dto.getPassword());
			System.out.print("\t" + sdf.format(dto.getDob()));
			System.out.print("\t" + dto.getMobileNo());
			System.out.print("\t" + dto.getRoleId());
			System.out.print("\t" + dto.getRoleName());

		}

	}

	@Test
	public void testSearch() {
		List list = new ArrayList();
		long pk = 4L;
		dto.setId(pk);
		list = service.search(dto, 1, 10);
		if (list.size() == 0) {
			System.out.println("NO RECORD FOUND");
		} else {
			System.out
					.println("ID \tFIRST_NAME \tLAST_NAME \tLOGIN \tPASSWORD \tDOB \tMOBILE_NO \tROLE_ID \tROLE_NAME");
			Iterator it = list.iterator();
			while (it.hasNext()) {
				dto = (UserDTO) it.next();
				System.out.print("\n" + dto.getId());
				System.out.print("\t" + dto.getFirstName());
				System.out.print("\t" + dto.getLastName());
				System.out.print("\t" + dto.getLogin());
				System.out.print("\t" + dto.getPassword());
				System.out.print("\t" + dto.getDob());
				System.out.print("\t" + dto.getMobileNo());
				System.out.print("\t" + dto.getRoleId());
				System.out.print("\t" + dto.getRoleName());

			}

		}

	}
}
