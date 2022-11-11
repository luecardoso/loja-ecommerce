package br.senac.ecommerce.pi.loja.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.ecommerce.pi.loja.modelo.CompraModelo;

@Repository
public interface CompraRepositorio extends JpaRepository <CompraModelo, Long> {
    
}
