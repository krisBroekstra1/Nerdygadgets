package nerdygadgets.backoffice.main.data;

import nerdygadgets.backoffice.main.Gpscoördinate;
import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.Route.Coördinates;
import nerdygadgets.backoffice.main.Route.GPSCoördinaten;
import nerdygadgets.backoffice.main.Route.Route;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GenerateRouteCities {

    private CustomerAddress customeraddress;
    private ArrayList<CustomerAddress> allCities = new ArrayList<CustomerAddress>();
    private ArrayList<CustomerAddress> selectedCities = new ArrayList<CustomerAddress>();
    private double lat;
    private double lon;
    GPSCoördinaten gps;
    Coördinates coordinates;

    public GenerateRouteCities(CustomerAddress ca){
        try{
            this.customeraddress = ca;
            gps = new GPSCoördinaten();
            coordinates = gps.generate(customeraddress.getCity() + " " + customeraddress.getAddress());
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void getOrderCities(){
        getAllCities();
        selectedCities.add(this.customeraddress);
        try{
            for(CustomerAddress s: allCities){
                Coördinates c = gps.generate(s.getCity() + " " + s.getAddress());
                double distance = gps.calculateDistance(coordinates, c);
                if(distance <= 50.00){
                    s.setCoördinaten(c);
                    selectedCities.add(s);
                    System.out.println(s.getCity());
                }
            }
            System.out.println(selectedCities);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<CustomerAddress> getSelectedCities() {
        return selectedCities;
    }

    private void getAllCities(){
        ResultSet result = Driver.getOrderCities();
        try {
            while (result.next()) {
                CustomerAddress ca = new CustomerAddress(result.getString("City"), result.getString("adres"));
                ca.setName(result.getString("CustomerName"));
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
