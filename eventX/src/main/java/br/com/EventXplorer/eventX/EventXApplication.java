package br.com.EventXplorer.eventX;

import br.com.EventXplorer.eventX.models.enums.Categorias;
import br.com.EventXplorer.eventX.models.enums.Status;
import br.com.EventXplorer.eventX.repository.impl.EventoRepositoryImpl;
import br.com.EventXplorer.eventX.repository.impl.InscricaoRepositoryImpl;
import br.com.EventXplorer.eventX.repository.impl.UsuarioRepositoryImpl;
import br.com.EventXplorer.eventX.services.EventoService;
import br.com.EventXplorer.eventX.services.InscricaoService;
import br.com.EventXplorer.eventX.services.UsuarioService;
import br.com.EventXplorer.eventX.validations.UsuarioDuplicadoException;
import br.com.EventXplorer.eventX.view.InscricaoView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

@SpringBootApplication
public class EventXApplication {

	public static void main(String[] args) throws UsuarioDuplicadoException {
		//SpringApplication.run(EventXApplication.class, args);
		UsuarioRepositoryImpl usuarioRepositoryImpl = new UsuarioRepositoryImpl();
		UsuarioService usuarioService = new UsuarioService(usuarioRepositoryImpl);
		EventoRepositoryImpl eventoRepositoryImpl = new EventoRepositoryImpl();
		EventoService eventoService = new EventoService(eventoRepositoryImpl);
		InscricaoRepositoryImpl inscricaoRepository = new InscricaoRepositoryImpl(eventoRepositoryImpl,usuarioRepositoryImpl);
		InscricaoService inscricaoService = new InscricaoService(inscricaoRepository);


		usuarioService.cadastrarUsuario("João", "joao@exemple.com", "Lauro de Freitas",18);

		eventoService.cadastrarEvento("Dia das Mães","Rua aqui perto", Categorias.FESTIVAL, Status.ATIVO,LocalTime.of(13,30),LocalDate.of(2024,3,1));

		inscricaoService.realizarIncricaoEvento(1,3);
		inscricaoService.realizarIncricaoEvento(2,4);
		inscricaoService.realizarIncricaoEvento(1,4);

		inscricaoService.buscarTodasAsIncricoes();

		InscricaoView inscricaoView = new InscricaoView();
		inscricaoView.mostrarDetalhesInscricao(inscricaoService.buscarTodasAsIncricoes());


		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("Bem-vindo ao seu aplicativo de gerenciamento de eventos!");
			System.out.println("Escolha uma opção:");
			System.out.println("1. Usuário");
			System.out.println("2. Evento");
			System.out.println("0. Sair");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consumir a quebra de linha

			switch (choice) {
				case 1:
					// Opção de usuário
					menuUsuario(scanner, usuarioService);
					break;

				case 2:
					// Opção de evento
					menuEvento(scanner, eventoService);
					break;

				case 0:
					running = false;
					System.out.println("Obrigado por usar nosso aplicativo. Até mais!");
					break;

				default:
					System.out.println("Opção inválida. Por favor, escolha novamente.");
					break;
			}
		}

		scanner.close();
	}

	public static void menuUsuario(Scanner scanner, UsuarioService usuarioService) throws UsuarioDuplicadoException { //, EventoService eventoService
		// Implemente as opções do menu para usuários
		boolean running = true;

		while (running) {
			System.out.println("Menu de usuário:");
			System.out.println("Escolha uma opção:");
			System.out.println("1. Cadastrar novo usuário");
			System.out.println("2. Visualizar todos os usuários");
			System.out.println("3. Buscar usuário por ID");
			System.out.println("4. Atualizar dados do usuário por ID");
			System.out.println("5. Excluir usuário por ID");
			System.out.println("6. Visualizar todos os eventos");
			System.out.println("7. Participar de um evento");
			System.out.println("0. Voltar");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consumir a quebra de linha

			switch (choice) {
				case 1:
					// Opção para cadastrar novo usuário
					System.out.println("Digite o nome do usuário:");
					String nome = scanner.nextLine();
					System.out.println("Digite o email do usuário:");
					String email = scanner.nextLine();
					System.out.println("Digite a cidade do usuário:");
					String cidade = scanner.nextLine();
					System.out.println("Digite a idade do usuário:");
					int idade = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					usuarioService.cadastrarUsuario(nome, email, cidade, idade);
					System.out.println("Usuário cadastrado com sucesso!");
					break;

				case 2:
					System.out.println("Lista de todos os usuários:");
					usuarioService.listarUsuarios();
					break;
				case 3:
					System.out.println("Digite o id do usuário:");
					long idUsuario = scanner.nextInt();
					scanner.nextLine();
					usuarioService.buscarUsuarioPorId(idUsuario);
					break;
				case 4:
					// Opção para atualizar o usuário
					System.out.println("Digite o ID do usuário:");
					long id = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Digite o nome do usuário:");
					String updateName = scanner.nextLine();
					System.out.println("Digite o email do usuário:");
					String updateEmail = scanner.nextLine();
					System.out.println("Digite a cidade do usuário:");
					String updateCity = scanner.nextLine();
					System.out.println("Digite a idade do usuário:");
					int updateAge = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					usuarioService.atualizarUsuario(id,updateName, updateEmail, updateCity, updateAge);
					System.out.println("Usuário atualizado com sucesso!");
					break;
				case 5:
					System.out.println("Digite o ID do usuário que deseja excluir:");
					int idExcluir = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					usuarioService.excluirUsuarioPorId(idExcluir);
					System.out.println("Usuário excluído com sucesso!");
					break;
//				case 5:
//					System.out.println("Lista de todos os eventos:");
//					eventoService.buscarTodosEventos().forEach(System.out::println);
//					break;
//
//
//				case 6:
//					System.out.println("Digite o ID do evento:");
//					long idEvento = scanner.nextLong();
//					System.out.println("Digite o ID do usuário:");
//					long idUsuarioEvento = scanner.nextLong();
//					scanner.nextLine(); // Consumir a quebra de linha
//					eventoService.adicionarParticipanteAoEvento(idUsuarioEvento, idEvento);
//					System.out.println("Participação confirmada com sucesso!");
//					break;

				case 0:
					// Opção para sair do menu de usuário
					running = false;
					break;
				default:
					System.out.println("Opção inválida. Por favor, escolha novamente.");
					break;
			}
		}


	}

	public static void menuEvento(Scanner scanner, EventoService eventoService) {
		// Implemente as opções do menu para eventos
//
		boolean running = true;

	while (running) {
			System.out.println("\nMenu de Eventos:");
			System.out.println("Escolha uma opção:");
			System.out.println("1. Cadastrar Novo Evento");
			System.out.println("2. Visualizar Todos os Eventos");
			System.out.println("3. Buscar Evento por ID");
			System.out.println("4. Atualizar Dados do Evento Por ID");
		    System.out.println("5. Excluir Evento Por ID");
			System.out.println("0. Voltar");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consumir a quebra de linha

			switch (choice) {
				case 1:
				System.out.println("\nCadastrar Novo Evento:");

					System.out.println("Digite o nome do evento:");
					String nome = scanner.nextLine();

					// Coletar informações do endereço
					System.out.println("Digite o endereço do evento:");
					System.out.println("Nome da Rua:");
					String rua = scanner.nextLine();
					System.out.println("Número da residência:");
					String numero = scanner.nextLine();
					System.out.println("Cidade:");
					String cidade = scanner.nextLine();

					// Criar o endereço a partir das informações coletadas
					String endereco = rua + ", " + numero + ", " + cidade;

					// Coletar categoria do evento
					System.out.println("Digite a categoria do evento:");
					System.out.println("Opções disponíveis:");
					System.out.println("1. Conferência");
					System.out.println("2. Seminário");
					System.out.println("3. Workshop");


                  // Coletar a escolha do usuário
					int escolha = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha

    // Mapear a escolha do usuário para a categoria correspondente
					Categorias categoria;
					switch (escolha) {
 						case 1:
							categoria = Categorias.CONFERENCIA;
							break;
						case 2:
							categoria = Categorias.SEMINARIO;
							break;
						case 3:
							categoria = Categorias.WORKSHOP;
							break;
						// Adicione outros casos conforme necessário
						default:
							System.out.println("Opção inválida. Usando categoria padrão.");
							categoria = Categorias.OUTRA_CATEGORIA;
							break;
					}
					Status status;
					switch (escolha) {
						case 1:
							status = Status.ATIVO;
							break;
						case 2:
							status = Status.EM_ANDAMENTO;
							break;
						case 3:
							status = Status.CANCELADO;
							break;
						case 4:
							status = Status.CONCLUIDO;
							break;
						// Adicione outros casos conforme necessário
						default:
							System.out.println("Opção inválida. Usando status padrão.");
							status = Status.ATIVO;
							break;
					}

					System.out.println("Digite a hora do evento (0-23):");
					int hora = scanner.nextInt();
					System.out.println("Digite o minuto do evento (0-59):");
					int minuto = scanner.nextInt();
					System.out.println("Digite o dia do evento:");
					int dia = scanner.nextInt();
					System.out.println("Digite o mês do evento:");
					int mes = scanner.nextInt();
					System.out.println("Digite o ano do evento:");
					int ano = scanner.nextInt();

					LocalTime horario = LocalTime.of(hora, minuto);
					LocalDate data = LocalDate.of(ano, mes, dia);
					// Chamar o método do serviço de evento para cadastrar o novo evento
					eventoService.cadastrarEvento(nome, endereco,categoria,status, horario, data);
					break;
				case 2:
					//Opção para buscar todos os eventos
					eventoService.listEventos();
					break;
				case 3:
					// Opção para buscar um evento por ID
					System.out.println("Digite o id do evento:");
					long idEvento = scanner.nextInt();
					scanner.nextLine();
					 eventoService.buscarEventoPorId(idEvento);
					break;
				case 4:
					// Opção para atualizar dados do evento por ID
					System.out.println("Digite o ID do evento:");
					long idEvent = scanner.nextLong();
					scanner.nextLine(); // Consumir a quebra de linha

					System.out.println("Digite o novo nome do evento:");
					String novoNome = scanner.nextLine();

					System.out.println("Digite o novo endereço do evento:");
					System.out.println("Nome da Rua:");
					String novaRua = scanner.nextLine();
					System.out.println("Número da residência:");
					String novoNumero = scanner.nextLine();
					System.out.println("Cidade:");
					String novaCidade = scanner.nextLine();
					String novoEndereco = novaRua + ", " + novoNumero + ", " + novaCidade;

					System.out.println("Digite a nova categoria do evento:");
					System.out.println("Opções disponíveis:");
					System.out.println("1. Conferência");
					System.out.println("2. Seminário");
					System.out.println("3. Workshop");
					int escolhaCategoria = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					Categorias novaCategoria;
					switch (escolhaCategoria) {
						case 1:
							novaCategoria = Categorias.CONFERENCIA;
							break;
						case 2:
							novaCategoria = Categorias.SEMINARIO;
							break;
						case 3:
							novaCategoria = Categorias.WORKSHOP;
							break;
						default:
							novaCategoria = Categorias.OUTRA_CATEGORIA;
							break;
					}

					System.out.println("Digite o novo status do evento:");
					System.out.println("Opções disponíveis:");
					System.out.println("1. Ativo");
					System.out.println("2. Em Andamento");
					System.out.println("3. Cancelado");
					System.out.println("4. Concluído");
					int escolhaStatus = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					Status novoStatus;
					switch (escolhaStatus) {
						case 1:
							novoStatus = Status.ATIVO;
							break;
						case 2:
							novoStatus = Status.EM_ANDAMENTO;
							break;
						case 3:
							novoStatus = Status.CANCELADO;
							break;
						case 4:
							novoStatus = Status.CONCLUIDO;
							break;
						default:
							novoStatus = Status.ATIVO; // Valor padrão
							break;
					}

					System.out.println("Digite a nova hora do evento (0-23):");
					int novaHora = scanner.nextInt();
					System.out.println("Digite o novo minuto do evento (0-59):");
					int novoMinuto = scanner.nextInt();
					System.out.println("Digite o novo dia do evento:");
					int novoDia = scanner.nextInt();
					System.out.println("Digite o novo mês do evento:");
					int novoMes = scanner.nextInt();
					System.out.println("Digite o novo ano do evento:");
					int novoAno = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha

					LocalTime novoHorario = LocalTime.of(novaHora, novoMinuto);
					LocalDate novaData = LocalDate.of(novoAno, novoMes, novoDia);

					// Chamar o método do serviço de evento para atualizar o evento
					eventoService.atualizarEvento(idEvent, novoNome, novoEndereco, novaCategoria, novoStatus, novoHorario, novaData);
					break;
				case 5:
					System.out.println("Digite o ID do evento que deseja excluir:");
					int idExcluir = scanner.nextInt();
					scanner.nextLine(); // Consumir a quebra de linha
					eventoService.excluirEventoPorId(idExcluir);
					System.out.println("Evento excluído com sucesso!");
					break;
				case 0:
					// Opção para voltar ao menu anterior
					running = false;
					break;
				default:
					System.out.println("Opção inválida. Por favor, escolha novamente.");
					break;
	      }
	   }
	}

}



