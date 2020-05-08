package nerdygadgets.backoffice.main.data;

import nerdygadgets.backoffice.main.Gpscoördinate;
import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.Route.Route;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GenerateRouteCities {

    private CustomerAddress customeraddress;
    private ArrayList<CustomerAddress> allCities = new ArrayList<CustomerAddress>();
    private ArrayList<CustomerAddress> selectedCities = new ArrayList<CustomerAddress>();
    private double lat;
    private double lon;

    public GenerateRouteCities(CustomerAddress ca){
        this.customeraddress = ca;
    }

    public void getOrderCities(){
    }

    private void getAllCities(){
        ResultSet result = Driver.getOrderCities();
        try {
            while (result.next()) {
                CustomerAddress ca = new CustomerAddress();
                ca.setName(result.getString("CustomerName"));
                ca.setAddress(result.getString("adres"));
                ca.setCity(result.getString("City"));
                ca.setPostalcode(result.getString("Postalcode"));
                ca.setOrderid(result.getString("OrderID"));
                allCities.add(ca);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
