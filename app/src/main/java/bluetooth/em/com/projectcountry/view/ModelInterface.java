package bluetooth.em.com.projectcountry.view;

/**
 * Created by Em on 5/11/2016.
 */

import bluetooth.em.com.projectcountry.model.ModelObserverInterface;

/**
 * This is the base interface for all the Model implementing them as observables
 */
public interface ModelInterface {
    /**
     * Register an object to listens for updates from the model
     */
    public void registerObserver(ModelObserverInterface observer);

    /**
     * Unregister an object listening for updates from the model
     */
    public void unregisterObserver(ModelObserverInterface Observer);
}
