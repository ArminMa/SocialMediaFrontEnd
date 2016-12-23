/**
 *  (C) 2013-2014 Stephan Rauh http://www.beyondjava.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kth.controller.beans.carshop;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;
import org.kth.util.gsonX.GsonX;

@XmlRootElement
@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_EMPTY)
public class Car implements Serializable, Comparable<Car>{
	String brand;

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@NotEmpty
	String color;

	String type;

	@Min(1886)
	@Max(2014)
	@NotEmpty
	int year;

	@Min(0)
	@Max(1000000)
	@NotEmpty
	int mileage;

	@NotEmpty
	String fuel;

	@Min(1)
	@Max(5000000)
	int price;

	public Car() {
	}

	public Car(String brand, String type, int year, String color, int mileage, String fuel, int price) {
		this.brand = brand;
		this.type = type;
		this.year = year;
		this.color = color;
		this.mileage = mileage;
		this.fuel = fuel;
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public String getColor() {
		return color;
	}

	public String getType() {
		return type;
	}

	public int getYear() {
		return year;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public int compareTo(Car o) {
		int thisTime = this.hashCode();
		long anotherEntity = o.hashCode();
		return (thisTime<anotherEntity ? -1 : (thisTime==anotherEntity ? 0 : 1));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Car car = (Car) o;

		if (year != car.year) return false;
		if (mileage != car.mileage) return false;
		if (price != car.price) return false;
		if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
		if (color != null ? !color.equals(car.color) : car.color != null) return false;
		if (type != null ? !type.equals(car.type) : car.type != null) return false;
		return fuel != null ? fuel.equals(car.fuel) : car.fuel == null;
	}

	@Override
	public int hashCode() {
		int result = brand != null ? brand.hashCode() : 0;
		result = 31 * result + (color != null ? color.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + year;
		result = 31 * result + mileage;
		result = 31 * result + (fuel != null ? fuel.hashCode() : 0);
		result = 31 * result + price;
		return result;
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}

}
