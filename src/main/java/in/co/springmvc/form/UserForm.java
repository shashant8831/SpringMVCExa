package in.co.springmvc.form;

import in.co.springmvc.dto.BaseDTO;
import in.co.springmvc.dto.UserDTO;
import in.co.springmvc.util.Util;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contains User form elements and their declarative input validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class UserForm extends BaseForm {

	@NotEmpty
	private String firstName;
	/**
	 * Last Name of User
	 */

	@NotEmpty
	private String lastName;

	@NotEmpty
	/* @Email */
	private String login;
	/**
	 * Password of User
	 */
	@NotEmpty
	private String password;

	/**
	 * Date of Birth of User
	 */
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@NotNull
	private Date dob;

	/**
	 * MobielNo of User
	 */

	/* @Pattern(regexp = "^[7-9][0-9]{9}$") */
	@NotEmpty
	private String mobileNo = null;

	/**
	 * Role of User
	 */
	@NotNull
	/*
	 * @Pattern(regexp = "[a-zA-Z]+", message =
	 * "Last Name is required and should be contain only alphabets")
	 */
	private Long roleId = 0l;
	/**
	 * Role Name of User
	 */
	private String roleName;
	/**
	 * Number of unsuccessful login attempt
	 */
	private int unSuccessfulLogin;
	/**
	 * Gender of User
	 */
	@NotEmpty
	private String gender;
	/**
	 * Last login long
	 */
	private long lastLogin;
	/**
	 * User Lock
	 */
	private String lock;

	/**
	 * IP Address of User from where User was registred.
	 */

	private String registeredIP;
	/**
	 * IP Address of User of his last login
	 */

	private String lastLoginIP;

	/*
	 * Accesor Methods
	 */

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getUnSuccessfulLogin() {
		return unSuccessfulLogin;
	}

	public void setUnSuccessfulLogin(int unSuccessfulLogin) {
		this.unSuccessfulLogin = unSuccessfulLogin;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getRegisteredIP() {
		return registeredIP;
	}

	public void setRegisteredIP(String registeredIP) {
		this.registeredIP = registeredIP;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunilos.proj0.form.BaseForm#getDto()
	 */
	@Override
	public BaseDTO getDto() {
		UserDTO dto = new UserDTO();

		dto.setId(id);
		dto.setFirstName(Util.getCapital(firstName));

		dto.setLastName(Util.getCapital(lastName));
		dto.setLogin(login);
		dto.setPassword(password);
		System.out.println(password);
		dto.setDob(dob);

		dto.setMobileNo(mobileNo);
		if (roleId != null) {
			dto.setRoleId(roleId);
		}
		dto.setLastLogin(new Timestamp(lastLogin));
		dto.setGender(gender);
		dto.setRegisteredIP(registeredIP);
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

		return dto;
	}

	@Override
	public void populate(BaseDTO bDto) {

		UserDTO dto = (UserDTO) bDto;

		id = dto.getId();
		firstName = Util.getCapital(dto.getFirstName());
		lastName = Util.getCapital(dto.getLastName());
		login = dto.getLogin();
		password = dto.getPassword();
		System.out.println(password);
		dob = dto.getDob();
		mobileNo = dto.getMobileNo();
		roleId = dto.getRoleId();
		if (dto.getLastLogin() != null) {
			lastLogin = dto.getLastLogin().getTime();
		}
		gender = dto.getGender();
		registeredIP = dto.getRegisteredIP();
		createdBy = dto.getCreatedBy();
		modifiedBy = dto.getModifiedBy();
		if (dto.getCreatedDatetime() != null) {
			createdDatetime = dto.getCreatedDatetime().getTime();
		}
		if (dto.getModifiedDatetime() != null) {
			modifiedDatetime = dto.getModifiedDatetime().getTime();
		}

		super.populate(bDto);
	}

}
