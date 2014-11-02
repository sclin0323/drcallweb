package com.drcall.client.util;

import java.util.ArrayList;

import com.drcall.db.dao.Schedule;

public class DrcallScheduleDay {
	
	private static final int MORNING_SHIFT = 1;
	private static final int NOON_SHIFT = 2;
	private static final int NIGHT_SHIFT = 3;
	
	private class ScheduleShift{
		private long scheduleId;
		private String doctor_name;
		private int max_number;
		private int current_number;
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
		public void setMax_number(int max_number) {
			this.max_number = max_number;
		}
		public void setCurrent_number(int current_number) {
			this.current_number = current_number;
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
			ms.setMax_number(schedule.getAfternoonShiftRoom());
			ms.setCurrent_number(schedule.getAfternoonShiftCallingno());
			ms.setStatus(schedule.getMorningShift());		
			morningShift.add(ms);
		

			ScheduleShift ans = new ScheduleShift();	
			ans.setScheduleId(schedule.getScheduleId());
			ans.setDoctor_name(schedule.getDoctor().getName());
			ans.setMax_number(schedule.getAfternoonShiftRoom());
			ans.setCurrent_number(schedule.getAfternoonShiftCallingno());
			ans.setStatus(schedule.getAfternoonShift());	
			noonShift.add(ans);
		
			
			ScheduleShift ns = new ScheduleShift();
			ns.setScheduleId(schedule.getScheduleId());
			ns.setDoctor_name(schedule.getDoctor().getName());
			ns.setMax_number(schedule.getAfternoonShiftRoom());
			ns.setCurrent_number(schedule.getAfternoonShiftCallingno());
			ns.setStatus(schedule.getNightShift());	
			nightShift.add(ns);
		
	}
}
