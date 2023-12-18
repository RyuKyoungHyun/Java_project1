package com.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.domain.PetVO;
import com.shop.domain.UsersVO;
import com.shop.service.JoinService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value="/shop/*")
@AllArgsConstructor
public class JoinController {
	
	@Autowired
	private JoinService joinservice;
	
	//회원가입 페이지 이동
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public void joinGET() {
		System.out.println("회원가입 페이지 진입");
	}
	
	//회원가입
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinPOST(UsersVO user, RedirectAttributes rttr) throws Exception{
		joinservice.insertUser(user);
		System.out.println("join Service 성공");
		int result = 1;
		rttr.addFlashAttribute("result", result);
		return "redirect:/";
		
	}
	
	// 로그인 페이지 이동
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public void loginGET() {
		System.out.println("로그인 페이지 진입");
	}
	
	//로그인 
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPOST(HttpServletRequest request, UsersVO user, RedirectAttributes rttr) throws Exception {
		HttpSession session = request.getSession();
		UsersVO uVo = joinservice.loginUser(user);
		//아이디나 비밀번호가 일치하지 않을 때
		if(uVo == null) {
			int result = 0;
			rttr.addFlashAttribute("result", result);
			System.out.println("로그인 실패");
			return "redirect:/shop/login";
		}
		//아이디와 비밀번호가 일치할 때
		session.setAttribute("user", uVo);
		System.out.println("login Service 성공");
		return "redirect:/";
	}
	
	//아이디 중복 검사 
	@RequestMapping(value = "/userIdCk", method=RequestMethod.POST)
	@ResponseBody
	public String userIdCkPost(String userId) throws Exception {
		System.out.println("userIdCk() 진입");
		int result = joinservice.idCheck(userId);
		System.out.println("결과값 = "+result);
		if(result != 0) {
			return "fail"; //중복 아이디O
		} else {
			return "success"; //중복 아이디X
		}
	}
	
	//로그아웃
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logoutGET(HttpServletRequest request) throws Exception {
		System.out.println("logoutGET 메서드 진입");
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/";
	}

	//반려견 정보 입력 페이지 이동
	@RequestMapping(value="/mypage-pet", method = RequestMethod.GET)
	public void petGET() {
		System.out.println("반려견 정보입력 페이지 진입");
	}
	
	//반려견 정보 입력
	@RequestMapping(value="/mypage-pet", method = RequestMethod.POST)
	public String petPOST(PetVO pet) throws Exception {
		joinservice.insertPet(pet);
		System.out.println("반려견 정보입력 성공");
		return "redirect:/shop/mypage";
	}

	//마이페이지 - 회원정보 수정 페이지 이동
	@RequestMapping(value="/mypage-modify", method = RequestMethod.GET)
	public void modifyGET() {
		System.out.println("회원정보 수정 페이지 진입");
	}
	
	//회원정보 수정 
	@RequestMapping(value="/mypage-modify", method = RequestMethod.POST)
	public String modifyPOST(UsersVO user) throws Exception {
		joinservice.updateUser(user);
		System.out.println("회원정보 수정 성공");
		return "redirect:/";
	}
	
	//회원 탈퇴
	@RequestMapping(value="/mypage-delete", method = RequestMethod.POST)
	public String deletePOST(HttpServletRequest request, HttpSession session) {
		UsersVO loginUser = (UsersVO) session.getAttribute("user");
		if(loginUser != null) {
			try {
				joinservice.deleteUser(loginUser.getUserId());
				session.invalidate();
			} catch	(Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/";
	}
	
	//회원 아이디 찾기 페이지 이동
	@RequestMapping(value="searchId", method=RequestMethod.GET)
	public void searchIdGET() {
		System.out.println("아이디 찾기 페이지 진입");
	}
	
	// 아이디 찾기
	@RequestMapping(value="searchId", method=RequestMethod.POST) 
	public String searchIdPOST(Model model, @RequestParam String userName, @RequestParam String email, @RequestParam String phone, @RequestParam String searchType, RedirectAttributes rttr) {
		 try {
		        UsersVO searchUser = null;

		        // 라디오 버튼에 따라 검색 조건 다르게 설정
		        if ("email".equals(searchType)) {
		            searchUser = joinservice.searchIdByEmail(userName, email);
		            System.out.println(searchUser);
		        } else if ("phone".equals(searchType)) {
		            searchUser = joinservice.searchIdByPhone(userName, phone);
		        }

		        if (searchUser != null) {
		            // 일치하는 회원이 있다면 해당 회원의 아이디를 FlashAttribute에 추가하여 전달
		            rttr.addFlashAttribute("userId", searchUser.getUserId());
		        } else {
		            // 일치하는 회원이 없으면 메시지 전달
		            rttr.addFlashAttribute("notFound", true);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

	    return "redirect:/shop/showId";
	}
	
	//회원 아이디 찾기 결과 페이지 이동
	@RequestMapping(value="showId", method=RequestMethod.GET)
	public void showIdGET() {
		System.out.println("아이디 찾기 결과 페이지 진입");
	}
	
	@RequestMapping(value="searchPw", method=RequestMethod.GET)
	public void searchPwGET() {
		System.out.println("비밀번호 찾기 페이지 진입");
	}
}

