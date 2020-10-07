package com.subscriptiontracker.entity;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
public class Subscription {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;
	@Basic
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate startDate;
	private String payInterval;
	private String name;
	private String status;
	private Integer freePeriod;
	private Float price;
	private Boolean reminder;
	
	public Subscription() {
		super();
	}
	public Subscription(Integer id, Integer userId, LocalDate startDate, String payInterval, String name, String status,Integer freePeriod,  Float price,Boolean reminder) {
		super();
		this.id = id;
		this.userId = userId;
		this.startDate = startDate;
		this.payInterval = payInterval;
		this.name = name;
		this.status = status;
		this.freePeriod=freePeriod;
		this.price=price;
		this.reminder=reminder;
	}
	
	public Integer getFreePeriod() {
		return freePeriod;
	}
	public void setFreePeriod(Integer freePeriod) {
		this.freePeriod = freePeriod;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public String getPayInterval() {
		return payInterval;
	}
	public void setPayInterval(String payInterval) {
		this.payInterval = payInterval;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Boolean getReminder() {
		return reminder;
	}
	public void setReminder(Boolean reminder) {
		this.reminder = reminder;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((freePeriod == null) ? 0 : freePeriod.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((payInterval == null) ? 0 : payInterval.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscription other = (Subscription) obj;
		if (freePeriod == null) {
			if (other.freePeriod != null)
				return false;
		} else if (!freePeriod.equals(other.freePeriod))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (payInterval == null) {
			if (other.payInterval != null)
				return false;
		} else if (!payInterval.equals(other.payInterval))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", startDate=" + startDate + ", payInterval="
				+ payInterval + ", name=" + name + ", status=" + status + ", freePeriod=" + freePeriod + ", price="
				+ price + "]";
	}
	
	
	
}
