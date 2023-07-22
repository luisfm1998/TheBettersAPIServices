package TheBettersAPIServices.TheBettersAPIServices.Services.Productos;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import TheBettersAPIServices.TheBettersAPIServices.Utilities.Validations;
import TheBettersAPIServices.TheBettersAPIServices.dto.Response;

@Service
public class Productos {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Response getcatalogoCategorias(MultiValueMap<String, String> paramMap) {
    Response _respuesta = new Response();
    Validations _val = new Validations();
    try {
      SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
      .withProcedureName("SP_Getcategoriasproductos");
      Map<String, Object> inParams = new HashMap<>();
      inParams.put("Busqueda", paramMap.getFirst(""));
      Map<String, Object> result = jdbcCall.execute(inParams);
      _respuesta.setData(_val.Sql(result));
      _respuesta.setIsOK(true);
      _respuesta.setMessage("");
      _respuesta.setAuthorization("Authorized");
    } catch (Exception e) {
      _respuesta.setAuthorization(e.getMessage());
    }
    return _respuesta;
  } 
    
}
