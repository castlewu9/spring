package com.sung.demo.alignment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@Table(name = "points")
public class Point {
	
	@Id @GeneratedValue
	@JsonIgnore
	private Long id;

	private float x;
	private float y;
	private float z;
	
	@Column(name = "align_id")
	@JsonIgnore
	private Long alignId;
	
	@JsonIgnore
	private Long ptsOrder;

}
