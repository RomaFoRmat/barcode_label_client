package gui.service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

public final class PrintUtility {

    /**
     * Utility class; no construction!
     */
    private PrintUtility() {
    }

    /**
     * Retrieve a Print Service with a name containing the specified PrinterName; will return null if not found.
     */
    public static PrintService findPrintService(String printerName) {

        printerName = printerName.toLowerCase();

        PrintService service = null;

        // Get array of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();

        // Retrieve a print gui.service from the array
        for (int index = 0; service == null && index < services.length; index++) {

            if (services[index].getName().toLowerCase().contains(printerName)) {
                service = services[index];
            }
        }

        // Return the print gui.service
        return service;
    }

    /**
     * Retrieves a List of Printer Service Names.
     *
     * @return List
     */
    public static List<String> getPrinterServiceNameList() {

        // get list of all print services
        PrintService[] services = PrinterJob.lookupPrintServices();
        List<String> list = new ArrayList<>();

        for (PrintService service : services) {
            list.add(service.getName());
        }

        return list;
    }

//    public static String getZebraPrinter() {
//        List<String> printers = getPrinterServiceNameList();
//        for (String printer : printers) {
//            if (printer.toLowerCase().contains("zebra")) {
//                return printer;
//            }
//        }
//        return "";
//    }

    public static String getDefaultPrinter() {
        return PrintServiceLookup.lookupDefaultPrintService().getName();
    }
}