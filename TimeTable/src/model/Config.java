package model;

import java.util.Date;

public class Config {
	private int roomsPerSession;
	private int sessionsPerDay;
	private Date startDate;
	private Date endDate;
	
	public int getRoomsPerSession() {
		return roomsPerSession;
	}
	public void setRoomsPerSession(int roomsPerSession) {
		this.roomsPerSession = roomsPerSession;
	}
	public int getSessionsPerDay() {
		return sessionsPerDay;
	}
	public void setSessionsPerDay(int sessionsPerDay) {
		this.sessionsPerDay = sessionsPerDay;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}