package com.appeti.utils;

public final class Constants {
	public static final class LogActions{
		public final static int ADD_OPENING = 1;
		public final static int ADD_COMPANY = 2;
		public final static int ADD_TEAM = 3;
		public final static int ADD_PROFILE = 4;
		public final static int APPLY_OPENING = 5;
		public final static int EDIT_OPENING = 6;
		}
	
	public static final class ReviewType{
		public static final String SELLER = "SELLER";
		public static final String PRODUCT = "PRODUCT";
		public static final String BLOG = "BLOG";
	}
	
	public static final class RatingType{
		public final static int PRODUCT = 1;
		public final static int PTITLE = 2;
		public final static int SELLER = 3;
	}
	
	public static final class OrderBy{
		public static final int RELEVANCE = 1;
		public static final int RATING = 2;
		public static final int FRESHNESS = 3;
		public static final int PRICE_L2H = 4;
		public static final int PRICE_H2L = 5;
	}
	
	public static final class CartState{
		public static final String ACTIVE = "ACTIVE";
		public static final String ABANDONED = "ABANDONED";
		public static final String ORDERED = "ORDERED";
	}
	
	public static final class CouponScope{
		public static final String CART = "CART";
		public static final String PRODUCT = "PRODUCT";
		public static final String PTITLE = "PTITLE";
		public static final String TAG = "TAG";
		public static final String SELLER = "SELLER";
	}
	
	public static final class CouponState{
		public static final String ACTIVE = "ACTIVE";
		public static final String APPLIED = "APPLIED";
		public static final String PROCESSED = "PROCESSED";
		public static final String USED = "USED";
	}

	public static final class OrderState{
		public static final String ACTIVE = "ACTIVE";
		public static final String PROCESSING = "PROCESSING";
		public static final String ABANDONED = "ABANDONED";
		public static final String CANCELLED = "CANCELLED";
		public static final String ORDERED = "ORDERED";
		public static final String RECEIVED = "RECEIVED";
		public static final String DISPATCHED = "DISPATCHED";
		public static final String DELIVERED = "DELIVERED";
	}
	
	public static final class InvoiceState{
		public static final String NEW = "NEW";
		public static final String MAILED = "MAILED";
	}

	public static final class PaymentGateway{
		public static final int PAY_U = 1;
		public static final int CC_AVENUE = 2;
	}

	public static final class Source{
		public static final String PAY_U = "PAYU";
		public static final String CC_AVENUE = "CC-AVENUE";
	}
	
	public static final class PaymentStatus{
		public static final String SUCCESS = "Success";
		public static final String FAILURE = "Failure";
		public static final String ABORTED = "Aborted";
		public static final String INVALID = "Invalid";
	}
	
	public static final class PaymentResponse{
		public static final int FAILURE = 101;
		public static final int SUCCESS = 201;
	}
	
	public static final class ClickSaleSource{
		public static final String WEB = "WEB";
		public static final String APP = "APP";
	}
	
	public static final class ProductAvailability{
		public static final String IN_STOCK = "In Stock";
		public static final String OUT_OF_STOCK = "Out of Stock";
	}
}
