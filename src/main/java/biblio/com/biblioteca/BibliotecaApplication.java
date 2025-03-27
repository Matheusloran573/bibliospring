package biblio.com.biblioteca;

import biblio.com.biblioteca.model.Livro;
import biblio.com.biblioteca.model.Usuario;
import biblio.com.biblioteca.model.Emprestimo;
import biblio.com.biblioteca.service.LivroService;
import biblio.com.biblioteca.service.UsuarioService;
import biblio.com.biblioteca.service.EmprestimoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BibliotecaApplication.class, args);
        LivroService livroService = context.getBean(LivroService.class);
        UsuarioService usuarioService = context.getBean(UsuarioService.class);
        EmprestimoService emprestimoService = context.getBean(EmprestimoService.class);

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU BIBLIOTECA ===");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Cadastrar Usuário");
            System.out.println("4. Listar Usuários");
            System.out.println("5. Pegar Livro Emprestado");
            System.out.println("6. Listar Livros Emprestados");
            System.out.println("7. Devolver Livro");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Título do Livro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor do Livro: ");
                    String autor = scanner.nextLine();
                    System.out.print("Ano de Publicação: ");
                    int anoPublicacao = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Gênero do Livro: ");
                    String genero = scanner.nextLine();
                    Livro novoLivro = new Livro();
                    novoLivro.setTitulo(titulo);
                    novoLivro.setAutor(autor);
                    novoLivro.setAnoPublicacao(anoPublicacao);
                    novoLivro.setGenero(genero);
                    novoLivro.setDisponivel(true); // O livro começa disponível
                    livroService.salvarLivro(novoLivro);
                    System.out.println("Livro cadastrado com sucesso!");
                    break;

                case 2:
                    List<Livro> livros = livroService.listarTodos();
                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado.");
                    } else {
                        System.out.println("\n=== LISTA DE LIVROS ===");
                        livros.forEach(livro -> System.out.println(livro.getId() + " - " + livro.getTitulo()));
                    }
                    break;

                case 3:
                    System.out.print("Nome do Usuário: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email do Usuário: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha do Usuário: ");
                    String senha = scanner.nextLine();
                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setNome(nome);
                    novoUsuario.setEmail(email);
                    novoUsuario.setSenha(senha);
                    novoUsuario.setDataRegistro(LocalDate.now());
                    usuarioService.salvarUsuario(novoUsuario);
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;

                case 4:
                    List<Usuario> usuarios = usuarioService.listarTodos();
                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        System.out.println("\n=== LISTA DE USUÁRIOS ===");
                        usuarios.forEach(usuario -> System.out.println(usuario.getId() + " - " + usuario.getNome()));
                    }
                    break;

					case 5:
                    System.out.print("ID do Livro para emprestar: ");
                    Long idLivroEmprestar = scanner.nextLong();
                    System.out.print("ID do Usuário: ");
                    Long idUsuario = scanner.nextLong();

                    Emprestimo emprestimo = emprestimoService.pegarLivroEmprestado(idLivroEmprestar, idUsuario);
                    if (emprestimo != null) {
                        System.out.println("Livro emprestado com sucesso!");
                    } else {
                        System.out.println("Não foi possível emprestar o livro. Verifique se o livro está disponível.");
                    }
                    break;

                case 6:
                    List<Emprestimo> emprestimos = emprestimoService.listarTodos();
                    if (emprestimos.isEmpty()) {
                        System.out.println("Nenhum livro emprestado.");
                    } else {
                        System.out.println("\n=== LIVROS EMPRESTADOS ===");
                        emprestimos.forEach(emp -> System.out.println(emp.getId() + " - Livro: " + emp.getLivro().getTitulo() + " - Usuário: " + emp.getUsuario().getNome()));
                    }
                    break;

                case 7:
                    System.out.print("ID do Empréstimo para devolver: ");
                    Long idEmprestimo = scanner.nextLong();
                    boolean devolvido = emprestimoService.devolverLivro(idEmprestimo);
                    if (devolvido) {
                        System.out.println("Livro devolvido com sucesso!");
                    } else {
                        System.out.println("Não foi possível devolver o livro.");
                    }
                    break;

                case 8:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }

        } while (opcao != 8);

        scanner.close();
    }
}