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
 * Contains User Registration form elements and their declarative input
 * validations.
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public class UserRegistrationForm extends BaseForm {

	/* @Pattern(regexp = "[a-zA-Z]+") */
	/* @NotEmpty */
	private String firstName;
	/**
	 * Last Name of User
	 */
	/* @Pattern(regexp = "[a-zA-Z]+") */
	/* @NotEmpty */
	private String lastName;

	/* @Pattern(regexp = "\\+?[7-9][0-9]*") */
	/* @NotEmpty */
	private String mobileNo = null;

	/* @NotEmpty */
	/* @Email */
	private String login;
	/**
	 * Password of User
	 */
	@NotEmpty
	private String password;

	@NotEmpty
	private String confirmpassword;

	/**
	 * Date of Birth of User
	 */
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@NotNull
	private Date dob;

	/**
	 * MobielNo of User
	 */

	/**
	 * Gender of User
	 */
	@NotEmpty
	private String gender;

	private long lastLogin;

	private String lock;

	/**
	 * IP Address of User from where User was registred.
	 */

	private String registeredIP;
	/**
	 * IP Address of User of his last login
	 */

	private String lastLoginIP;

	private Long roleId = 0l;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

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

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public BaseDTO getDto() {
		UserDTO dto = new UserDTO();

		dto.setId(id);
		dto.setFirstName(Util.getCapital(firstName));
		dto.setLastName(Util.getCapital(lastName));
		dto.setLogin(login);
		dto.setPassword(password);
		dto.setConfirmPassword(confirmpassword);
		dto.setDob((dob));
		dto.setMobileNo(mobileNo);
		dto.setGender(gender);
		dto.setLastLogin(new Timestamp(lastLogin));
		dto.setRegisteredIP(registeredIP);
		dto.setRoleId(roleId);
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
		confirmpassword = dto.getConfirmPassword();
		dob = dto.getDob();
		mobileNo = dto.getMobileNo();
		roleId = dto.getRoleId();
		gender = dto.getGender();
		if (dto.getLastLogin() != null) {
			lastLogin = dto.getLastLogin().getTime();
		}
		registeredIP = dto.getRegisteredIP();
	}

}
