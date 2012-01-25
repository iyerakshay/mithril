/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Akshay
 *
 */
public class RuleBasedTimeTable extends TimeTable implements Iterator<TimeTableDay> {
	
	private Date startDate, endDate;
	boolean[][] dayPreferences;
	TimeTableCourse[] courses;
	int count = 0;
	int numSessions = 0, numRooms = 0;
	
	public RuleBasedTimeTable(Config runConfig) throws Throwable {
		CourseDetails courseDetails = CourseDetails.getCourseDetails(runConfig.getCourseDetailsFile());
		if(courseDetails == null) throw new Exception("Invalid course details file");
		
		courses = courseDetails.getCoursesAsArray();
		numSessions = runConfig.getSessionsPerDay();
		numRooms = runConfig.getRoomsPerSession();
		
		dayPreferences = runConfig.getDayPreferences();
		startDate = runConfig.getStartDate();
		endDate = runConfig.getEndDate();
		
		/*
		int count = 0;
		int coursesCount = dayPreferences.length;
		while(startDate.before(runConfig.getEndDate())) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, 1);
			
			startDate = calendar.getTime();
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) continue;
			
			List<TimeTableCourse> candidateCourses = new ArrayList<TimeTableCourse>();
			for(int courseIndex = 0; courseIndex < coursesCount; courseIndex++) {
				if(dayPreferences[courseIndex][count])
					candidateCourses.add(courses[courseIndex]);
			}
			
			count++;
		}
		*/
	}

	@Override
	public Iterator<TimeTableDay> getTimeTableIterator() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean hasNext() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return  startDate.before(endDate);
		}
		return !(startDate.after(endDate));
	}

	@Override
	public TimeTableDay next(){
		while(true) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				calendar.add(Calendar.DATE, 1);
				startDate = calendar.getTime();
				continue;
			}
			
			List<TimeTableCourse> candidateCourses = new ArrayList<TimeTableCourse>();
			for(int courseIndex = 0; courseIndex < courses.length; courseIndex++) {
				if((courses[courseIndex].getSessionsRequired() > 0) && dayPreferences[courseIndex][count] && (calendar.get(Calendar.DAY_OF_WEEK)/5 == courses[courseIndex].getCourseIndex()%2) )
					candidateCourses.add(courses[courseIndex]);
			}
			if(candidateCourses.size() == 0) { //no courses could be found. relax the constraint
				for(int courseIndex = 0; courseIndex < courses.length; courseIndex++) {
					if((courses[courseIndex].getSessionsRequired() > 0) && dayPreferences[courseIndex][count])
						candidateCourses.add(courses[courseIndex]);
				}
			}
			
			count++;
			
			TimeTableDay day = new TimeTableDay(count, startDate, numSessions);
			
			if(candidateCourses.size() == 0) {
				calendar.add(Calendar.DATE, 1);
				startDate = calendar.getTime();
				return day;
			}
			
//			Random random = new Random();
			int courseCounter = 0;
			while(day.getSessions().size() < numSessions) {
				TimeTableSession session = new TimeTableSession(numRooms);
				List<TimeTableCourse> coursesToSchedule = new ArrayList<TimeTableCourse>();
				coursesToSchedule.addAll(candidateCourses);
				
				int room = 0;
				while(room < numRooms) {
					try {
						int courseIdToSchedule = 0;
						if(coursesToSchedule.size() > 0) courseIdToSchedule = 0;
						else break;
//						int courseIdToSchedule = random.nextInt(coursesToSchedule.size());
						TimeTableCourse course = coursesToSchedule.get(courseIdToSchedule);
						session.addCourse(course, room);
						course.setSessionsRequired(course.getSessionsRequired()-1);
						
						coursesToSchedule.remove(courseIdToSchedule);
						if(coursesToSchedule.isEmpty()) break;
					} catch (TimeTableSessionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					room++;
				}
				
				day.addSession(session);
			}
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
			
			return day;
		}
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	

	
}