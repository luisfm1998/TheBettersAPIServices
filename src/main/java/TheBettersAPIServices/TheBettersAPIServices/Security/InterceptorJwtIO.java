package TheBettersAPIServices.TheBettersAPIServices.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.List;

@Component
public class InterceptorJwtIO implements HandlerInterceptor{

    @Value("${jms.jwt.token.auth.path}")
    private String AUTH_PATH;
    @Value("#{'${jms.jwt.excluded.path}'.split(',')}")
    private List<String> UrlExclude; 

    @Autowired
    private JwtIO jwtIO;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
        throws Exception{
        boolean validate = false;
        String uri =  request.getRequestURI();
        if(uri.equals(AUTH_PATH)|| excluded(uri)){
            validate = true;// Si es true ya no se valida la URL
        }
        if(!validate && request.getHeader("Authorization") != null && !request.getHeader("Authorization").isEmpty()){
            String token = request.getHeader("Authorization").replace("Bearer ","");
            validate = !jwtIO.validateToken(token);
        }
        if(!validate){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return validate;
    }


    //Validar las rutas que sean correctas desde el propiertis
    private boolean excluded(String path){
        boolean result = false;
        for(String exc: UrlExclude){
            if(exc.equals(path)){
                result = true;
            }
        }
        return result;
    }
    
}
