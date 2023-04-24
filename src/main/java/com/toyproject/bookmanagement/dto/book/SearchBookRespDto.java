package com.toyproject.bookmanagement.dto.book;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchBookRespDto {

	private int bookId;
	private String bookName;
	private int authorId;
	private String authorName;
	private int publisherId;
	private String publisherNamel;
	private int categoryId;
	private String categoryName;
	private String coverImgUrl;
}
