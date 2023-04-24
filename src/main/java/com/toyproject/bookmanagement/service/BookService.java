package com.toyproject.bookmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.book.SearchBookReqDto;
import com.toyproject.bookmanagement.dto.book.SearchBookRespDto;
import com.toyproject.bookmanagement.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public List<SearchBookRespDto> searchBooks(SearchBookReqDto searchBookReqDto){
		List<SearchBookRespDto> list = new ArrayList<>();
		Map<String, Object> indexMap = new HashMap<>();
		
		int index = (searchBookReqDto.getPage() - 1) * 20;
		
		indexMap.put("index", index);
		
		bookRepository.searchBooks(indexMap).forEach(book -> {
			list.add(book.toDto());
		});;
		
		
		return list;
	}
	
}
