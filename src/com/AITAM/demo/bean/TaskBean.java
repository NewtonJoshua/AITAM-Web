package com.AITAM.demo.bean;

import java.util.List;

public class TaskBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5534597566442331026L;
	int TaskId;
	String MainTitle;
	String MainDesc;
	String MainDate;
	String MainCompleted;
	int Review;
	int TimeComp;
	int Rating;
	int PriorInt;
	String Title;
	String Desc;
	String DueDate;
	String CreatedDate;
	String CompletedDate;
	String AppealedDate;
	String Priority;
	long Days;
	int MainID;
	int Reviewer;
	String AssigneeName;
	String Status;
	String Commnets;
	int Assigned;
	String ReviewerName;
	List <EmpBean> EmpList;
	
	public String getMainCompleted() {
		return MainCompleted;
	}
	public void setMainCompleted(String mainCompleted) {
		MainCompleted = mainCompleted;
	}
	public int getReview() {
		return Review;
	}
	public void setReview(int review) {
		Review = review;
	}
	public int getTimeComp() {
		return TimeComp;
	}
	public void setTimeComp(int timeComp) {
		TimeComp = timeComp;
	}
	public int getRating() {
		return Rating;
	}
	public void setRating(int rating) {
		Rating = rating;
	}
	public int getPriorInt() {
		return PriorInt;
	}
	public void setPriorInt(int priorInt) {
		PriorInt = priorInt;
	}
	public int getMainID() {
		return MainID;
	}
	public void setMainID(int mainID) {
		MainID = mainID;
	}
	public String getMainTitle() {
		return MainTitle;
	}
	public void setMainTitle(String mainTitle) {
		MainTitle = mainTitle;
	}
	public String getMainDesc() {
		return MainDesc;
	}
	public void setMainDesc(String mainDesc) {
		MainDesc = mainDesc;
	}
	public String getMainDate() {
		return MainDate;
	}
	public void setMainDate(String mainDate) {
		MainDate = mainDate;
	}
	public String getAppealedDate() {
		return AppealedDate;
	}
	public void setAppealedDate(String appealedDate) {
		AppealedDate = appealedDate;
	}
	public long getDays() {
		return Days;
	}
	public void setDays(long days) {
		Days = days;
	}
	public List<EmpBean> getEmpList() {
		return EmpList;
	}
	public void setEmpList(List<EmpBean> empList) {
		EmpList = empList;
	}
	public String getReviewerName() {
		return ReviewerName;
	}
	public void setReviewerName(String reviewerName) {
		ReviewerName = reviewerName;
	}
	public int getAssigned() {
		return Assigned;
	}
	public void setAssigned(int assigned) {
		Assigned = assigned;
	}
	int Creator;
	public int getCreator() {
		return Creator;
	}
	public void setCreator(int creator) {
		Creator = creator;
	}
	public int getTaskId() {
		return TaskId;
	}
	public void setTaskId(int taskId) {
		TaskId = taskId;
	}
	public String getAssigneeName() {
		return AssigneeName;
	}
	public void setAssigneeName(String assignerName) {
		AssigneeName = assignerName;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public String getDueDate() {
		return DueDate;
	}
	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}
	public String getPriority() {
		return Priority;
	}
	public void setPriority(String priority) {
		Priority = priority;
	}
	public int getReviewer() {
		return Reviewer;
	}
	public void setReviewer(int reviewer) {
		Reviewer = reviewer;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCommnets() {
		return Commnets;
	}
	public void setCommnets(String commnets) {
		Commnets = commnets;
	}
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	public String getCompletedDate() {
		return CompletedDate;
	}
	public void setCompletedDate(String completedDate) {
		CompletedDate = completedDate;
	}
	
	
}
