/**
 * 
 */
package model;

/**
 * @author Akshay
 *
 */
public class TimeTableCourse {
	
	private int courseNum, courseIndex;
	private String courseName;
	private String instructorName;
	private int sessionsRequired;
	private int daysAvailable = 1;
	private int sessionsRemaining;
	
	
	public TimeTableCourse(int courseIdx, int courseNumber, String courseName, String instructorName, int sessionsRequired) {
		this.courseNum = courseNumber;
		this.courseName = courseName;
		this.instructorName = instructorName;
		this.sessionsRequired = sessionsRequired;
		this.sessionsRemaining = this.sessionsRequired;
		courseIndex = courseIdx;
	}
	
	public int getCourseNumber() {
		return courseNum;
	}
	
	public int getCourseIndex() {
		return courseIndex;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getInstructorName() {
		return instructorName;
	}
	
	public int getSessionsRequired() {
		return sessionsRequired;
	}
	
	public String toString() {
		String retVal = "[Course:\nNumber: " + courseNum + "\nName: " + courseName + "\nInstructor: " + instructorName + "\nSessions Required: " + sessionsRequired + "\nIndex: " + courseIndex + "\n]";
		return retVal;
	}
	
	public int getDaysAvailable() {
		return daysAvailable;
	}
	
	public void setDaysAvailable(int days) {
		daysAvailable = days;
	}
	
	public int getSessionsRemaining() {
		return sessionsRemaining;
	}
	
	public void setSessionsRemaining(int sessions) {
		sessionsRemaining = sessions;
	}
}
