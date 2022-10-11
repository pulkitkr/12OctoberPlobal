//package com.utility;
//
//import com.aventstack.extentreports.markuputils.ExtentColor;
//
//import com.utility.LoggingUtils;
//import org.testng.Assert;
//import org.testng.ITestResult;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class CustomSoftAssert  {
//    private List<Throwable> m_errors;
//    static LoggingUtils Logger = new LoggingUtils();
//    public CustomSoftAssert() {
//        m_errors = new LinkedList<>();
//    }
//
//    public void assertTrue(boolean condition, String message) {
//        try {
//            Assert.assertTrue(condition, message);
//
//            Logger.info(message + " = <b>Pass</b>");
//        } catch (Throwable e) {
//            Logger.error(message + " = <b>Fail</b>", ExtentColor.RED);
//          
//            m_errors.add(e);
//        }
//    }
//
//    public void assertContainsIgnoreCase(String completeString, String subString, String message) {
//        String str = message + "<br><b>Complete String : </b>" + completeString + "<br><b>Substring : </b>" + subString;
//
//        try {
//            String errorMessage = message + " -- \nComplete String : " + completeString + "\nSubstring : " + subString + "\n\n";
//            Assert.assertTrue(completeString.toLowerCase().contains(subString.toLowerCase()), errorMessage);
//            Logger.info(str);
//        } catch (Throwable e) {
//           // Logger.error(str + " = <b>Fail</b>", ExtentColor.RED);
//            m_errors.add(e);
//        }
//    }
//
//    public void assertEquals(String actual, String expected, String message) {
//        String str = message + "<br><b>Actual : </b>" + actual + "<br><b>Expected : </b>" + expected;
//
//        try {
//            Assert.assertEquals(actual, expected, message);
//            Logger.info(str);
//        } catch (Throwable e) {
//            Logger.error(str, ExtentColor.RED);
//            m_errors.add(e);
//        }
//    }
//
//    public void assertEqualsIgnoreCase(String actual, String expected, String message) {
//        String str = message + "<br><b>Actual : </b>" + actual + "<br><b>Expected : </b>" + expected;
//
//        try {
//            Assert.assertEquals(actual.toLowerCase(), expected.toLowerCase(), message);
//            Logger.info(str);
//        } catch (Throwable e) {
//            Logger.error(str, ExtentColor.RED);
//            m_errors.add(e);
//        }
//
//    }
//
//    public void assertEquals(double actual, double expected, String message) {
//        String str = message + "<br><b>Actual : </b>" + actual + "<br><b>Expected : </b>" + expected;
//
//        try {
//            Assert.assertEquals(actual, expected, message);
//            Logger.info(str);
//        } catch (Throwable e) {
//            Logger.error(str, ExtentColor.RED);
//            m_errors.add(e);
//        }
//    }
//
//    public void assertEquals(int actual, int expected, String message) {
//        String str = message + "<br><b>Actual : </b>" + actual + "<br><b>Expected : </b>" + expected;
//
//        try {
//            Assert.assertEquals(actual, expected, message);
//            Logger.info(str);
//        } catch (Throwable e) {
//            Logger.error(str, ExtentColor.RED);
//            m_errors.add(e);
//        }
//    }
//
//    public void assertContains(String completeString, String subString, String message) {
//        String str = message + "<br><b>Complete String : </b>" + completeString + "<br><b>Substring : </b>" + subString;
//        String errorMessage = message + " -- \nComplete String : " + completeString + "\nSubstring : " + subString + "\n\n";
//        try {
//            Assert.assertTrue(completeString.contains(subString), errorMessage);
//            Logger.info(str);
//        } catch (Throwable e) {
//            Logger.error(str, ExtentColor.RED);
//            m_errors.add(e);
//        }
//    }
//
//
//
//
//    public void assertAll() {
//        StringBuilder customMessage = new StringBuilder();
//        int size = m_errors.size();
//
//        if (size > 0) {
//            customMessage.append("Total Assertion Failures = [" + size + "]" + System.lineSeparator());
//
//            int i = 1;
//            for (Throwable throwable : m_errors) {
//                customMessage.append(System.lineSeparator());
//                customMessage.append("Failure " + i + " of " + size + " :" + System.lineSeparator());
//                customMessage.append(throwable.getLocalizedMessage());
//                customMessage.append(System.lineSeparator());
//                i++;
//            }
//
//            throw new AssertionError(customMessage);
//        }
//    }
//    public void assertFalse(boolean condition, String message) {
//        try {
//            Assert.assertFalse(condition, message);
//            Logger.info(message + " = <b>Pass</b>");
//        } catch (Throwable e) {
//            Logger.error(message + " = <b>Fail</b>", ExtentColor.RED);
//            m_errors.add(e);
//        }
//    }
//
//}
