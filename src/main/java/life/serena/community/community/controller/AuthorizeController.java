package life.serena.community.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import life.serena.community.community.dto.AccessTokeDTO;
import life.serena.community.community.dto.GithubUser;
import life.serena.community.community.mapper.UserMapper;
import life.serena.community.community.model.User;
import life.serena.community.community.provider.GithubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.cilent.id}")
    private String clientId;

    @Value("${github.cilent.secret}")
    private String cilentSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokeDTO accessTokeDTO = new AccessTokeDTO();
        accessTokeDTO.setCilent_id(clientId);
        accessTokeDTO.setCilent_secret(cilentSecret);
        accessTokeDTO.setCode(code);
        accessTokeDTO.setRedirect_uri(redirectUri);
        accessTokeDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokeDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            return "redirect:/";
        }



    }
}
