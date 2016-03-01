package com.myproject.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "CUSTOMER")

public class Customer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "type")
  private String type;

	@Column(name = "sex")
	private String sex;

  /**
   * @return the id
   */
	public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
	public void setId(int id) {
    this.id = id;
  }



  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
  @Override
  public int hashCode() {
		return Objects.hashCode(super.hashCode(), id, firstName, lastName, type, sex);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Customer)) {
      return false;
    }
    final Customer other = (Customer) obj;

    return Objects.equal(this.id, other.id)
 && Objects.equal(this.firstName, other.firstName)
				&& Objects.equal(this.lastName, other.lastName)
      && Objects.equal(this.type, other.type)

				&& Objects.equal(this.sex, other.sex)
    ;

  }

}
