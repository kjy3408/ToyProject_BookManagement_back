package com.toyproject.bookmanagement.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBookRespDto {
	
	private int bookId;
	private String bookName;
	private String authorName;
	private String publisherName;
	private String categoryName;
	private String coverImgUrl;
}
