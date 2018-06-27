package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_toshiba_key_config database table.
 * 
 */
@Entity
@Table(name="tbl_toshiba_key_config")
@NamedQuery(name="TblToshibaKeyConfig.findAll", query="SELECT t FROM TblToshibaKeyConfig t")
public class TblToshibaKeyConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String toshiba_key_name;

	private String toshiba_key_value;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddt;

	private String is_Enabled;

	private String status;

	public TblToshibaKeyConfig() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getToshiba_key_name() {
		return toshiba_key_name;
	}

	public void setToshiba_key_name(String toshiba_key_name) {
		this.toshiba_key_name = toshiba_key_name;
	}

	public String getToshiba_key_value() {
		return toshiba_key_value;
	}

	public void setToshiba_key_value(String toshiba_key_value) {
		this.toshiba_key_value = toshiba_key_value;
	}

	public Date getCreateddt() {
		return this.createddt;
	}

	public void setCreateddt(Date createddt) {
		this.createddt = createddt;
	}

	public String getIs_Enabled() {
		return this.is_Enabled;
	}

	public void setIs_Enabled(String is_Enabled) {
		this.is_Enabled = is_Enabled;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}