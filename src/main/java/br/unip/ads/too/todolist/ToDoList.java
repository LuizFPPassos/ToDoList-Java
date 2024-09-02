package br.unip.ads.too.todolist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {

	private static Scanner scanner;

	public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        ListaDeTarefas listaDeTarefasObj = new ListaDeTarefas(); // instancia objeto da classe ListaDeTarefas
        listaDeTarefasObj.carregarListaDeTarefas(); // carrega lista de tarefas do arquivo JSON
        
        ArrayList<String> listaDeTarefasString; // lista de tarefas convertida para String
        
        int menu = 0;
        do{
            System.out.println("***Menu***");
            System.out.println("0-Sair");
            System.out.println("1-Adicionar Nova Tarefa");
            System.out.println("2-Exibir Tarefas");
            menu = Integer.parseInt(scanner.nextLine());

			switch(menu){
                case 0:
                    System.out.println("Saindo...");
                    return;
                case 1:
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(listaDeTarefasObj.obterProximoId()); // define o Id para o próximo Id disponível
                    
                    // título da tarefa
                    System.out.println("Digite o título da tarefa: ");
                    tarefa.setTitulo(scanner.nextLine());
                    
                    // descrição da tarefa
                    System.out.println("Digite a descrição da tarefa: ");
                    tarefa.setDescricao(scanner.nextLine());
                    
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
                        listaDeTarefasObj.ordenarListaDeTarefas(); // ordena a lista de tarefas por data de realização
                        listaDeTarefasObj.exportTarefasToJSON(listaDeTarefasObj.getListaDeTarefas()); // exporta a lista de tarefas para o arquivo JSON
                    }
                    break;
                case 2:
                	// caso não existam tarefas, exibir mensagem de lista vazia
					if (listaDeTarefasObj.getListaDeTarefas().isEmpty()) {
						System.out.println("Lista de tarefas vazia.");
						break;
					}
					
					listaDeTarefasObj.ordenarListaDeTarefas(); // ordena a lista de tarefas por data de realização
                	// converter listaDeTarefasObj.getListaDeTarefas() para String
                	listaDeTarefasString = new ArrayList<String>();
                	for(Tarefa t: listaDeTarefasObj.getListaDeTarefas()){
                		listaDeTarefasString.add(t.toString());
                	}
                    System.out.println("Tarefas: ");
                    for(String t: listaDeTarefasString){
                        System.out.println(t);
                    }

                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }while (menu != 0);
    }
    
}
