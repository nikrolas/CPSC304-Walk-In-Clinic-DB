package Database;

/**
 * Created by Hayley on 2017-03-28.
 */
public class Contact {
    private Number aptHouseNumber;
    private String street;
    private String city;
    private String postalCode;
    private String province;
    private Number phoneNumber;
    private String notes;

    public Contact( Number aptHouseNumber, String street, String city, String postalCode, String province, Number phoneNumber, String notes){
        this.aptHouseNumber = aptHouseNumber;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }
    
    public String getCity(){
    	return city;
    }

    public Number getAptHouseNumber() {
        return aptHouseNumber;
    }

    public Number getPhoneNumber() {
        return phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getProvince() {
        return province;
    }

    public String getStreet() {
        return street;
    }

    public void setAptHouseNumber(Number aptHouseNumber) {
        this.aptHouseNumber = aptHouseNumber;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPhoneNumber(Number phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}


