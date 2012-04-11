package com.huawei.sqm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class DemoDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@GenericGenerator(name = "fk", strategy = "foreign", parameters = { @Parameter(name = "property", value = "demo") })
	private long id;

	private String name;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Demo demo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Demo getDemo() {
		return demo;
	}

	public void setDemo(Demo demo) {
		this.demo = demo;
	}

}
