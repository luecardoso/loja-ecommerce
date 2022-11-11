package br.senac.ecommerce.pi.loja.servico;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.senac.ecommerce.pi.loja.modelo.MarcaModelo;
import br.senac.ecommerce.pi.loja.repositorio.MarcaRepositorio;

@Service
@Transactional
public class MarcaServico {

    @Autowired
    MarcaRepositorio marcaRepositorio;

    public static final int MARCA_POR_PAGINA = 10;

    public Page<MarcaModelo> listarPorPagina(int numPagina, String keyword) {
        Pageable pageable = PageRequest.of(numPagina - 1, MARCA_POR_PAGINA);

        if (keyword != null) {
            return marcaRepositorio.findAll(keyword, pageable);
        }
        return marcaRepositorio.findAll(pageable);
    }

    public void atualizarStatusAtivo(Long id, boolean enabled) {
        marcaRepositorio.atualizarStatusAtivado(id, enabled);
    }

    public MarcaModelo salvar(MarcaModelo marca) {
        return marcaRepositorio.save(marca);
    }

    public MarcaModelo editar(Long id) {
        return marcaRepositorio.findById(id).get();
    }

    public void deletar(Long id) {
        marcaRepositorio.deleteById(id);
    }

    public List<MarcaModelo> listaMarca() {
        return (List<MarcaModelo>) marcaRepositorio.findAll();
    }
}
