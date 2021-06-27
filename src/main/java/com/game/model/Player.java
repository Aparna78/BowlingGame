package com.game.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Player {

  private @Id @GeneratedValue Long id;
  private String name;
  private String laneNumber;

  Player() {}

  Player(String name, String role) {

    this.name = name;
    this.laneNumber = role;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getRole() {
    return this.laneNumber;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(String role) {
    this.laneNumber = role;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Player))
      return false;
    Player employee = (Player) o;
    return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
        && Objects.equals(this.laneNumber, employee.laneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.laneNumber);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.laneNumber + '\'' + '}';
  }
}