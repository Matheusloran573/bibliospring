package biblio.com.biblioteca.service;

import biblio.com.biblioteca.model.Emprestimo;
import biblio.com.biblioteca.model.Livro;
import biblio.com.biblioteca.model.Usuario;
import biblio.com.biblioteca.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo pegarLivroEmprestado(Long idLivro, Long idUsuario) {
        Livro livro = livroService.buscarPorId(idLivro);
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        if (livro != null && usuario != null && livro.isDisponivel()) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setLivro(livro);
            emprestimo.setUsuario(usuario);
            emprestimo.setDevolvido(false);

            livro.setDisponivel(false);
            livroService.salvarLivro(livro);

            return emprestimoRepository.save(emprestimo);
        } else {
            return null; 
        }
    }

    public boolean devolverLivro(Long idEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo).orElse(null);
        if (emprestimo != null && !emprestimo.isDevolvido()) {
            emprestimo.setDevolvido(true);

            Livro livro = emprestimo.getLivro();
            livro.setDisponivel(true); 
            livroService.salvarLivro(livro);

            emprestimoRepository.save(emprestimo);
            return true;
        }
        return false; 
    }
}
