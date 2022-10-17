package boardProgram;

import java.io.Serializable;

public class Board implements Comparable<Board>, Serializable{
	private static final long serialVersionUID = -1553801742929460024L;

	private int number;
	private String title;
	private String content;
	private String writer;
	private String date;
	private String password;
	
	public Board(int number, String title, String content, String writer, String date) {
		this.number = number;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.date = date;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	
	
	public String getPassword() {
		return password;
	}
	
	public void MakePassword() {
		this.password = this.writer + "1234";
	}

	public void viewDetails() {
		System.out.println(number + "\t" + title + "\t" + writer + "\t" + date);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Board)) return false;
		return this.number == ((Board)obj).number ? true : false;
	}
	
	@Override
	public int hashCode() {
		return this.number;
	}
	

	@Override
	public int compareTo(Board o) {
		return this.getDate().compareTo(o.getDate());
	}
	
	@Override
	public String toString() {
		return "[" + number + "]\n제목 : " + title + "\n내용 : " + content + "\n작성자 : " + writer + "\n작성일 : " + date;
	}
}