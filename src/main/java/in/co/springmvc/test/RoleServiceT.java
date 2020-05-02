package in.co.springmvc.test;

import in.co.springmvc.dto.RoleDTO;
import in.co.springmvc.exception.DuplicateRecordException;
import in.co.springmvc.service.RoleServiceInt;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class RoleServiceT {

	@Autowired
	public static RoleServiceInt service;

	/* public static RoleServiceInt service = new RoleServiceSpringImpl(); */
	public static RoleDTO dto = new RoleDTO();

	public static void main(String[] args) {

		testAdd();

	}

	private static void testAdd() {

		ApplicationContext context = new FileSystemXmlApplicationContext(
	"C:/Project Reserve/ORS_Project0/src/main/webapp/WEB-INF/dispatcher-servlet.xml");

		dto.setName("Admin");
		dto.setDescription("Administrator");
		dto.setCreatedBy("Robin@gmail.com");
		dto.setModifiedBy("Robin@gmail.com");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			long pk = service.add(dto);
			RoleDTO addedDTO = service.findByPK(pk);
			if (addedDTO == null) {
				System.out.println("Method Add Role Fails");
			} else {
				System.out.println("Role Added At " + dto.getId());
			}
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}
}
