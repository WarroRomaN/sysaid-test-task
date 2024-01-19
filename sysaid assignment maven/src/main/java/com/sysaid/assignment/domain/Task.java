package com.sysaid.assignment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Task class represents a task with various attributes such as activity, accessibility, task type, participants,
 * price, link, and key. It also tracks the rank of the task and the users who have completed or added it to their wish list.
 */
@Getter
@Setter
@ToString
public class Task implements Serializable {

	private String activity;
	private float accessibility;
	private TaskType type;
	private int participants;
	private float price;
	private String link;
	private String key;

	private int rank;
	private List<String> completedTaskUsers;
	private List<String> addedToWishListUsers;

	public Task() {
		this.rank = 0;
		this.completedTaskUsers = new ArrayList<>();
		this.addedToWishListUsers = new ArrayList<>();
	}
}

