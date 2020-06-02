package com.appeti.database.table.order;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.appeti.utils.ExceptionUtils;
import com.appeti.utils.Property;


public class Delivery {
	
	private static Properties properties = null;
	private static final String PROPERTY_FILE_PATH = "/properties/delivery.properties";
	
	public static final long HALF_KG = 50; 
	public static final long FULL_KG = 50; 
	
	private static Map<String, Long> map = null;
	
	public static long getFreeDeliveryAmount(){
		long amount = Property.DEFAULT_FREE_DELIVERY_AMOUNT;
		try{
			amount = Long.valueOf(properties.getProperty(Property.FREE_DELIVERY_AMOUNT));
		}catch(Exception e){}
		return amount;
	}
	
	public static long getCharge(String compressedUnitString){
		loadProperties();
		if(map == null){
			map = new HashMap<String, Long>();
			String mapStr = properties.getProperty(Property.DELIVERY_CHARGE);
			if(mapStr != null){
				String[] set = mapStr.split(",");
				for(String pair : set){
					if(StringUtils.isNotBlank(pair) && pair.split(":").length == 2){
						String key = pair.split(":")[0];
						String value = pair.split(":")[1];
						map.put(key, Long.valueOf(value));
					}
				}
			}
		}
		return map.get(compressedUnitString);
	}
	private static void loadProperties(){
		if(properties == null){
			try {
				properties = new Properties();
				properties.load(new FileInputStream(PROPERTY_FILE_PATH));
			} catch (FileNotFoundException e) {
				ExceptionUtils.logException(e);
			} catch (IOException e) {
				ExceptionUtils.logException(e);
			}
		}
	}
	
	/*private long id;
	private long charge;
	
	private static final String TABNAME = "delivery";
	
	public static long getDeliveryCharge(long tagId){
		Tag tag = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "tag_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				tag = new Tag(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return tag;
	}*/
}
