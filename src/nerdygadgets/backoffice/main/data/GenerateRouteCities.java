package nerdygadgets.backoffice.main.data;

import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.Route.Coördinates;
import nerdygadgets.backoffice.main.Route.GPSCoördinaten;

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
            System.out.println(customeraddress.getPostalcode());
            System.out.println(customeraddress.getAddress());

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void getOrderCities(){
        try{
            getAllCities(customeraddress.getProvince());
            System.out.println(customeraddress.getProvince());
            System.out.println(selectedCities);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<CustomerAddress> getSelectedCities() {
        return selectedCities;
    }

    private void getAllCities(String province){
        System.out.println(province);
        ResultSet result = Driver.getOrderCities(province);
        try {
            while (result.next()) {
                CustomerAddress ca = new CustomerAddress(result.getString("City"), result.getString("adres"), result.getString("province"));
                ca.setName(result.getString("CustomerName"));
                ca.setPostalcode(result.getString("Postalcode"));
                ca.setOrderid(result.getString("OrderID"));
                System.out.println(ca);
                if(result.getDouble("lat") == 0.00 && result.getDouble("long") == 0.00){
                    ca.setCoördinaten(gps.generate(result.getString("City") + " " + result.getString("adres")));
                    Driver.setCoördinates(ca.getCoördinaten().getLatitude(), ca.getCoördinaten().getLongtitude(), ca.getPostalcode());
                } else {
                    Coördinates c = new Coördinates(result.getDouble("long"), result.getDouble("lat"));
                    ca.setCoördinaten(c);
                }

                selectedCities.add(ca);
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
