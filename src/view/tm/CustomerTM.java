package view.tm;

public class CustomerTM {
    private String id;
    private String name;
    private  String Address;
    private  String province;
    private String postalCode;

    public CustomerTM() {
    }

    public CustomerTM(String id, String name, String address, String province, String postalCode) {
        this.setId(id);
        this.setName(name);
        setAddress(address);
        this.setProvince(province);
        this.setPostalCode(postalCode);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

   /*@Override
    public int compareTo(CustomerTM o) {
        return id.compareTo(o.getId());
    }*/
}
