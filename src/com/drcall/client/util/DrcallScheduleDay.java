package com.drcall.client.util;

import java.util.ArrayList;

import com.drcall.db.dao.Schedule;

public class DrcallScheduleDay {
	
	private static final int MORNING_SHIFT = 0;
	private static final int NOON_SHIFT = 1;
	private static final int NIGHT_SHIFT = 2;
	
	private class ScheduleShift{
		private long scheduleId;
		private String doctor_name;
		private int callingNo;
		private int waitingNum;
		private int room;
		
		//private int current_number;
		
		//private int max_number;
		private boolean status; //0: 沒排班       1: 有排班
		
		public void setStatus(boolean status) {
			this.status = status;
		}
		public void setScheduleId(long scheduleId) {
			this.scheduleId = scheduleId;
		}
		public void setDoctor_name(String doctor_name) {
			this.doctor_name = doctor_name;
		}
		public void setCallingNo(int callingNo) {
			this.callingNo = callingNo;
		}
		public void setWaitingNum(int waitingNum) {
			this.waitingNum = waitingNum;
		}
		public void setRoom(int room) {
			this.room = room;
		}
		
		
		
	}
	
	private String date;
	private ArrayList<ScheduleShift> morningShift = new ArrayList<ScheduleShift>();
	private ArrayList<ScheduleShift> noonShift = new ArrayList<ScheduleShift>();
	private ArrayList<ScheduleShift> nightShift = new ArrayList<ScheduleShift>();
	
	public DrcallScheduleDay(String date) {
		this.date = date;
	}
	
	public ArrayList<ScheduleShift> getScheduleByShift(int shift){
		
		if(shift == MORNING_SHIFT){
			return morningShift;
		} else if(shift == NOON_SHIFT){
			return noonShift;
		} else if(shift == NIGHT_SHIFT){
			return nightShift;
		}
		
		return null;
	}

	public void addSchedule(Schedule schedule) {
		
		
			ScheduleShift ms = new ScheduleShift();		
			ms.setScheduleId(schedule.getScheduleId());
			ms.setDoctor_name(schedule.getDoctor().getName());
			ms.setRoom(schedule.getMorningShiftRoom());
			ms.setCallingNo(schedule.getMorningShiftCallingno());
			ms.setWaitingNum(schedule.getMorningShiftWaitingnum());
			ms.setStatus(schedule.getMorningShift());		
			morningShift.add(ms);
		

			ScheduleShift ans = new ScheduleShift();	
			ans.setScheduleId(schedule.getScheduleId());
			ans.setDoctor_name(schedule.getDoctor().getName());
			ans.setRoom(schedule.getAfternoonShiftRoom());
			ans.setCallingNo(schedule.getAfternoonShiftCallingno());
			ans.setWaitingNum(schedule.getAfternoonShiftWaitingnum());
			ans.setStatus(schedule.getAfternoonShift());	
			noonShift.add(ans);
		
			
			ScheduleShift ns = new ScheduleShift();
			ns.setScheduleId(schedule.getScheduleId());
			ns.setDoctor_name(schedule.getDoctor().getName());
			ns.setRoom(schedule.getNightShiftRoom());
			ns.setCallingNo(schedule.getNightShiftCallingno());
			ns.setWaitingNum(schedule.getNightShiftWaitingnum());
			ns.setStatus(schedule.getNightShift());	
			
			
			nightShift.add(ns);
		
	}
}
