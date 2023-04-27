package com.toyproject.bookmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.toyproject.bookmanagement.dto.book.CategoryRespDto;
import com.toyproject.bookmanagement.dto.book.GetBookRespDto;
import com.toyproject.bookmanagement.dto.book.RentalListRespDto;
import com.toyproject.bookmanagement.dto.book.SearchBookReqDto;
import com.toyproject.bookmanagement.dto.book.SearchBookRespDto;
import com.toyproject.bookmanagement.entity.User;
import com.toyproject.bookmanagement.repository.BookRepository;
import com.toyproject.bookmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	private final UserRepository userRepository;
	
	public GetBookRespDto getbook (int bookId) {
	
		
		return bookRepository.getBook(bookId).toGetBookDto();
	}
	
	
	public Map<String, Object> searchBooks(SearchBookReqDto searchBookReqDto){
		List<SearchBookRespDto> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		int index = (searchBookReqDto.getPage() - 1) * 20;
		
		map.put("index", index);
		map.put("categoryIds", searchBookReqDto.getCategoryIds());
		map.put("searchValue", searchBookReqDto.getSearchValue());
		
		
		bookRepository.searchBooks(map).forEach(book -> {
			list.add(book.toDto());
		});;
		
		int totalCount = bookRepository.getTotalCount(map);
		
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
	
	public int getLikeCount(int bookId) {
		
		return bookRepository.getLikeCount(bookId);
	}
	
	public int getLikeStatus(int bookId, int userId) {
//매개변수가 두개일때 map으로 묶어서 repository로 보냄.
		Map<String, Object> map = new HashMap<>();
		map.put("bookId", bookId);
		map.put("userId", userId);
		
		return bookRepository.getLikeStatus(map);
	}
	
	public int setLike(int bookId, int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("bookId", bookId);
		map.put("userId", userId);
		
		return bookRepository.setLike(map);
	}
	
	public int disLike(int bookId, int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("bookId", bookId);
		map.put("userId", userId);
		
		return bookRepository.disLike(map);
	}
	
	public List<RentalListRespDto> getRentalListByBookId(int bookId){
		List<RentalListRespDto> list = new ArrayList<>();
		
		bookRepository.getRentalListByBookId(bookId).forEach(rentalData -> {
			list.add(rentalData.toDto());
		});
		
		return list;
	}
	
	public int rentalBook(int bookListId, int userid) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("bookListId", bookListId);
		map.put("userId", userid);
		
		
		return bookRepository.rentalBook(map);
	}
	
	public int returnBook(int bookListId, int userid) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("bookListId", bookListId);
		map.put("userId", userid);
		
		
		return bookRepository.returnBook(map);
	}
	
	
}
