package com.appeti.database.table.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.appeti.database.table.review.Rating;
import com.appeti.database.utils.DBUtils;
import com.appeti.utils.ExceptionUtils;

public class SellerRating extends Rating{
	
	private static final String TABNAME = "seller_rating";
	private static final int COUNT_THRESHOLD = 5;
	
	private SellerRating(ResultSet rs) throws SQLException{
		super(rs);
		this.id = rs.getLong("seller_id");
	}
	
	public static double getSellerRating(long sellerId){
		List<SellerRating> ratings = getAllRatings(sellerId);
		int count = 0;
		int thumbs = 0;
		int totalRating = 0;
		for(SellerRating rating : ratings){
			count ++;
			if(rating.getRating() != -1)
				totalRating = totalRating + rating.getRating();
			if(rating.isThumbsUp())
				thumbs ++;
			if(rating.isThumbsDown())
				thumbs --;
		}
		return getScore(count,thumbs,totalRating);
	}
	
	private static List<SellerRating> getAllRatings(long sellerId){
		List<SellerRating> list = new ArrayList<SellerRating>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String query = DBUtils.prepareSelectQuery(null, TABNAME , "seller_id = ?");
		try{
			con = DBUtils.getConnection();
			stmt = con.prepareStatement(query);
			stmt.setLong(1, sellerId);
			rs = stmt.executeQuery();
			while(rs.next())
				list.add(new SellerRating(rs));
		}catch(Exception e){
			ExceptionUtils.logException(e,query);
		}finally{
			DBUtils.closeAll(rs,stmt,con);
		}
		return list;
	}
	
	private static double getScore(int count, int thumbs, int rating){
		double score = 0;
		if(count >= COUNT_THRESHOLD){
			score = thumbs/count + rating/(5*count) + Math.log10(count)/5;
		}
		return score;
	}
}
