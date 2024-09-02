package br.unip.ads.too.todolist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ListaDeTarefas {
	
	private List<Tarefa> listaDeTarefas;
	private static final String NOME_ARQUIVO_JSON = "ListaDeTarefas.json"; // constante para nome do arquivo JSON
	
	// override do método toString para exibir a lista de tarefas ao usuário
	@Override
	public String toString() {
		return "ListaDeTarefas [listaDeTarefas=" + listaDeTarefas + "]";
	}
	
	// getter e setters
    public List<Tarefa> getListaDeTarefas() {
		return listaDeTarefas;
	}
	public void setListaDeTarefas(List<Tarefa> listaDeTarefas) {
		this.listaDeTarefas = listaDeTarefas;
	}

	// método para exportar a lista de tarefas para arquivo JSON definido em NOME_ARQUIVO_JSON
	public void exportTarefasToJSON(List<Tarefa> listaDeTarefas) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	    objectMapper.registerModule(new JavaTimeModule());
	    
	    try {
	        objectMapper.writeValue(new File(NOME_ARQUIVO_JSON), listaDeTarefas);
	        System.out.println("Lista de tarefas exportada para JSON: " + NOME_ARQUIVO_JSON);
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Erro ao exportar lista de tarefas para JSON");
	    }
	}
    
	// método para ordenar a lista de tarefas pela data de realização da tarefa, e em caso de empate, pela data de criação
	public void ordenarListaDeTarefas() {
	    listaDeTarefas.sort(Comparator
	        .comparing(Tarefa::getData)
	        .thenComparing(Tarefa::getDataDeCriacao));
	}
    
    // método para carregar a lista de tarefas a partir do arquivo JSON
    public void carregarListaDeTarefas() {
    	ObjectMapper objectMapper = new ObjectMapper();
    	objectMapper.registerModule(new JavaTimeModule());
    	
        File file = new File(NOME_ARQUIVO_JSON);

        // Verificar se o arquivo já existe
        if (!file.exists()) {
            try {
            	System.out.println("Arquivo ListaDeTarefas.json não encontrado. Criando novo arquivo...");
                
                file.createNewFile(); // Cria um novo arquivo vazio

                // Inicializa o arquivo JSON com uma lista vazia
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                    writer.flush();
                }
                System.out.println("Arquivo ListaDeTarefas.json criado com sucesso.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao criar arquivo ListaDeTarefas.json");
                return;  // Sai do método se houver erro na criação do arquivo
            }
        }
    	
    	// carregar ListaDeTarefas.json e preencher listaDeTarefas
        try {
            listaDeTarefas = objectMapper.readValue(file, new TypeReference<List<Tarefa>>(){});
            System.out.println("Lista de tarefas carregada com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar lista de tarefas");
        }
    }
    
    // método para fazer o auto-increment do Id das tarefas
    public int obterProximoId() {
        int proximoId = 1; // Id inicial
        for (Tarefa tarefa : listaDeTarefas) {
            if (tarefa.getId() >= proximoId) {
                proximoId = tarefa.getId() + 1;
            }
        }
        return proximoId;
    }
    
}