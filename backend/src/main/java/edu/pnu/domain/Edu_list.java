package edu.pnu.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//edu_list table 만들기
@Entity
@Table(name="pp_main")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edu_list {
	@Id
	@Column(name ="course_id")
	private Integer course_id;

	@Column(name="course_name")
	private String course_name;

	@Column(name="number")
	private String phone;

	@Column(name="email")
	private String email;

	@Column(name="address")
	private String address;

	@Column(name="address2")
	private String address2;

    @Column(name = "ncs_num", columnDefinition="varchar(8)")  // 데이터베이스 컬럼 이름과 매핑
    private String ncsCode;

    @Column(name="ncs_ko")
	private String ncsKorean;

	@Column(name="starrating")
	private Double starrating;

    @Column(name="training_type")
    private String trainType;

    @Column(name="train_time")
    private String trainTime;

	@Column(name = "ps")
	private double ps;

	@Column(name = "emp")
	private double emp;

	@Column(name ="summary_review")
	private String summaryReview;

	@Column(name="urls")
	private String urls;

	@Column(name="edu_institute")
	private String eduInstitute;

	@Transient // 데이터베이스에 저장되지 않도록 지정
	private Double score;
}
