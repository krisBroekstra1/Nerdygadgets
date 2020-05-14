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
    private double straal = 500;

    public GenerateRouteCities(CustomerAddress ca){
        try{
            this.customeraddress = ca;
            gps = new GPSCoördinaten();
            coordinates = gps.generate(customeraddress.getCity() + " " + customeraddress.getAddress());
            this.customeraddress.setCoördinaten(coordinates);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public void getOrderCities(){
        getAllCities();
        System.out.println(customeraddress.getOrderid());
        try{
            for(CustomerAddress s: allCities){
                Coördinates c = gps.generate(s.getCity() + " " + s.getAddress());
                double distance = gps.calculateDistance(coordinates, c);
                System.out.println(s.getOrderid());
                if(distance <= straal){
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

    public void setStraal(double straal) {
        this.straal = straal;
    }
}
