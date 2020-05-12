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
            //coordinates = gps.generate(customeraddress.getCity() + " " + customeraddress.getAddress());
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void getOrderCities(){
        getAllCities();
        try{
            for(CustomerAddress s: allCities){
                System.out.println(s.getCity() + ":" + s.getAddress());
                Coördinates c = gps.generate(customeraddress.getCity() + " " + customeraddress.getAddress());
                //System.out.println(c.getLatitude() + ":" + c.getLongtitude());
                //double distance = gps.calculateDistance(coordinates, c);
                //if(distance <= 50.00){
                  //  selectedCities.add(s);
                //}
            }

            System.out.println(selectedCities);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println(allCities);
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
