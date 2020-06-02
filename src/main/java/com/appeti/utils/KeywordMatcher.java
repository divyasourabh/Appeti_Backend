package com.appeti.utils;

import static org.simmetrics.StringMetricBuilder.with;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.CosineSimilarity;
import org.simmetrics.simplifiers.Case;
import org.simmetrics.simplifiers.NonDiacritics;
import org.simmetrics.tokenizers.QGram;
import org.simmetrics.tokenizers.Whitespace;

public class KeywordMatcher {
	
	public static double match(String query, SearchKeyword keyword){
		if(StringUtils.isBlank(query))
			return -1;
		String[] words = query.split(" ");
		double productScore = calculateScore(words, keyword.getProductName());
		double ptitleScore = calculateScore(words, keyword.getPtitleName());
		double unitScore = calculateScore(words, keyword.getUnit());
		double nodeScore = calculateScore(words, keyword.getLocation());
		double catScore = calculateScore(words, keyword.getCategory());
		
		double score = 17*ptitleScore + 13*productScore + 5*unitScore + 3*nodeScore + catScore;
		return score;
	}
	
	private static double calculateScore(String[] words, String keyword){
		double sum = 0;
		for(String query : words){
			try{
				StringMetric metric =
			            with(new CosineSimilarity<String>())
			            .simplify(new Case.Lower(Locale.ENGLISH))
			            .simplify(new NonDiacritics())
			            .simplifierCache()
			            .tokenize(new Whitespace())
			            .tokenize(new QGram(2))
			            .tokenizerCache()
			            .build();
				
		    	sum += metric.compare(query, keyword);
		    }catch(Exception e){e.printStackTrace();}
		}
		
		return words != null && words.length > 0 ? sum/words.length : 0;
	}
}
