package student;

import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.CourierOperations;
import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.DistrictOperations;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;
import student.ts170426_CityOperations;
import student.ts170426_CourierOperations;
import student.ts170426_CourierRequestOperation;
import student.ts170426_DistrictOperations;
import student.ts170426_GeneralOperations;
import student.ts170426_PackageOperations;
import student.ts170426_UserOperations;
import student.ts170426_VehicleOperations;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;


public class StudentMain {

    public static void main(String[] args) {
        CityOperations cityOperations = new ts170426_CityOperations(); // Change this to your implementation.
        DistrictOperations districtOperations = new ts170426_DistrictOperations(); // Do it for all classes.
        CourierOperations courierOperations = new ts170426_CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new ts170426_CourierRequestOperation();
        GeneralOperations generalOperations = new ts170426_GeneralOperations();
        UserOperations userOperations = new ts170426_UserOperations();
        VehicleOperations vehicleOperations = new ts170426_VehicleOperations();
        PackageOperations packageOperations = new ts170426_PackageOperations();

        TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();
    }
}
