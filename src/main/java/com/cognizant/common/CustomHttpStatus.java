//package com.cognizant.common;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.lang.Nullable;
//
///**
// * Enumeration of HTTP status codes.
// *
// * <p>The HTTP status code series can be retrieved via {@link #series()}.
// *
// * @author Arjen Poutsma
// * @author Sebastien Deleuze
// * @author Brian Clozel
// * @since 3.0
// * @see HttpStatus.Series
// * @see <a href="http://www.iana.org/assignments/http-status-codes">HTTP Status Code Registry</a>
// * @see <a href="http://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes - Wikipedia</a>
// */
//public enum CustomHttpStatus {
//
//	USER_ALREADY_EXISTS(512, "User already exists");
//	
//	USER_CONSTRAINT_VOILATION(513, "User ");
//
//	private final int value;
//
//	private final String reasonPhrase;
//
//
//	CustomHttpStatus(int value, String reasonPhrase) {
//		this.value = value;
//		this.reasonPhrase = reasonPhrase;
//	}
//
//
//	/**
//	 * Return the integer value of this status code.
//	 */
//	public int value() {
//		return this.value;
//	}
//
//	/**
//	 * Return the reason phrase of this status code.
//	 */
//	public String getReasonPhrase() {
//		return this.reasonPhrase;
//	}
//
//	/**
//	 * Whether this status code is in the HTTP series
//	 * {@link org.springframework.http.HttpStatus.Series#INFORMATIONAL}.
//	 * This is a shortcut for checking the value of {@link #series()}.
//	 */
//	public boolean is1xxInformational() {
//		return Series.INFORMATIONAL.equals(series());
//	}
//
//	/**
//	 * Whether this status code is in the HTTP series
//	 * {@link org.springframework.http.HttpStatus.Series#SUCCESSFUL}.
//	 * This is a shortcut for checking the value of {@link #series()}.
//	 */
//	public boolean is2xxSuccessful() {
//		return Series.SUCCESSFUL.equals(series());
//	}
//
//	/**
//	 * Whether this status code is in the HTTP series
//	 * {@link org.springframework.http.HttpStatus.Series#REDIRECTION}.
//	 * This is a shortcut for checking the value of {@link #series()}.
//	 */
//	public boolean is3xxRedirection() {
//		return Series.REDIRECTION.equals(series());
//	}
//
//
//	/**
//	 * Whether this status code is in the HTTP series
//	 * {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR}.
//	 * This is a shortcut for checking the value of {@link #series()}.
//	 */
//	public boolean is4xxClientError() {
//		return Series.CLIENT_ERROR.equals(series());
//	}
//
//	/**
//	 * Whether this status code is in the HTTP series
//	 * {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR}.
//	 * This is a shortcut for checking the value of {@link #series()}.
//	 */
//	public boolean is5xxServerError() {
//		return Series.SERVER_ERROR.equals(series());
//	}
//
//	/**
//	 * Whether this status code is in the HTTP series
//	 * {@link org.springframework.http.HttpStatus.Series#CLIENT_ERROR} or
//	 * {@link org.springframework.http.HttpStatus.Series#SERVER_ERROR}.
//	 * This is a shortcut for checking the value of {@link #series()}.
//	 */
//	public boolean isError() {
//		return is4xxClientError() || is5xxServerError();
//	}
//
//	/**
//	 * Returns the HTTP status series of this status code.
//	 * @see HttpStatus.Series
//	 */
//	public Series series() {
//		return Series.valueOf(this);
//	}
//
//	/**
//	 * Return a string representation of this status code.
//	 */
//	@Override
//	public String toString() {
//		return Integer.toString(this.value);
//	}
//
//
//	/**
//	 * Return the enum constant of this type with the specified numeric value.
//	 * @param statusCode the numeric value of the enum to be returned
//	 * @return the enum constant with the specified numeric value
//	 * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
//	 */
//	public static CustomHttpStatus valueOf(int statusCode) {
//		CustomHttpStatus status = resolve(statusCode);
//		if (status == null) {
//			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
//		}
//		return status;
//	}
//
//
//	/**
//	 * Resolve the given status code to an {@code HttpStatus}, if possible.
//	 * @param statusCode the HTTP status code (potentially non-standard)
//	 * @return the corresponding {@code HttpStatus}, or {@code null} if not found
//	 * @since 5.0
//	 */
//	@Nullable
//	public static CustomHttpStatus resolve(int statusCode) {
//		for (CustomHttpStatus status : values()) {
//			if (status.value == statusCode) {
//				return status;
//			}
//		}
//		return null;
//	}
//
//
//	/**
//	 * Enumeration of HTTP status series.
//	 * <p>Retrievable via {@link HttpStatus#series()}.
//	 */
//	public enum Series {
//
//		INFORMATIONAL(1),
//		SUCCESSFUL(2),
//		REDIRECTION(3),
//		CLIENT_ERROR(4),
//		SERVER_ERROR(5);
//
//		private final int value;
//
//		Series(int value) {
//			this.value = value;
//		}
//
//		/**
//		 * Return the integer value of this status series. Ranges from 1 to 5.
//		 */
//		public int value() {
//			return this.value;
//		}
//
//		public static Series valueOf(int status) {
//			int seriesCode = status / 100;
//			for (Series series : values()) {
//				if (series.value == seriesCode) {
//					return series;
//				}
//			}
//			throw new IllegalArgumentException("No matching constant for [" + status + "]");
//		}
//
//		public static Series valueOf(CustomHttpStatus status) {
//			return valueOf(status.value);
//		}
//	}
//
//}