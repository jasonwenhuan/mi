package com.chinaunicom.filterman.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * User: larry
 */
public class Logging {
    private static Logger transactionLog = Logger.getLogger("transaction");
    private static Logger requestActionLog = Logger.getLogger("requestAction");
    private static Logger infoLog = Logger.getLogger("info");
    private static Logger errorLog = Logger.getLogger("error");
    private static Logger debugLog = Logger.getLogger("debug");

    /**
     * Log all types of interaction with search engines. Transactions with Database, etc.
     *
     * @param desc
     */
    public static void logTransaction(String desc) {
        transactionLog.info(desc);
    }

    /**
     * Log all types of request data.
     *
     * @param desc
     */
    public static void logRequestAction(Object desc) {
        requestActionLog.info(desc);
    }

    /**
     * Log all severe error that cannot be resolved by server.
     *
     * @param desc
     * @param ex
     */
    public static void logError(String desc, Throwable ex) {
        errorLog.error(desc, ex);
    }


    /**
     * Log all severe error that cannot be resolved by server.
     *
     * @param desc
     */
    public static void logError(String desc) {
        errorLog.error(desc);
    }


    public static void logDebug(String desc) {
        debugLog.debug(desc);
    }

    /**
     * Log important server information.
     *
     * @param desc
     */
    public static void logInfo(String desc) {
        infoLog.info(desc);
    }

    /**
     * Log important error message that can be handled by server or not severe.
     *
     * @param desc
     * @param ex
     */
    public static void logInfo(String desc, Throwable ex) {
        infoLog.info(desc, ex);
    }

    public static String getStringData(Throwable th) {
        ThrowableInformation thInfo = new ThrowableInformation(th);
        String[] arr = thInfo.getThrowableStrRep();
        StringBuffer sb = new StringBuffer();
        for (String s : arr) {
            sb.append(String.format("%s%n", s));
        }

        return sb.toString();
    }

}
