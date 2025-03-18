package biblio.com.biblioteca.service;

import biblio.com.biblioteca.model.Livro;
import biblio.com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro salvarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id).orElse(null);
    }

    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }
}