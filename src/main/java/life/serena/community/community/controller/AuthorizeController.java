package life.serena.community.community.controller;

import life.serena.community.community.dto.AccessTokeDTO;
import life.serena.community.community.dto.GithubUser;
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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokeDTO accessTokeDTO = new AccessTokeDTO();
        accessTokeDTO.setCilent_id(clientId);
        accessTokeDTO.setCilent_secret(cilentSecret);
        accessTokeDTO.setCode(code);
        accessTokeDTO.setRedirect_uri(redirectUri);
        accessTokeDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokeDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());

        return "index";


    }
}
