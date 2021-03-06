package com.haoback.sys.entity;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import com.haoback.common.entity.BaseEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 系统用户
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity<Long> implements UserDetails {
	
	private static final long serialVersionUID = -1417067088396668589L;
	
	public static final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
	public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 50;
	
	@Column(name = "user_name", unique = true, nullable = false, length = 100)
	@Pattern(regexp = USERNAME_PATTERN, message = "{user.username.not.valid}")
	private String userName;
	
	@Column(name = "password")
	@Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{user.password.not.valid}")
	private String password;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "salt", length = 10)
	private String salt;

	/**
	 * 是否微信用户
	 */
	@Column(name = "is_wx_user")
	private Boolean isWxUser;

	/**
	 * 微信用户-昵称
	 */
	@Column(name = "nick_name", length = 50)
	private String nickName;

	/**
	 * 微信用户-城市
	 */
	@Column(name = "city", length = 50)
	private String city;

	/**
	 * 微信用户-国家
	 */
	@Column(name = "country", length = 50)
	private String country;

	/**
	 * 微信用户-语言
	 */
	@Column(name = "language", length = 20)
	private String language;

	/**
	 * 微信用户- 1-男 2-女 0-未知
	 */
	@Column(name = "gender", length = 1)
	private String gender;

	/**
	 * 微信用户-省
	 */
	@Column(name = "province", length = 50)
	private String province;

	/**
	 * 积分
	 */
	@Column(name = "integration")
	private Integer integration;
	
//	@Column(name = "role", length = 300)
//	private String role;

	@OneToMany(mappedBy = "sysUser", fetch = FetchType.EAGER)
	private List<SysUserRole> sysUserRoles;

	@Transient
	private Map<String, Set<String>> permissions;

	@Transient
	private Object menus;
	
	public SysUser() {}

	public SysUser(String userName, String password, String name, String salt) {
		super();
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.salt = salt;
//		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 生成salt
	 */
	public void randomSalt(){
		setSalt(RandomStringUtils.randomAlphanumeric(10));
	}

	/**
	 * 获取验证信息
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<SysUserRole> sysUserRoles = this.getSysUserRoles();
		if(CollectionUtils.isNotEmpty(sysUserRoles)){
//			String[] roles = role.split(",");
			for(SysUserRole sysUserRole : sysUserRoles){
				SysRole sysRole = sysUserRole.getSysRole();
				GrantedAuthority  authority = new SimpleGrantedAuthority(sysRole.getCode());
				authorities.add(authority);
			}
//			for(String r : roles){
//				GrantedAuthority  authority = new SimpleGrantedAuthority(r);
//				authorities.add(authority);
//			}
		}
		
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	/**
	 * 用户是否未过期
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 是否未被锁定
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 凭据是否过期
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否可用
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<SysUserRole> getSysUserRoles() {
		return sysUserRoles;
	}

	public void setSysUserRoles(List<SysUserRole> sysUserRoles) {
		this.sysUserRoles = sysUserRoles;
	}

	public Map<String, Set<String>> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<String, Set<String>> permissions) {
		this.permissions = permissions;
	}

	public Object getMenus() {
		return menus;
	}

	public void setMenus(Object menus) {
		this.menus = menus;
	}

	public Boolean getWxUser() {
		return isWxUser;
	}

	public void setWxUser(Boolean wxUser) {
		isWxUser = wxUser;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}
}
