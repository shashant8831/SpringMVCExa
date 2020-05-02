package in.co.springmvc.dto;

import java.sql.Timestamp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User DTO class encapsulates User attributes
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
@Entity
@Table(name = "st_user")
public class UserDTO extends BaseDTO {

	/**
	 * Lock Active constant for User
	 */
	public static final String ACTIVE = "Active";
	/**
	 * Lock Inactive constant for User
	 */
	public static final String INACTIVE = "Inactive";
	/**
	 * First Name of User
	 */

	@Column(name = "FIRST_NAME", length = 50)
	private String firstName;
	/**
	 * Last Name of User
	 */
	@Column(name = "LAST_NAME", length = 50)
	private String lastName;
	/**
	 * Login of User
	 */
	@Column(name = "LOGIN", length = 50)
	private String login;
	/**
	 * Password of User
	 */
	@Column(name = "PASSWORD", length = 50)
	private String password;
	/**
	 * Confirm Password of User
	 */
	@Column(name = "CONFIRM_PASSWORD", length = 50)
	private String confirmPassword;
	/**
	 * Date of Birth of User
	 */
	@Column(name = "DOB", length = 50)
	private Date dob;
	/**
	 * MobielNo of User
	 */
	@Column(name = "MOBILE_NO", length = 50)
	private String mobileNo;
	/**
	 * Role of User
	 */
	@Column(name = "ROLE_ID", length = 50)
	private long roleId;

	@Column(name = "ROLE_NAME", length = 50)
	private String roleName;

	/**
	 * Number of unsuccessful login attempt
	 */
	@Column(name = "UNSUCCESSFUL_LOGIN", length = 50)
	private int unSuccessfulLogin;
	/**
	 * Gender of User
	 */
	@Column(name = "GENDER", length = 50)
	private String gender;
	/**
	 * Last login timestamp
	 */
	@Column(name = "LASTLOGIN", length = 50)
	private Timestamp lastLogin;
	/**
	 * User Lock
	 */
	@Column(name = "USER_LOCK", length = 50)
	private String lock = INACTIVE;

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * IP Address of User from where User was registred.
	 */
	@Column(name = "REGISTERED_IP", length = 50)
	private String registeredIP;
	/**
	 * IP Address of User of his last login
	 */
	@Column(name = "LAST_LOGIN_IP", length = 50)
	private String lastLoginIP;

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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
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

	public String getKey() {
		return id + "";
	}

	public String getValue() {

		return firstName + " " + lastName;
	}

}