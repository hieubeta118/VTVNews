package vccorp.project.cnnd.vtvnews.main.service;

import retrofit.RestAdapter;

/**
 * Created by Admin on 3/24/2016.
 */
public class ServiceManager {
    private static ServiceAPI serviceAPI;

    private ServiceManager() {
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(ServiceConfig.SERVICE_HOST).build();
        serviceAPI = restAdapter.create(ServiceAPI.class);
    }

    public static synchronized ServiceAPI getInstance() {
        if (serviceAPI == null) {
            new ServiceManager();
        }
        return serviceAPI;
    }
}
