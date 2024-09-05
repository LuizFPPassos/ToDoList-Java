package br.unip.ads.too.todolist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

	private static Scanner scanner;

	public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        ListaDeTarefas listaDeTarefasObj = new ListaDeTarefas(); // instancia objeto da classe ListaDeTarefas
        listaDeTarefasObj.obterUltimaListaDeTarefas(); // obtém o nome do arquivo JSON da última lista utilizada
        listaDeTarefasObj.carregarListaDeTarefas(); // carrega lista de tarefas do arquivo JSON
        
        ArrayList<String> listaDeTarefasString = null; // lista de tarefas convertida para String
        
        int menu = 0;
        do{
        	System.out.println();
        	exibirTarefas(listaDeTarefasObj, listaDeTarefasString);
        	
            System.out.println("***Menu***");
            System.out.println("0-Sair");
            System.out.println("1-Adicionar Nova Tarefa");
            System.out.println("2-Editar tarefa");
            System.out.println("3-Excluir tarefa");
            System.out.println("4-Carregar arquivo de lista de tarefas existente");
            System.out.println("5-Criar novo arquivo de lista de tarefas");
            
            menu = Integer.parseInt(scanner.nextLine());

			switch(menu){
                case 0: // sair
                    System.out.println("Saindo...");
                    System.out.println("Pressione ENTER para fechar o programa.");
                    scanner.nextLine();
                    return;
                    
                case 1: // adicionar nova tarefa
                	
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(listaDeTarefasObj.obterProximoId()); // define o Id para o próximo Id disponível
                    
                    // título da tarefa
                    do {
                        System.out.println("Digite o título da tarefa: ");
                        tarefa.setTitulo(scanner.nextLine());
						if (tarefa.getTitulo() == null || tarefa.getTitulo().isEmpty()) {
							System.out.println("Título inválido, por favor tente novamente.");
						}
                    } while (tarefa.getTitulo() == null || tarefa.getTitulo().isEmpty());

                    // descrição da tarefa
                    do {
                        System.out.println("Digite a descrição da tarefa: ");
                        tarefa.setDescricao(scanner.nextLine());
                        if (tarefa.getDescricao() == null || tarefa.getDescricao().isEmpty()) {
                            System.out.println("Descrição inválida, por favor tente novamente.");                            
                        }
                    } while (tarefa.getDescricao() == null || tarefa.getDescricao().isEmpty());
                    
                    // data para realização da tarefa
                    LocalDateTime dataFormatada = null;
                    do{
                        System.out.println("Digite a data da tarefa (formato \"dd/MM/yyyy HH:mm\"): ");
                        String data = scanner.nextLine();
                        dataFormatada = tarefa.formatarData(data);
                        
                        if (dataFormatada != null) {
                            tarefa.setData(dataFormatada);
                        }else{
                            System.out.println("Data inválida, por favor tente novamente.");
                        }
                    }while (dataFormatada == null);
                    tarefa.setData(dataFormatada);
                    
                    tarefa.setDataDeCriacao(); // define a data de criação da tarefa como a data atual
                    
                    // confirmação de criação da tarefa
                    System.out.println("Confirma criação da tarefa? (0-Não, 1-Sim)");
                    System.out.println(tarefa.toString());
                    
                    int confirma = Integer.parseInt(scanner.nextLine());
                    if (confirma == 1){
                        listaDeTarefasObj.getListaDeTarefas().add(tarefa); // adiciona a tarefa à lista de tarefas
                        listaDeTarefasObj.atualizarListaDeTarefas();
                        enterParaVoltar();
                    }
                    break;
                    
                case 2: // editar tarefa
                	
                	System.out.println("Digite o Id da tarefa que deseja editar: ");
                	int id = Integer.parseInt(scanner.nextLine());
                	Tarefa tarefaEditar = listaDeTarefasObj.procurarTarefa(id);
                	if(tarefaEditar != null) {
                		int menuEditar = 0;
                		do {
                            System.out.println(tarefaEditar.toString());
                            System.out.println("0-Voltar");
                            System.out.println("1-Alterar título");
                            System.out.println("2-Alterar descrição");
                            System.out.println("3-Alterar data");
                            System.out.println("4-Alternar status de tarefa (completa/incompleta)");
                            menuEditar = Integer.parseInt(scanner.nextLine());
                            String tituloEditar = null;
                            String descricaoEditar = null;
                            LocalDateTime dataEditar = null;
                            switch(menuEditar) {
                            	case 0: // cancelar
                            		//System.out.println("Edição abortada.");
                            		enterParaVoltar();
                            		break;
                            	case 1: // alterar título
                                    do {
                                        System.out.println("Digite o novo título da tarefa: ");
                                		tituloEditar = scanner.nextLine();
                						if (tituloEditar == null || tituloEditar.isEmpty()) {
                							System.out.println("Título inválido, por favor tente novamente.");
                						}
                						else {
                							tarefaEditar.setTitulo(tituloEditar);
                						}
                                    } while (tituloEditar == null || tituloEditar.isEmpty());
                                    
                                	listaDeTarefasObj.editarTarefa(tarefaEditar);
                                	listaDeTarefasObj.atualizarListaDeTarefas();
                            		break;
                            		
                            	case 2: // alterar descrição
                            		do {
                            			System.out.println("Digite a nova descrição da tarefa: ");
                            			descricaoEditar = scanner.nextLine();
                            			if (descricaoEditar == null || descricaoEditar.isEmpty()) {
                            				System.out.println("Descrição inválida, por favor tente novamente.");
                            			}
                            			else {
                            				tarefaEditar.setDescricao(descricaoEditar);
                            			}
                            		} while (descricaoEditar == null || descricaoEditar.isEmpty());
                            		
                                	listaDeTarefasObj.editarTarefa(tarefaEditar);
                                	listaDeTarefasObj.atualizarListaDeTarefas();
                        			break;
                        			
                            	case 3: // alterar data
                                    do{
                                        System.out.println("Digite a data da tarefa (formato \"dd/MM/yyyy HH:mm\"): ");
                                        String data = scanner.nextLine();
                                        dataEditar = tarefaEditar.formatarData(data);
                                        
                                        if (dataEditar != null) {
                                            tarefaEditar.setData(dataEditar);
                                        	listaDeTarefasObj.editarTarefa(tarefaEditar);
                                        	listaDeTarefasObj.atualizarListaDeTarefas();
                                        }else{
                                            System.out.println("Data inválida, por favor tente novamente.");
                                        }
                                    }while (dataEditar == null);
                                    break;
                                    
                            	case 4: // alternar status (completa/incompleta)
                            		tarefaEditar.toggleCompleta();
            						System.out.println((tarefaEditar.isCompleta() ? "Tarefa marcada como completa." : "Tarefa marcada como incompleta."));
            						listaDeTarefasObj.atualizarListaDeTarefas();
            						break;
            						
                            	default:
                            		System.out.println("Opção inválida.");
                            		break;
                            }
                		} while (menuEditar != 0);
                        break;
					} else {
						System.out.println("Tarefa não encontrada.");
						enterParaVoltar();
						break;
					}
                	
                case 3: // excluir tarefa
                	
                	System.out.println("Digite o Id da tarefa que deseja excluir: ");
                	int idExcluir = Integer.parseInt(scanner.nextLine());
					try {
	                	listaDeTarefasObj.excluirTarefa(idExcluir);
	                	listaDeTarefasObj.atualizarListaDeTarefas();
	                	enterParaVoltar();
					} catch (Exception e) {
						System.out.println("Erro ao excluir tarefa: " + e.getMessage());
						enterParaVoltar();
					}
                	break;
					
                case 4: // carregar nova lista de tarefas
                	
                	System.out.println("Digite o nome do arquivo JSON da lista de tarefas: ");
                	String nomeArquivo = scanner.nextLine();
					if (nomeArquivo == null || nomeArquivo.isEmpty()) {
                        System.out.println("Nome de arquivo inválido.");
                        enterParaVoltar();
                        break;
                    }
					try {
						listaDeTarefasObj.setNomeArquivoJson(nomeArquivo);
						if(listaDeTarefasObj.verificarArquivoJsonExiste(nomeArquivo)) {
							System.out.println("Arquivo " + nomeArquivo + ".json" + " encontrado.");
							listaDeTarefasObj = new ListaDeTarefas();
							listaDeTarefasObj.setNomeArquivoJson(nomeArquivo);
							listaDeTarefasObj.salvarUltimaListaDeTarefas();
							listaDeTarefasObj.carregarListaDeTarefas();
							System.out.println("Nova lista de tarefas carregada com sucesso.");
                        } else {
                        	System.out.println("Arquivo " + nomeArquivo + ".json" + " não encontrado.");
                        }
						enterParaVoltar();
					} catch (Exception e) {
						System.out.println("Erro ao carregar lista de tarefas: " + e.getMessage());
						enterParaVoltar();
					}
			        break;
			        
                case 5: // criar novo arquivo de lista de tarefas

					System.out.println("Digite o nome de arquivo para a nova lista de tarefas: ");
					String nomeArquivoNovo = scanner.nextLine();
					if (nomeArquivoNovo == null || nomeArquivoNovo.isEmpty()) {
                        System.out.println("Nome de arquivo inválido.");
                        enterParaVoltar();
                        break;
                    } else if (listaDeTarefasObj.verificarArquivoJsonExiste(nomeArquivoNovo)) {
                    	System.out.println("Arquivo " + nomeArquivoNovo + ".json" + " já existe.");
						enterParaVoltar();
                    	break;
                    }
					try {
						listaDeTarefasObj = new ListaDeTarefas();
						listaDeTarefasObj.setTitulo(nomeArquivoNovo);
						listaDeTarefasObj.setNomeArquivoJson(nomeArquivoNovo);
						listaDeTarefasObj.criarNovaListaDeTarefas();
						listaDeTarefasObj.salvarUltimaListaDeTarefas();
						System.out.println("Nova lista de tarefas criada com sucesso.");
						enterParaVoltar();
						break;
					} catch (Exception e) {
						System.out.println("Erro ao criar nova lista de tarefas: " + e.getMessage());
						enterParaVoltar();
						break;
					}
                	
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }while (menu != 0);
    }
	
	// método para exibir a lista de tarefas
	public static void exibirTarefas(ListaDeTarefas listaDeTarefasObj, ArrayList<String> listaDeTarefasString) {
		
    	System.out.println("Lista de tarefas: " + listaDeTarefasObj.getTitulo());
    	
		// caso não existam tarefas, exibir mensagem de lista vazia
		if (listaDeTarefasObj.getListaDeTarefas().isEmpty()) {
			System.out.println("Lista de tarefas vazia.");
			return;
		}
		
		listaDeTarefasObj.ordenarListaDeTarefas(); // ordena a lista de tarefas por data de realização
    	
		// converter listaDeTarefasObj.getListaDeTarefas() para String
    	listaDeTarefasString = new ArrayList<String>();
    	for(Tarefa t: listaDeTarefasObj.getListaDeTarefas()){
    		listaDeTarefasString.add(t.toString());
    	}

        int qtdTarefas = 0; // contador de tarefas
        int qtdTarefasAtrasadas = 0; // contador de tarefas atrasadas
		for (Tarefa t : listaDeTarefasObj.getListaDeTarefas()) {
			if (t.isAtrasada()) {
				System.out.println("A tarefa " + t.getTitulo() + " está atrasada.");
				qtdTarefasAtrasadas++;
			}
			qtdTarefas++;
		}
        System.out.println("Quantidade de tarefas atrasadas: " + qtdTarefasAtrasadas + " de " + qtdTarefas);
        System.out.println("Tarefas: ");
        for(String t: listaDeTarefasString){
            System.out.println(t);
        }
	}
	
	// método para exibir mensagem para pressionar ENTER para voltar ao menu
	public static void enterParaVoltar() {
		System.out.println("Pressione ENTER para retornar ao menu.");
		scanner.nextLine();
	}
    

}

