package io.github.salmasouza.agenda.controller;

import io.github.salmasouza.agenda.model.Contato;
import io.github.salmasouza.agenda.repository.ContatoRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin("*")
public class ContatoController {

    private final ContatoRepository contatoRepository;

    public ContatoController(ContatoRepository contatoRepository){
        this.contatoRepository = contatoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  Contato save(@RequestBody  Contato contato){
        return contatoRepository.save(contato);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        contatoRepository.deleteById(id);
    }

    @GetMapping
    public Page<Contato> list(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value= "size", defaultValue = "10") Integer tamanhoPagina
    ){
        Sort sort = Sort.by(Sort.Direction.ASC,"nome");
        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina, sort);
        return contatoRepository.findAll(pageRequest);
    }

    @GetMapping("/{id}")
    public Optional<Contato> buscarContato(@PathVariable Long id){
        return contatoRepository.findById(id);
    }

    @PatchMapping("/{id}/favorito")
    public void favorite(@PathVariable Long id ){
        Optional<Contato> contato = contatoRepository.findById(id);
        contato.ifPresent(c -> {
            boolean favorito = c.getFavorito() == Boolean.TRUE;
            c.setFavorito(!favorito);
            contatoRepository.save(c);
        });
    }

    @PutMapping("/{id}/foto")
    public byte[]  addPhoto(@PathVariable Long id, @RequestParam("foto") Part arquivo){
        Optional<Contato> contato = contatoRepository.findById(id);

        return contato.map(c ->{
            try {
                InputStream is = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(is, bytes);
                c.setFoto(bytes);
                contatoRepository.save(c);
                is.close();
                return bytes;

            }catch(IOException e){
                return null;
            }
        }).orElse(null);
    }


}
