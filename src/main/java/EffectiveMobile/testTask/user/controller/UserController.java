package EffectiveMobile.testTask.user.controller;

import EffectiveMobile.testTask.auth.AuthenticationRequest;
import EffectiveMobile.testTask.auth.AuthenticationResponse;
import EffectiveMobile.testTask.auth.AuthenticationService;
import EffectiveMobile.testTask.auth.RegisterRequest;
import EffectiveMobile.testTask.user.model.User;
import EffectiveMobile.testTask.user.request.EditRequest;
import EffectiveMobile.testTask.user.request.EmailDeleteRequest;
import EffectiveMobile.testTask.user.request.PhoneDeleteRequest;
import EffectiveMobile.testTask.user.request.TransferRequest;
import EffectiveMobile.testTask.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final AuthenticationService authService;
  private final UserServiceImpl userService;

  @PostMapping("/user/create")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/user/edit")
  public ResponseEntity<EditRequest> editUser(@RequestBody EditRequest request) {
    userService.editUserData(request);
    return ResponseEntity.ok(request);
  }

  @PostMapping("/user/add-info")
  public ResponseEntity<EditRequest> addUserInfo(@RequestBody EditRequest request) {
    userService.addUserContacts(request);
    return ResponseEntity.ok(request);
  }

  @PostMapping("/user/delete-phone")
  public ResponseEntity<PhoneDeleteRequest> deleteUserPhone(
      @RequestBody PhoneDeleteRequest request) {
    userService.deleteUserPhone(request);
    return ResponseEntity.ok(request);
  }

  @PostMapping("/user/delete-email")
  public ResponseEntity<EmailDeleteRequest> deleteUserPhone(
      @RequestBody EmailDeleteRequest request) {
    userService.deleteUserEmail(request);
    return ResponseEntity.ok(request);
  }

  @PostMapping("/auth/authenticate")
  public ResponseEntity<AuthenticationResponse> authentication(
      @RequestBody AuthenticationRequest request) {
    log.info("Вызов аутентификации");
    return ResponseEntity.ok(authService.authenticate(request));
  }

  @PostMapping("/user/transfer")
  public ResponseEntity<TransferRequest> transfer(@RequestBody TransferRequest request) {
    log.info("Вызов функции перевода денег");
    userService.transferMoney(request);
    return ResponseEntity.ok(request);
  }

  @GetMapping("/user/find")
  public ResponseEntity<List<User>> findUsersByFilter(
      @RequestParam(required = false) LocalDate date,
      @RequestParam(required = false) String phone,
      @RequestParam(required = false) String fullName,
      @RequestParam(required = false) String email) {
    List<User> users = userService.findUsersByFilter(date, phone, fullName, email);
    log.info("Find {} users", users.size());
    log.info("Users found: {}", users);
    return ResponseEntity.ok(users);
  }

}
