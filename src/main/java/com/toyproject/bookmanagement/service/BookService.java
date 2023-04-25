package com.toyproject.bookmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.book.CategoryRespDto;
import com.toyproject.bookmanagement.dto.book.SearchBookReqDto;
import com.toyproject.bookmanagement.dto.book.SearchBookRespDto;
import com.toyproject.bookmanagement.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public Map<String, Object> searchBooks(SearchBookReqDto searchBookReqDto){
		List<SearchBookRespDto> list = new ArrayList<>();
		Map<String, Object> Map = new HashMap<>();
		
		int index = (searchBookReqDto.getPage() - 1) * 20;
		
		Map.put("index", index);
		Map.put("categoryIds", searchBookReqDto.getCategoryIds());
		
		bookRepository.searchBooks(Map).forEach(book -> {
			list.add(book.toDto());
		});;
		
		int totalCount = bookRepository.getTotalCount(Map);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("totalCount", totalCount);
		responseMap.put("bookList", list);
		
		return responseMap;
	}
	
	public List<CategoryRespDto> getCategories(){
		List<CategoryRespDto> list = new ArrayList<>();
		
		bookRepository.getCategories().forEach(category -> {
			list.add(category.toDto());
		});;
		
		return list;
	}
	
}
