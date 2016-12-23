
package org.kth.controller.beans.carshop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.beyondjava.angularFaces.core.ELTools;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@SessionScoped
public class FilterBean implements Serializable {
	private static final Logger LOGGER = Logger.getLogger("org.kth.controller.carshop.FilterBean");
	
	private String brand;

	@ManagedProperty("#{carPool}")
	private transient CarPool carPool;

	private String color;

	@ManagedProperty("#{dynamicOptionBean}")
	private DynamicOptionBean dynamicOptions;

	private String fuel;

	private String mileage;
	
	private String price;

	private String type;

    private String yearText;

    public void doFilter(AjaxBehaviorEvent event) {
		LOGGER.info("doFilter called");
	}

    public String doFilterAction() {
		LOGGER.info("doFilterAction called");
		return null;
	}

	public String getBrand() {
		return brand;
	}

	public String getColor() {
		return color;
	}

	@JsonIgnore
	public String getCounter() {
		initIfNecessary();
		carPool.applyFilter(this);
		long l=carPool.getSelectedCars().size();
//		if (null != brand) return String.valueOf(l) + " " + brand; 
		return String.valueOf(l);
	}

	public DynamicOptionBean getDynamicOptions() {
		return dynamicOptions;
	}

	public String getFuel() {
		return fuel;
	}

	public String getMileage() {
		return mileage;
	}

	public String getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}
	
	@JsonIgnore
	public List<String> getTypes() {
		initIfNecessary();
		return dynamicOptions.getTypesToBrand(brand);
	}


	public int getYear() {
		if (yearText==null || yearText.length()<4) return 0;
		try {
			String year = yearText.substring(0, 4);
			return Integer.parseInt(year);
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

	public String getYearText() {
		return yearText;
	}

	/**
	 * This bean is created both by JSF and by the JSON deserializer (Jackson). In the latter case there's not JSF initialization, so we may have to catch up on it.
	 */
	private void initIfNecessary() {
		if (null==dynamicOptions) {
			dynamicOptions=(DynamicOptionBean) ELTools.evalAsObject("#{dynamicOptionBean}");
		}
		if (null==carPool) {
			carPool=(CarPool) ELTools.evalAsObject("#{carPool}");
		}
	}

	public void setBrand(String brand) {
		this.brand = brand;
		if (brand != null && type!=null && type.length()>0) {
			initIfNecessary();
			if (!brand.equals(dynamicOptions.getBrandToType(type)))
				type=null;
		}
		if (null != dynamicOptions) {
			dynamicOptions.setBrandAndType(brand, type);
		}
	}

	public void setCarPool(CarPool carPool) {
		this.carPool = carPool;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public void setDynamicOptions(DynamicOptionBean dynamicOptions) {
		this.dynamicOptions = dynamicOptions;
	}
	
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	
	public void setOptions(DynamicOptionBean options) {
		this.dynamicOptions = options;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	public void setType(String type) {
		this.type = type;
		initIfNecessary();
		if (null != dynamicOptions) {
			String b = dynamicOptions.getBrandToType(type);
			if (null != b && b.length()>0) {
				brand=b;
			}
			dynamicOptions.setBrandAndType(brand, type);
		}
	}
	
	public void setYearText(String year) {
		yearText=year;
	}
}
