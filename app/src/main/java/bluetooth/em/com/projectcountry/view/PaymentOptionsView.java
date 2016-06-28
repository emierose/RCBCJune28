package bluetooth.em.com.projectcountry.view;

import java.util.ArrayList;

import bluetooth.em.com.projectcountry.data.ClientObjects;

/**
 * Created by Em on 6/28/2016.
 */
public interface  PaymentOptionsView extends ViewInterface {
    void displayPayment(ArrayList<ClientObjects> paymentmodes);
    MenuHolder getCredentials();
}
