package com.spring.mvc2.data_transfer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc2.data_transfer.dao.MemberDAO;

@Controller
@RequestMapping("cTOv")
public class ControllerToView {
	@Autowired
	private MemberDAO memberDAO;
	   /*
	    * 
	    *  예시 1) Model
	    *  
	    *  메서드의 파라미터로 Model을 추가하여 addAddtribute() 메서드로 뷰에서 사용할 데이터를 전달 한다.
	    * 
	    * */
	   @RequestMapping(value="/list1") 
	   public String list1(Model model) {
	      
	      model.addAttribute("from", "list1");
	      model.addAttribute("memberList" , memberDAO.selectAllMember());
	      
	      return "member/memberList";
	   }
	   
	   /*
	    * 예시 2) ModelAndView 클래스 이용
	    * 
	    * ModelAndView객체를 생성하여  addObject() 메서드로 뷰에서 사용할 데이터를 전달 한다.
	    * 관용적으로 객체명으로 mv 혹은 mav로 작성한다.
	    * 
	    * */
	   @RequestMapping(value="list2")
	   public ModelAndView list2()	{				//return 타입은 String이 아니라 ModelAndView 클래스
		   //ModelAndView mv = new ModelAndView();
		   //mv.setViewName("member/memberList");     				//view -> 포워딩할 jsp파일명 작성
		   ModelAndView mv = new ModelAndView("member/memberList");
		   mv.addObject("from", "list2");
		   mv.addObject("memberList", memberDAO.selectAllMember()); //model-> addObject 메서드를 사용하여 데이터를 전송
		   return mv;								//ModelAndView 객체를 반환한다.
	   }
	   
	   /*
	    * 예시3) HttpServletRequest 이용 
	    * */
	   
	   @RequestMapping(value="/list3")
	   public String list3(HttpServletRequest request) {
		   request.setAttribute("from", "list3");
		   request.setAttribute("memberList",  memberDAO.selectAllMember());
		   return "member/memberList";
	   }
	   /*
	     
       # 예시 4) ResponseEntity 이용 (응답객체)
    
       - HTTP에서 Request와 Response 동작시 전송되는 정보는 크게 body(몸체) , headers(헤더), status code(상태 코드)가 있다.
       - @ResponseBody 에는 없는 헤더와 상태코드가 함께 들어간다.
      
      1. body 			: 선택
      - HTTP의 request 또는 response가 전송하는 데이터(본문)
      
      2. headers 		: 선택
      - HTTP의 request 또는 response에 대한 부가적인 정보
      
      3. status code 	: 필수
      - 클라이언트의 요청이 성공적으로 처리되었는지 상태를 알려주는 것
      - 상태 코드는 필수적으로 리턴해주어야 한다.

    */
	   @RequestMapping(value="/test1")
	   public ResponseEntity<Object> test3(){
		   //1) 
		   //return new ResponseEntity<Object>(HttpStatus.OK);
		   
		   //2)
		   //return new ResponseEntity<Object>("<h1>abcdefg</h1>",HttpStatus.OK);
		   
		   //3)
		   String data = "<script>";
		   		  data += "alert('페이지이동');";
		   		  data += "</script>";
		   		  data += "<h1>html페이지를 반환합니다.</h1>";
		   HttpHeaders header = new HttpHeaders();
		   header.add("Content-Type", "text/html; charset=utf-8");
		   		  
		   return new ResponseEntity<Object>(data, header, HttpStatus.OK);
	   }
	   
	 /*
	     
       # 예시 5) ResponseBody이용  
       
       - ResponseEntity와 기능이 같으며 html 본문만 전송한다.
       - 한글 데이터가 전송이 되지 않으며 한글 전송시 ResponseEntity의 객체의 
          객체명.add("Content-Type", "text/html; charset=utf-8") 메서드를 이용하여
         헤더 정보를 추가하여 사용한다.
 
    */
	  @RequestMapping(value="/test2")
	  public @ResponseBody String test2() {
		  String message = "<script>";
		  		 message += "alert('send message');";
		  		 message += "</script>";
		  
		  return message;
	  }
}

/*
포트폴리오에서는 사용 안함 ,  실무에서도 거의 쓰지 않음 
# 예시 6) RestController 이용 

- ResponseBody와 기능이 같으며 헤더와 상태 코드를 제외한 html 본문만 전송한다.
- 클래스에 @RestController어노테이션을 작성하여 구현한다. 
- 그냥 Resposebody를 안붙이면 된다

*/

@RestController
class RestControllerEx {
	   
	   @RequestMapping(value="/test3")
	   public String test3() {
	      
	      String message = "<script>";
	             message += "alert('send message');";
	             message += "</script>";
	      
	      return message;
	  }
}




























