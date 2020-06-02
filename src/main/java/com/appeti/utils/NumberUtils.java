package com.appeti.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * utiltity class for various number operations.
 *
 * @version $Revision$
 */
public class NumberUtils
{
	private static final String __CVS_VERSION = "@{#}CVS versionInfo: $Id$";

    private static final NumberFormat myNumberFormat = NumberFormat.getNumberInstance();
	
	/**
	 * Wrapper for Float.parseFloat(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed float.
	 */
	public static float stringToScalarFloat(String str, float defaultValue)
	{
		float value;

		try { value = (str != null) ? Float.parseFloat(str) : defaultValue; }
		catch (NumberFormatException ex) { value = defaultValue; }

		return value;
	}

	/**
	 * Wrapper for Float.valueOf(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed float.
	 */
	public static Float stringToFloat(String str, float defaultValue)
	{
		return new Float(stringToScalarFloat(str, defaultValue));
	}



	/**
	 * Wrapper for Double.parseDouble(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed double.
	 */
	public static double stringToScalarDouble(String str, double defaultValue)
	{
		double value;

		try { value = (str != null) ? Double.parseDouble(str) : defaultValue; }
		catch (NumberFormatException ex) { value = defaultValue; }

		return value;
	}

	/**
	 * Wrapper for Double.valueOf(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed double.
	 */
	public static Double stringToDouble(String str, double defaultValue)
	{
		return new Double(stringToScalarDouble(str, defaultValue));
	}

	/**
	 * Wrapper for Long.parseLong(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed long.
	 */
	public static long stringToScalarLong(String str, long defaultValue)
	{
		long value;

		try { value = (str != null) ? Long.parseLong(str) : defaultValue; }
		catch (NumberFormatException ex) { value = defaultValue; }

		return value;
	}

	/**
	 * Wrapper for Long.valueOf(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed Long.
	 */
	public static Long stringToLong(String str, long defaultValue)
	{
		return new Long(stringToScalarLong(str, defaultValue));
	}

	/**
	 * Wrapper for Integer.parseInt(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed long.
	 */
	public static int stringToScalarInt(String str, int defaultValue)
	{
		int value;

		try { value = (str != null) ? Integer.parseInt(str) : defaultValue; }
		catch (NumberFormatException ex) { value = defaultValue; }

		return value;
	}

	/**
	 * Wrapper for Long.valueOf(str) that returns <code>defaultValue</code>
	 * if <code>str</code> is null or a malformed Long.
	 */
	public static Integer stringToInteger(String str, int defaultValue)
	{
		return new Integer(stringToScalarInt(str, defaultValue));
	}

	/**
	 * Converts a Collection of Double's into an array of scalar doubles.
	 */
	public static double[] doublesToScalarArray(Collection doubleCollection)
	{
		double[] doubleArray = new double[doubleCollection.size()];

		int i = 0;
		for (Iterator iter = doubleCollection.iterator(); iter.hasNext(); ) {
			doubleArray[i++] = ((Double) iter.next()).doubleValue();
		}

		return doubleArray;
	}

	/**
	 * Converts a Collection of Long's into an array of scalar longs.
	 */
	public static long[] longsToScalarArray(Collection longCollection)
	{
		long[] longArray = new long[longCollection.size()];

		int i = 0;
		for (Iterator iter = longCollection.iterator(); iter.hasNext(); ) {
			longArray[i++] = ((Long) iter.next()).longValue();
		}

		return longArray;
	}

	/**
	 * Creates and initializes a Long's array of arraySize with initVal.
	 */
	public static long[] createAndInitLongsArray (int arraySize, long initVal) {
		arraySize = Math.max(arraySize, 1);
		List col = new ArrayList();
		while(--arraySize>=0) col.add(new Long(initVal));
		return NumberUtils.longsToScalarArray(col);
	}

	/**
	 * Creates and initializes a Double's array of arraySize with initVal.
	 */
	public static double[] createAndInitDoublesArray (int arraySize, double initVal) {
		arraySize = Math.max(arraySize, 1);
		List col = new ArrayList();
		while(--arraySize>=0) col.add(new Double(initVal));
		return NumberUtils.doublesToScalarArray(col);
	}

	/**
	 * Converts an array of longs into an array of Longs.
	 */
	public static Long[] scalarArrayToLongArray(long[] scalarArray)
	{
		Long[] longArray = new Long[scalarArray.length];

		for (int i = 0; i < scalarArray.length; i++) {
			longArray[i] = new Long(scalarArray[i]);
		}

		return longArray;
	}

	public static double roundToNearestNth(double num, int n) {
		double nearest = Math.pow(10,n);
		return Math.round(num * nearest) / nearest;
	}

	public static double roundToNearestNth(double num, int n, double d) {
		// 0.145 * 100 --> 14.5 (internal rep is actually 14.49999999999...) -->
		// 14.4999999999999999999 + 0.5 = 14.9999999999999999999 --> floor --> 14
		// add a very small number (like 0.0000001) to the number before rounding
		double nearest = Math.pow(10,n);
		return Math.round((num+d) * nearest) / nearest;
	}

	public static void main(String args[]) {
		double num = 101.2356;
		System.out.println("num = " +num);
		num = roundToNearestNth(num,2);
		System.out.println("num = " +num);
		System.out.println(Math.round( (0.145+0.0000000001)*100d ) / 100d );
		System.exit(0);
	}

    public static BigDecimal defaultBigDecimal(BigDecimal bd, BigDecimal defaultValue){
        return (bd == null) ? defaultValue:bd;
    }

	public static double parseDouble(String valueAsStr, double defaultValue){
		double parsedValue;
		try{
			parsedValue = myNumberFormat.parse(valueAsStr).doubleValue();
		}catch(ParseException e){
			parsedValue = defaultValue;
		}catch(NumberFormatException e){
			parsedValue = defaultValue;//this threw NFE one time, weird. being safe here.
		}
		return parsedValue;
	}

    public static BigDecimal defaultBigDecimal(BigDecimal bd){
        // Here, we are not calling defaultBigDecimal(BigDecimal bd, BigDecimal defaultValue)
        // because, we do need to construct new BigDecimal() unless bd==null
        return (bd == null) ? new BigDecimal(0.0):bd;
    }

    public static long longValue(BigDecimal bd){
        if (bd==null) {
            return 0;
}
        return Double.valueOf(bd.doubleValue()).longValue();
    }

    public static int intValue(BigDecimal bd){
        if (bd==null) {
            return 0;
        }
        return Double.valueOf(bd.doubleValue()).intValue();
    }

    public static int intValue (Long l) {
        if (l==null){return 0;}
        return l.intValue();
    }


}
