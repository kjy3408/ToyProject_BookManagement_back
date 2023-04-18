package com.toyproject.bookmanagement.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.toyproject.bookmanagement.exception.CustomException;

@Component
@Aspect
public class ValidationAop {

	@Pointcut("@annotation(com.toyproject.bookmanagement.aop.annotation.ValidAspect)")
	private void pointCut() {}
	
	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object[] args = joinPoint.getArgs(); //::Controller - bindingResult //bindingResult에서 값을 꺼냄.
		BindingResult bindingResult = null; //bindingResult로 downCasting하기위함
		
		for(Object arg : args) {
			if(arg.getClass() == BeanPropertyBindingResult.class) {
				bindingResult = (BindingResult) arg; //반복돌려 downCasting해줌
			}
		}
		
		if(bindingResult.hasErrors()) { //bindingResult에 넣은 arg들 중 error를 가지고 있다면!
			Map<String, String> errorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(error -> {
				errorMap.put(error.getField(), error.getDefaultMessage());
			});
			
			throw new CustomException("Validation Failed", errorMap);
		}
		
		
		
		return joinPoint.proceed();
	}
	
}
