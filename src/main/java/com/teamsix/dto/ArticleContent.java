package com.teamsix.dto;

public class ArticleContent {
	
	private String title;
	private String content;
	
	public ArticleContent(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	public ArticleContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
