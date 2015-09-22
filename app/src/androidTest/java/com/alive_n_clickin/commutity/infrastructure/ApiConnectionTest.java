package com.alive_n_clickin.commutity.infrastructure;

import android.test.AndroidTestCase;

/**
 * @author hjorthjort
 *         Created 22/09/15
 */
public class ApiConnectionTest extends AndroidTestCase {

    private final ApiConnection connection = new ApiConnection();
    private static final String TEST_QUERY = "dgw=Ericsson$Vin_Num_001&t1=1442922895000&t2=1442922899000";
    private static final String TEST_RESPONSE = "[{\"resourceSpec\":\"RMC_Value\",\"timestamp\":1442922897094,\"value\":\"$GPRMC,135456,A,5743,2398,N,1157,6162,E,,,2282015,,,A*89\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Cell_Id2_Value\",\"timestamp\":1442922897657,\"value\":\"00086315\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Cell_Id_Value\",\"timestamp\":1442922897657,\"value\":\"01B1A105\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Destination_Value\",\"timestamp\":1442922897568,\"value\":\"Lindholmen\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Journey_Name_Value\",\"timestamp\":1442922897568,\"value\":\"45\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Bus_Stop_Name_Value\",\"timestamp\":1442922897190,\"value\":\"Frihamnen\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Turn_Signals_Value\",\"timestamp\":1442922897480,\"value\":\"011\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Course_Value\",\"timestamp\":1442922897004,\"value\":\"105\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Accelerator_Pedal_Position_Value\",\"timestamp\":1442922897845,\"value\":\"50\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Total_Vehicle_Distance_Value\",\"timestamp\":1442922897752,\"value\":\"120814\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Authenticated_Users_Value\",\"timestamp\":1442922896790,\"value\":\"24\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Total_Online_Users_Value\",\"timestamp\":1442922896790,\"value\":\"26\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Rssi2_Value\",\"timestamp\":1442922897657,\"value\":\"-110\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Rssi_Value\",\"timestamp\":1442922897657,\"value\":\"-105\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Latitude_Value\",\"timestamp\":1442922897004,\"value\":\"57.720664\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Speed_Value\",\"timestamp\":1442922897004,\"value\":\"52.7\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Longitude_Value\",\"timestamp\":1442922897004,\"value\":\"11.96027\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Driver_Cabin_Temperature_Value\",\"timestamp\":1442922897286,\"value\":\"27.578844\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Ambient_Temperature_Value\",\"timestamp\":1442922897385,\"value\":\"25.111962\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Speed2_Value\",\"timestamp\":1442922896896,\"value\":\"11.8\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Altitude_Value\",\"timestamp\":1442922896897,\"value\":\"66.0\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Longitude2_Value\",\"timestamp\":1442922896896,\"value\":\"11.96027\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Course2_Value\",\"timestamp\":1442922896897,\"value\":\"359.0\",\"gatewayId\":\"Vin_Num_001\"},{\"resourceSpec\":\"Latitude2_Value\",\"timestamp\":1442922896896,\"value\":\"57.720664\",\"gatewayId\":\"Vin_Num_001\"}]";

    private static final String INCORRECT_QUERY = "apabepa91";

    public void testGetFromElectricity() {
        String response = connection.sendGetToElectricity(TEST_QUERY);
        assertEquals("Wrong response to Electricity API query", TEST_RESPONSE, response);

        response = connection.sendGetToElectricity(INCORRECT_QUERY);
        assertEquals("Response should be null", null, response);
    }
}
