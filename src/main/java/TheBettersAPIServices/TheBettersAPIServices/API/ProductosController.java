package TheBettersAPIServices.TheBettersAPIServices.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TheBettersAPIServices.TheBettersAPIServices.Services.Productos.Productos;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "API/Productos")
public class ProductosController {
  @Autowired
  private Productos productos;

  @GetMapping(value = "/getproductoscat")
  public ResponseEntity<Object> CreateUserAuthetication(@RequestParam MultiValueMap<String, String> paramMap) {
    return ResponseEntity.ok(productos.getcatalogoCategorias(paramMap));
  }
}
