package com.aloha.matmap.controller.users;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aloha.matmap.domain.users.CustomUser;
import com.aloha.matmap.domain.users.Users;
import com.aloha.matmap.dto.PasswordChangeDto;
import com.aloha.matmap.dto.PasswordCheckDto;
import com.aloha.matmap.dto.UserUpdateDto;
import com.aloha.matmap.service.inter.users.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Controller 
@RequestMapping("/users")
@RequiredArgsConstructor 
public class MyPageController {

    private final UserService userService;

    // 비밀번호 확인 완료 세션 키
    private static final String PASSWORD_VERIFIED = "PASSWORD_VERIFIED";

    // 마이페이지
    @GetMapping("/mypage")
    public String mypage(
        @AuthenticationPrincipal CustomUser loginUser,
        Model model
    ){
        // // 로그인 정보가 없는 경우
        // if (loginUser == null) {
        //     return "redirect:/users/login";
        // }

        // Users user = loginUser.getUser();
        // model.addAttribute("user", user);

        return "page/mypage/index";
    }

    // 회원 정보 수정 화면
    @GetMapping("/mypage/info")
    public String info(
        @AuthenticationPrincipal CustomUser loginUser,
        Model model
    ) {
    // 로그인 기능 완성 전 테스트용 아이디
    String username;
        if (loginUser == null) {
            username = "user"; // 실제 DB에 존재하는 아이디로 변경
        } else {
            username = loginUser.getUsername();
        }
        // 회원 정보 조회
        Users user = userService.selectByUsername(username);
        if (user == null) {
            return "redirect:/users/mypage";
        }
        UserUpdateDto dto = new UserUpdateDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getTel());
        model.addAttribute("updateDto", dto);
        model.addAttribute("user", user);
        return "page/mypage/info";
        }
    // @GetMapping("/mypage/info")
    // public String info(
    //     @AuthenticationPrincipal CustomUser loginUser, Model model
    // ) {
    //     // 로그인된 회원 아이디로, 실시간으로 회원 정보 조회
    //     String username = loginUser.getUsername();
    //     Users user = userService.selectByUsername(username);
    //     UserUpdateDto dto = new UserUpdateDto();
    //     dto.setName(user.getName());
    //     dto.setEmail(user.getEmail());
    //     model.addAttribute("updateDto", dto);
    //     model.addAttribute("user", user);
    //     return "page/mypage/info";
    // }

    @PostMapping("/mypage/info")
    public String updateInfo(
        @AuthenticationPrincipal CustomUser loginUser,          // 로그인된 사용자 정보
        @Valid                                                  // 유효성 검사
        @ModelAttribute ("updateDto") UserUpdateDto updateDto,  // "updateDto" 로 모델 등록
        BindingResult bindingResult,                            // 유효성 검사 오류 결과
        Model model,                                            // View 에 데이터 전달하는 객체
        RedirectAttributes ra                                   // 리다이렉트 시 데이터 전달
    ) {
        String username = loginUser.getUsername();
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            // 에러 발생하면, 다시 회원정보 수정 화면으로
            Users user = userService.selectByUsername(username);
            model.addAttribute("user", user);
            return"page/mypage/info";
        }

        // 회원 정보 수정
        Users updateUser = userService.selectByUsername(username);

        // 수정할 정보 설정
        updateUser.setName(updateDto.getName());
        updateUser.setEmail(updateDto.getEmail());
        updateUser.setTel(updateDto.getPhone());

        // 회원 정보 수정
        boolean result = userService.update(updateUser);

        log.info("회원정보 수정 결과: {}", result);

        // 수정된 사용자 정보
        CustomUser updatedLoginUser = new CustomUser(updateUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                                        updatedLoginUser,
                                                        updatedLoginUser.getPassword(),
                                                        updatedLoginUser.getAuthorities()
                                                    );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ra.addFlashAttribute("message", "회원정보가 수정되었습니다.");
        return "redirect:/users/mypage/info";
    }
    

    // 비밀번호 확인 화면
    @GetMapping("/mypage/password/check")
    public String passwordCheck(
        @ModelAttribute ("checkDto") PasswordCheckDto checkDto,
        HttpSession session
    ) {
        // 확인 화면에 들어오면 비밀번호 확인 완료 정보를 제거
        session.removeAttribute(PASSWORD_VERIFIED);
        return "/page/mypage/password-check";
    }

    // 비밀번호 확인 처리
    @PostMapping("/mypage/password/check")
    public String PasswordCheck(
        @AuthenticationPrincipal CustomUser loginUser,
        @Valid @ModelAttribute ("checkDto") PasswordCheckDto checkDto,
        BindingResult bindingResult,
        HttpSession session
    ) {
        // 비밀번호 미 입력시
        if(bindingResult.hasFieldErrors()) {
            return "page/mypage/password-check";
        }
        // 비밀번호 일치 여부 확인
        // 로그인한 사용자 정보 가져오기
        Users user = loginUser.getUser();

        // 입력한 비밀번호
        String password = checkDto.getPassword();

        // 비밀번호 일치 여부 확인
        boolean result = userService.checkPassword(user, password);

        // 불일치
        if(!result) {
            bindingResult.rejectValue("password", "mismatch", "비밀번호가 일치하지 않습니다.");
            return "page/mypage/password-check";
        }

        // 현재 비밀번호 확인 완료 여부를 세션에 저장
        session.setAttribute(PASSWORD_VERIFIED, true);
        return "redirect:/users/mypage/password";
    }

    // 비밀번호 변경 화면
    @GetMapping("/mypage/password")
    public String password(
        @ModelAttribute ("changeDto") PasswordChangeDto changeDto,
        HttpSession session
    ) {
        // 현재 비밀번호 일치 여부가 없으면
        if(session.getAttribute(PASSWORD_VERIFIED) == null) {
            return "redirect:/users/myage/password/check";
        }
        return "page/mypage/password";
    }

    // 비밀번호 변경 처리
    @PostMapping("/mypage/password")
    public String changePassword(
        @AuthenticationPrincipal CustomUser loginUser,
        @Valid @ModelAttribute("changeDto") PasswordChangeDto changeDto,
        BindingResult bindingResult,
        HttpSession session,
        RedirectAttributes ra
    ) {
        // 유효성 검사
        // - 비밀번호 확인 완료 여부 체크
        if( session.getAttribute(PASSWORD_VERIFIED) == null ) {
            return "redirect:/users/mypage/password/check";
        }
        // 비밀번호 6자리 이상, 확인하고 일치 여부 체크
        if( !bindingResult.hasFieldErrors("newPassword")
            && !bindingResult.hasFieldErrors("newPasswordConfirm")
            && !changeDto.getNewPassword().equals(changeDto.getNewPasswordConfirm()) ) {
                bindingResult.rejectValue("newPasswordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
            }
        if( bindingResult.hasErrors() ) {
            return "page/mypage/password";
        }

        // 비밀번호 변경 요청
        String username = loginUser.getUser().getUsername();
        String newPassword = changeDto.getNewPassword();
        Users updatedUser = userService.changePassword(username,newPassword);
        session.removeAttribute(PASSWORD_VERIFIED);

        // 로그인 인증 정보 갱신
        CustomUser updatedLoginUser = new CustomUser(updatedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                            updatedLoginUser,
                                            updatedLoginUser.getPassword(),
                                            updatedLoginUser.getAuthorities()
                                        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ra.addFlashAttribute("message", "비밀번호가 변경되었습니다.");
        return "redirect:/users/mypage/info";
    }
    
}
