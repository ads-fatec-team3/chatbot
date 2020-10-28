package br.gov.sp.fatec.backend.websocket;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebSocketRestController {
  @Autowired
  private ActiveUserManager activeUserManager;

  @PostMapping("/rest/user-connect")
  public String userConnect(HttpServletRequest request, @ModelAttribute("username") String username) {

    String remoteAddr = "";

    if (request != null) {
      remoteAddr = request.getHeader("Remote_Addr");
      if (StringUtils.isEmpty(remoteAddr)) {
        remoteAddr = request.getHeader("X-FORWARDED-FOR");

        if (remoteAddr == null || "".equals(remoteAddr)) {
          remoteAddr = request.getRemoteAddr();
        }
      }
    }

    activeUserManager.addUser(username, remoteAddr);

    return remoteAddr;
  }

  @PostMapping("/rest/user-disconnect")
  public String userDisconnect(@ModelAttribute("username") String username) {
    activeUserManager.removeUser(username);
    return "disconnected";
  }

  @GetMapping("/rest/active-users")
  public Set<String> getActiveUsers() {
    return activeUserManager.getActiveUsers();
  }
}