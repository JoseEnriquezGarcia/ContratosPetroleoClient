package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.Contrato;
import com.JEnriquez.Crud.ML.Factura;
import com.JEnriquez.Crud.ML.NodoComercialEntrega;
import com.JEnriquez.Crud.ML.NodoComercialRecepcion;
import com.JEnriquez.Crud.ML.Result;
import com.JEnriquez.Crud.ML.Usuario;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/facturas")
public class FacturasController {

    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "http://localhost:8081/";

    @GetMapping
    public String GetAll(@RequestParam(defaultValue = "0") int currentPage, Model model) {
        try {
            Factura facturaBusqueda = new Factura();
            facturaBusqueda.Contrato = new Contrato();
            facturaBusqueda.Contrato.Usuario = new Usuario();
            Result result = new Result();

            int totalPaginas = 67;
            int paginaActual = currentPage;

            int desde = Math.max(0, paginaActual - 1);
            int hasta = Math.min(desde + 4, totalPaginas - 1);
            if (hasta - desde < 4) {
                desde = Math.max(0, hasta - 4);
            }

// Generar lista de páginas visibles
            List<Integer> paginas = IntStream.rangeClosed(desde, hasta).boxed().collect(Collectors.toList());

            ResponseEntity<Result<Factura>> response = restTemplate.exchange(urlBase + "factura?numeroPagina=" + currentPage,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Factura>>() {
            });

            ResponseEntity<Result<Contrato>> responseContrato = restTemplate.exchange(urlBase + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Usuario>> responseUsuarios = restTemplate.exchange(urlBase + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

            ResponseEntity<Result<NodoComercialRecepcion>> responseNodoRecepcion = restTemplate.exchange(urlBase + "nodoRecepcion",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialRecepcion>>() {
            });

            ResponseEntity<Result<NodoComercialEntrega>> responseNodoEntrega = restTemplate.exchange(urlBase + "nodoEntrega",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialEntrega>>() {
            });

            result.objects = response.getBody().objects.stream()
                    .sorted(Comparator.comparing(f -> f.getIdFactura()))
                    .collect(Collectors.toList());
            result.currentPage = response.getBody().currentPage;

            model.addAttribute("facturaBusqueda", facturaBusqueda);
            model.addAttribute("numeroPagina", paginaActual);
            model.addAttribute("totalPaginas", totalPaginas);
            model.addAttribute("paginas", paginas);
            model.addAttribute("mostrarPaginacion", true);
            model.addAttribute("listaFacturas", result.objects);
            model.addAttribute("listaContratos", responseContrato.getBody().objects);
            model.addAttribute("listaUsuarios", responseUsuarios.getBody().objects);
            model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
            model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);

        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "ViewFacturas";
    }

    @PostMapping("/busqueda")
    public String BusquedaDinamica(@ModelAttribute Factura factura, Model model) {
        try {
            int totalPaginas = 67;
            int paginaActual = 0;

            int desde = Math.max(0, paginaActual - 1);
            int hasta = Math.min(desde + 4, totalPaginas - 1);
            if (hasta - desde < 4) {
                desde = Math.max(0, hasta - 4);
            }
            List<Integer> paginas = IntStream.rangeClosed(desde, hasta).boxed().collect(Collectors.toList());
            Factura facturaBusqueda = new Factura();
            facturaBusqueda.Contrato = new Contrato();
            facturaBusqueda.Contrato.Usuario = new Usuario();
            Result result = new Result();

            ResponseEntity<Result<Factura>> response = restTemplate.exchange(urlBase + "factura/busquedaService",
                    HttpMethod.POST,
                    new HttpEntity<>(factura),
                    new ParameterizedTypeReference<Result<Factura>>() {
            });

            ResponseEntity<Result<Contrato>> responseContrato = restTemplate.exchange(urlBase + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Usuario>> responseUsuarios = restTemplate.exchange(urlBase + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

            ResponseEntity<Result<NodoComercialRecepcion>> responseNodoRecepcion = restTemplate.exchange(urlBase + "nodoRecepcion",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialRecepcion>>() {
            });

            ResponseEntity<Result<NodoComercialEntrega>> responseNodoEntrega = restTemplate.exchange(urlBase + "nodoEntrega",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialEntrega>>() {
            });

            

            if (response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204))) {
                model.addAttribute("facturaBusqueda", facturaBusqueda);
                model.addAttribute("numeroPagina", paginaActual);
                model.addAttribute("totalPaginas", totalPaginas);
                model.addAttribute("paginas", paginas);
                model.addAttribute("mostrarMensaje", true);
                model.addAttribute("mostrarPaginacion", false);
                model.addAttribute("listaFacturas", result);
                model.addAttribute("listaContratos", responseContrato.getBody().objects);
                model.addAttribute("listaUsuarios", responseUsuarios.getBody().objects);
                model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
                model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);
            } else if (response.getStatusCode().is2xxSuccessful()) {
                result.objects = response.getBody().objects;
                model.addAttribute("facturaBusqueda", facturaBusqueda);
                model.addAttribute("numeroPagina", paginaActual);
                model.addAttribute("totalPaginas", totalPaginas);
                model.addAttribute("paginas", paginas);
                model.addAttribute("mostrarMensaje", false);
                model.addAttribute("mostrarPaginacion", false);
                model.addAttribute("listaFacturas", result.objects);
                model.addAttribute("listaContratos", responseContrato.getBody().objects);
                model.addAttribute("listaUsuarios", responseUsuarios.getBody().objects);
                model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
                model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);
            }

        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }

        return "ViewFacturas";
    }

    @GetMapping("/byFecha")
    public String BusquedaByFecha(@RequestParam String Desde, @RequestParam String Hasta, Model model) {

        try {
            Factura facturaBusqueda = new Factura();
            facturaBusqueda.Contrato = new Contrato();
            facturaBusqueda.Contrato.Usuario = new Usuario();
            Result result = new Result();
            ResponseEntity<Result<Factura>> response = restTemplate.exchange(urlBase + "factura/getByFecha?Desde=" + Desde + "&Hasta=" + Hasta,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Factura>>() {
            });

            ResponseEntity<Result<Contrato>> responseContrato = restTemplate.exchange(urlBase + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Usuario>> responseUsuarios = restTemplate.exchange(urlBase + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

            ResponseEntity<Result<NodoComercialRecepcion>> responseNodoRecepcion = restTemplate.exchange(urlBase + "nodoRecepcion",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialRecepcion>>() {
            });

            ResponseEntity<Result<NodoComercialEntrega>> responseNodoEntrega = restTemplate.exchange(urlBase + "nodoEntrega",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialEntrega>>() {
            });

            result.objects = response.getBody().objects;

            model.addAttribute("facturaBusqueda", facturaBusqueda);
            model.addAttribute("mostrarPaginacion", false);
            model.addAttribute("listaFacturas", result.objects);
            model.addAttribute("listaContratos", responseContrato.getBody().objects);
            model.addAttribute("listaUsuarios", responseUsuarios.getBody().objects);
            model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
            model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);

        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }

        return "ViewFacturas";
    }

}
