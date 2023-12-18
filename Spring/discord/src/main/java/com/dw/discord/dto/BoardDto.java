package com.dw.discord.dto;

import jakarta.validation.constraints.NotBlank;

public class BoardDto {
	
	private long id;
	@NotBlank
	private String author;
	@NotBlank
	private String title;
	@NotBlank
	private String text;
	@NotBlank
	private String category;
	
	public BoardDto() {
		super();
	}

	public BoardDto(long id, @NotBlank String author, @NotBlank String title, @NotBlank String text,
			@NotBlank String category) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.text = text;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
