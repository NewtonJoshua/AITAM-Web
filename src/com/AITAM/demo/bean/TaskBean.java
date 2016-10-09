package com.AITAM.demo.bean;

import java.util.List;

public class TaskBean implements java.io.Serializable {

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
	List<EmpBean> EmpList;
	int Creator;

	public String getAppealedDate() {
		return AppealedDate;
	}

	public int getAssigned() {
		return Assigned;
	}

	public String getAssigneeName() {
		return AssigneeName;
	}

	public String getCommnets() {
		return Commnets;
	}

	public String getCompletedDate() {
		return CompletedDate;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public int getCreator() {
		return Creator;
	}

	public long getDays() {
		return Days;
	}

	public String getDesc() {
		return Desc;
	}

	public String getDueDate() {
		return DueDate;
	}

	public List<EmpBean> getEmpList() {
		return EmpList;
	}

	public String getMainCompleted() {
		return MainCompleted;
	}

	public String getMainDate() {
		return MainDate;
	}

	public String getMainDesc() {
		return MainDesc;
	}

	public int getMainID() {
		return MainID;
	}

	public String getMainTitle() {
		return MainTitle;
	}

	public int getPriorInt() {
		return PriorInt;
	}

	public String getPriority() {
		return Priority;
	}

	public int getRating() {
		return Rating;
	}

	public int getReview() {
		return Review;
	}

	public int getReviewer() {
		return Reviewer;
	}

	public String getReviewerName() {
		return ReviewerName;
	}

	public String getStatus() {
		return Status;
	}

	public int getTaskId() {
		return TaskId;
	}

	public int getTimeComp() {
		return TimeComp;
	}

	public String getTitle() {
		return Title;
	}

	public void setAppealedDate(String appealedDate) {
		AppealedDate = appealedDate;
	}

	public void setAssigned(int assigned) {
		Assigned = assigned;
	}

	public void setAssigneeName(String assignerName) {
		AssigneeName = assignerName;
	}

	public void setCommnets(String commnets) {
		Commnets = commnets;
	}

	public void setCompletedDate(String completedDate) {
		CompletedDate = completedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public void setCreator(int creator) {
		Creator = creator;
	}

	public void setDays(long days) {
		Days = days;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}

	public void setEmpList(List<EmpBean> empList) {
		EmpList = empList;
	}

	public void setMainCompleted(String mainCompleted) {
		MainCompleted = mainCompleted;
	}

	public void setMainDate(String mainDate) {
		MainDate = mainDate;
	}

	public void setMainDesc(String mainDesc) {
		MainDesc = mainDesc;
	}

	public void setMainID(int mainID) {
		MainID = mainID;
	}

	public void setMainTitle(String mainTitle) {
		MainTitle = mainTitle;
	}

	public void setPriorInt(int priorInt) {
		PriorInt = priorInt;
	}

	public void setPriority(String priority) {
		Priority = priority;
	}

	public void setRating(int rating) {
		Rating = rating;
	}

	public void setReview(int review) {
		Review = review;
	}

	public void setReviewer(int reviewer) {
		Reviewer = reviewer;
	}

	public void setReviewerName(String reviewerName) {
		ReviewerName = reviewerName;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public void setTaskId(int taskId) {
		TaskId = taskId;
	}

	public void setTimeComp(int timeComp) {
		TimeComp = timeComp;
	}

	public void setTitle(String title) {
		Title = title;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
