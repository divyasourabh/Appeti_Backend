package com.appeti.database.table.aggregation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.appeti.database.utils.DBUtils;
import com.appeti.mail.MailOutputStream;
import com.appeti.utils.ExceptionUtils;

public class SearchKeyword {
	
	private long keywordId;
	private String keyword;
	private int searchCount;
	
	private SearchKeyword(ResultSet rs) throws SQLException{
		this.keywordId = rs.getLong("keyword_id");
		this.keyword = rs.getString("keyword");
		this.searchCount = rs.getInt("search_count");
	}
	
	public static void save(String keyword){
		SearchKeyword obj = getKeyword(keyword);
		Connection con = null;
		PreparedStatement stmt = null;
		try{
			con = DBUtils.getConnection();
			if(obj != null){
				String query = "Update search_keyword set search_count = ?,last_modified = CURTIME() where keyword_id = ?";
				stmt = con.prepareStatement(query);
				int i = 1;
				stmt.setInt(i++, obj.getSearchCount() + 1);
				stmt.setLong(i, obj.getKeywordId());
				stmt.executeUpdate();
			}else{
				String query = "Insert into search_keyword (keyword) values (?)";
				stmt = con.prepareStatement(query);
				int i = 1;
				stmt.setString(i++, keyword.trim().toLowerCase());
				stmt.executeUpdate();
			}
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
	}
	
	public static SearchKeyword getKeyword(String keyword){
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "Select * from search_keyword where keyword = ?";
		try{
			con = DBUtils.getConnection();
		
		stmt = con.prepareStatement(query);
		stmt.setString(1,keyword.trim().toLowerCase());
		rs = stmt.executeQuery();
		if(rs.next())
			return new SearchKeyword(rs);
		}catch(Exception e){
			ExceptionUtils.logException(e);
		}finally{
			DBUtils.closeResultSet(rs);
			DBUtils.closeStatement(stmt);
			DBUtils.closeConnection(con);
		}
		return null;
	}

	public long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(long keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
}
